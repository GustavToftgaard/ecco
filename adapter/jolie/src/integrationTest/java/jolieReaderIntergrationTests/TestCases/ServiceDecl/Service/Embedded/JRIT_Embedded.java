package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Embedded;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Embedded extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("embeddedTest1.ol", 1);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                test1(node);
                break;
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Embed
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.EMBEDDED, 1);

        // 1.2.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 1.2.1.*: Lines
        checkLineNode(node.getChildren().get(0), "  embedded {\n", 2);

        checkLineNode(node.getChildren().get(1), "    " + "Java: \"joliex.db.DatabaseService\" in Database2\n", 3);

        checkLineNode(node.getChildren().get(2), "  " + "}\n", 4);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 5);
    }
}

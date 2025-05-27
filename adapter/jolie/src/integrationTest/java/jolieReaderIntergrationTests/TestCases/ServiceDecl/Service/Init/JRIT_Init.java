package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Init;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Init extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("initTest1.ol", 1);
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

        // 1.2: Init
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.INIT, 1);

        // 1.2.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 9);

        // 1.2.1.*: Lines
        checkLineNode(node.getChildren().get(0), "  init {\n", 2);

        checkLineNode(node.getChildren().get(1), "    " + "config.database << {\n", 3);

        checkLineNode(node.getChildren().get(2), "      " + "username = \"\"\n", 4);

        checkLineNode(node.getChildren().get(3), "      " + "password = \"\"\n", 5);

        checkLineNode(node.getChildren().get(4), "      " + "host = \"\"\n", 6);

        checkLineNode(node.getChildren().get(5), "      " + "database = \"file:db.sqlite\"\n", 7);

        checkLineNode(node.getChildren().get(6), "      " + "driver = \"sqlite\"\n", 8);

        checkLineNode(node.getChildren().get(7), "    " + "}\n", 9);

        checkLineNode(node.getChildren().get(8), "  " + "}\n", 10);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 11);
    }
}

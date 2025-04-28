package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Main;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Main extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("mainTest1.ol", 1);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                test1(node);
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 3);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Embed
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Console", 2);

        // 1.2.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "console", 2);

        // 1.3: Main
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 1.3.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 1.3.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "\n" + "  main {\n" + "    ", 3);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "println@console( \"Hello There\" )()\n" + "  ", 4);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "}", 5);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 6);
    }
}

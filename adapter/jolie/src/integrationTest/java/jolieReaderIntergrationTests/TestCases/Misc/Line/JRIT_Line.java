package jolieReaderIntergrationTests.TestCases.Misc.Line;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Line extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("lineTest1.ol", 1);
        fileNames.put("lineTest2.ol", 2);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                test1(node);
                break;
            case 2:
                test2(node);
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

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Main
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 1.2.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 2);

        // 1.2.1.*: Lines
        checkLineNode(node.getChildren().get(0), "  main {\n" + "\n", 2);

        checkLineNode(node.getChildren().get(1), "  " + "}\n", 4);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 5);
    }

    private void test2(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Main
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 1.2.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 4);

        // 1.2.1.*: Lines
        checkLineNode(node.getChildren().get(0), "  main {\n", 2);

        checkLineNode(node.getChildren().get(1), "    " + "testLine1\n", 3);

        checkLineNode(node.getChildren().get(2), "    " + "testLine2\n", 4);

        checkLineNode(node.getChildren().get(3), "  " + "}\n", 5);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 6);
    }
}

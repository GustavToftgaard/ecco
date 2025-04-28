package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Courier;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Courier extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("courierTest1.ol", 1);
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
        checkContextNode(node, NodeTypes.SERVICEDECL, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.3: Courier
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.COURIER, 2);

        // 1.3.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestInputPort", 2);

        // 1.3.2: Block
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 8);

        // 1.3.2.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "> {\n" + "    ", 2);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "[ testOperationRR( request )( response ) ] {\n" + "      ", 3);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "some code\n" + "    ", 4);

        checkContextNode(node.getChildren().get(3), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(3).getChildren().get(0), "}\n\n" + "    ", 5);

//        checkContextNode(node.getChildren().get(4), NodeTypes.LINE, 1);
//        checkLineNode(node.getChildren().get(4).getChildren().get(0), "\n" + "    ", 6);

        checkContextNode(node.getChildren().get(4), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(4).getChildren().get(0), "[ testOperationOW( request ) ] {\n" + "      ", 7);

        checkContextNode(node.getChildren().get(5), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(5).getChildren().get(0), "some code\n" + "    ", 8);

        checkContextNode(node.getChildren().get(6), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(6).getChildren().get(0), "}\n" + "  ", 9);

        checkContextNode(node.getChildren().get(7), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(7).getChildren().get(0), "}", 10); // TODO!!!

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 11);
    }
}
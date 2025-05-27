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
        checkLineNode(node.getChildren().get(0), " {\n", 2);

        checkLineNode(node.getChildren().get(1), "    " + "[ testOperationRR( request )( response ) ] {\n", 3);

        checkLineNode(node.getChildren().get(2), "      " + "some code\n", 4);

        checkLineNode(node.getChildren().get(3), "    " + "}\n\n", 5);

        checkLineNode(node.getChildren().get(4), "    " + "[ testOperationOW( request ) ] {\n", 7);

        checkLineNode(node.getChildren().get(5), "      " + "some code\n", 8);

        checkLineNode(node.getChildren().get(6), "    " + "}\n", 9);

        checkLineNode(node.getChildren().get(7), "  " + "}\n", 10);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 11);
    }
}
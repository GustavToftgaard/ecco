package jolieReaderIntergrationTests.TestCases.InterfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_InterfaceDecl extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("interfaceDeclTest1.ol", 1);
        fileNames.put("interfaceDeclTest2.ol", 2);
        fileNames.put("interfaceDeclTest3.ol", 3);
        fileNames.put("interfaceDeclTest4.ol", 4);
        fileNames.put("interfaceDeclTest5.ol", 5);
        fileNames.put("interfaceDeclTest6.ol", 6);
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
            case 3:
                test3(node);
                break;
            case 4:
                test4(node);
                break;
            case 5:
                test5(node);
                break;
            case 6:
                test6(node);
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestAPI", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 1);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testThis", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }

    private void test2(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestAPI", 1);

        // 1.2: ONE_WAY_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.ONE_WAY_DECL, 1);

        // 1.2.1: ONE_WAY_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.ONE_WAY_ELEMENT, 2);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "test", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }

    private void test3(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 3);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestAPI", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 1);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testThis", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        // 1.3: ONE_WAY_DECL
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.ONE_WAY_DECL, 1);

        // 1.3.1: ONE_WAY_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0);
        checkContextNode(node, NodeTypes.ONE_WAY_ELEMENT, 2);

        // 1.3.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "test", 5);

        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 5);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 6);
    }

    private void test4(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestId", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 1);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "*", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Id", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "void", 3);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }

    private void test5(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestId", 1);

        // 1.2: ONE_WAY_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.ONE_WAY_DECL, 1);

        // 1.2.1: ONE_WAY_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.ONE_WAY_ELEMENT, 2);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "*", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Id", 3);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }

    private void test6(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 3);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestId", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 1);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "*", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Id", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "void", 3);

        // 1.3: ONE_WAY_DECL
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.ONE_WAY_DECL, 1);

        // 1.3.1: ONE_WAY_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0);
        checkContextNode(node, NodeTypes.ONE_WAY_ELEMENT, 2);

        // 1.3.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "*", 5);

        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Id", 5);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 6);
    }
}

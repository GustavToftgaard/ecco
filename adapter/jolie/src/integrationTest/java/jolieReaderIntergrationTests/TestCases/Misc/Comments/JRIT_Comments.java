package jolieReaderIntergrationTests.TestCases.Misc.Comments;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Comments extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("commentsTest1.ol", 1);
        fileNames.put("commentsTest2.ol", 2);
        fileNames.put("commentsTest3.ol", 3);
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
        checkTokenNode(node, JolieTokenType.ID, "NumbersAPI", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 2);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumUpTo", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        // 1.2.2: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.2.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumBetween", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 4);

        // 2: EOF
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

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "NumbersAPI", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 2);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumUpTo", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        // 1.2.2: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.2.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumBetween", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 4);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 5);
    }

    private void test3(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: INTERFACEDECL
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "NumbersAPI", 1);

        // 1.2: REQUEST_RESPONSE_DECL
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 2);

        // 1.2.1: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.1.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumUpTo", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 3);

        // 1.2.2: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 1.2.2.*: IDs
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumBetween", 8);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 8);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 8);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 9);
    }
}

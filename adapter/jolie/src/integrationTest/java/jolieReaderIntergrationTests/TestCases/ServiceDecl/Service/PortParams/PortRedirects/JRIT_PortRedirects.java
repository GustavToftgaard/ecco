package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortRedirects;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_PortRedirects extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("portRedirectsTest1.ol", 1);
        fileNames.put("portRedirectsTest2.ol", 2);
        fileNames.put("portRedirectsTest3.ol", 3);
        fileNames.put("portRedirectsTest4.ol", 4);
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

        // 1.2: InputPort
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.INPUTPORT, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testInPort", 2);

        // 1.2.2: PortLocation
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.PORTREDIRECTS, 8);

        // 1.2.2.*:  Tokens
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test1", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "=", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "Test1Service,", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(4);
        checkTokenNode(node, JolieTokenType.ID, "Test2", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(5);
        checkTokenNode(node, JolieTokenType.ID, "=", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(6);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(7);
        checkTokenNode(node, JolieTokenType.ID, "Test2Service", 3);

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

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: InputPort
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.INPUTPORT, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testInPort", 2);

        // 1.2.2: PortLocation
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.PORTREDIRECTS, 8);

        // 1.2.2.*:  Tokens
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test1", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "=", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "Test1Service,", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(4);
        checkTokenNode(node, JolieTokenType.ID, "Test2", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(5);
        checkTokenNode(node, JolieTokenType.ID, "=", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(6);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 3);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(7);
        checkTokenNode(node, JolieTokenType.ID, "Test2Service", 3);

        // 2: EndOfFile
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

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: InputPort
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.INPUTPORT, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testInPort", 2);

        // 1.2.2: PortLocation
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.PORTREDIRECTS, 8);

        // 1.2.2.*:  Tokens
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test1", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "=", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "Test1Service,", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(4);
        checkTokenNode(node, JolieTokenType.ID, "Test2", 5);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(5);
        checkTokenNode(node, JolieTokenType.ID, "=", 5);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(6);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 5);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(7);
        checkTokenNode(node, JolieTokenType.ID, "Test2Service", 5);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 7);
    }

    private void test4(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: InputPort
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.INPUTPORT, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testInPort", 2);

        // 1.2.2: PortLocation
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.PORTREDIRECTS, 8);

        // 1.2.2.*:  Tokens
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test1", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "=", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "Test1Service,", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(4);
        checkTokenNode(node, JolieTokenType.ID, "Test2", 5);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(5);
        checkTokenNode(node, JolieTokenType.ID, "=", 5);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(6);
        checkTokenNode(node, JolieTokenType.GREATER_THAN, ">", 5);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(7);
        checkTokenNode(node, JolieTokenType.ID, "Test2Service", 5);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 7);
    }
}

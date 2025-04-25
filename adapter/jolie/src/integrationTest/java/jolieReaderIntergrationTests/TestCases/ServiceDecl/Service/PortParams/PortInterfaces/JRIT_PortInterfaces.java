package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortInterfaces;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_PortInterfaces extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("portInterfacesTest1.ol", 1);
        fileNames.put("portInterfacesTest2.ol", 2);
        fileNames.put("portInterfacesTest3.ol", 3);
        fileNames.put("portInterfacesTest4.ol", 4);
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
        checkContextNode(node, NodeTypes.PORTINTERFACES, 1);

        // 1.2.2.1:  ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestInterface", 3);

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
        checkContextNode(node, NodeTypes.PORTINTERFACES, 1);

        // 1.2.2.1:  ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestInterface", 3);

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
        checkContextNode(node, NodeTypes.PORTINTERFACES, 2);

        // 1.2.2.*: Tokens
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestInterface1,", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "TestInterface2", 5);

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
        checkContextNode(node, NodeTypes.PORTINTERFACES, 2);

        // 1.2.2.*: Tokens
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestInterface1,", 4);

        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "TestInterface2", 5);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 7);
    }
}

package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Embed;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Embed extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("embedTest1.ol", 1);
        fileNames.put("embedTest2.ol", 2);
        fileNames.put("embedTest3.ol", 3);
        fileNames.put("embedTest4.ol", 4);
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
        checkContextNode(node, NodeTypes.SERVICEDECL, 3);

        // 1.1: ID
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

        // 1.3: Embed
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 1.3.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 3);

        // 1.3.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "stringUtils", 3);

        // 2: EndOfFile
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

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Embed
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestImport", 2);

        // 1.2.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "TestOutputPort", 2);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 3);
    }

    private void test3(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 4);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Embed
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.EMBED, 3);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Console", 2);

        // 1.2.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "1", 2);

        // 1.2.3: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "console", 2);

        // 1.3: Embed
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.EMBED, 3);

        // 1.3.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 3);

        // 1.3.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "1", 3);

        // 1.3.3: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "stringUtils", 3);

        // 1.4: Embed
        node = pluginNodeChildren.get(0).getChildren().get(3);
        checkContextNode(node, NodeTypes.EMBED, 11);

        // 1.4.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "MyService", 4);

        // 1.4.2: LEFT_BRACE
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.LEFT_BRACE, "{", 4);

        // 1.4.3: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, ".protocol", 4);

        // 1.4.4: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "=", 4);

        // 1.4.5: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(4);
        checkTokenNode(node, JolieTokenType.STRING, "\"sodep\"", 4);

        // 1.4.6: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(5);
        checkTokenNode(node, JolieTokenType.ID, ",", 4);

        // 1.4.7: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(6);
        checkTokenNode(node, JolieTokenType.ID, ".factor", 4);

        // 1.4.8: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(7);
        checkTokenNode(node, JolieTokenType.ID, "=", 4);

        // 1.4.9: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(8);
        checkTokenNode(node, JolieTokenType.ID, "2", 4);

        // 1.4.10: RIGHT_BRACE
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(9);
        checkTokenNode(node, JolieTokenType.RIGHT_BRACE, "}", 4);

        // 1.4.11: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(10);
        checkTokenNode(node, JolieTokenType.ID, "Service", 4);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 5);
    }

    private void test4(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 3);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: Embed
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.EMBED, 3);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "TestImport", 2);

        // 1.2.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "1", 2);

        // 1.2.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "TestOutputPort", 2);

        // 1.3: Embed
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.EMBED, 11);

        // 1.3.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "MyService", 3);

        // 1.3.2: LEFT_BRACE
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.LEFT_BRACE, "{", 3);

        // 1.3.3: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, ".protocol", 3);

        // 1.3.4: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "=", 3);

        // 1.3.5: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(4);
        checkTokenNode(node, JolieTokenType.STRING, "\"sodep\"", 3);

        // 1.3.6: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(5);
        checkTokenNode(node, JolieTokenType.ID, ",", 3);

        // 1.3.7: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(6);
        checkTokenNode(node, JolieTokenType.ID, ".factor", 3);

        // 1.3.8: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(7);
        checkTokenNode(node, JolieTokenType.ID, "=", 3);

        // 1.3.9: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(8);
        checkTokenNode(node, JolieTokenType.ID, "2", 3);

        // 1.3.10: RIGHT_BRACE
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(9);
        checkTokenNode(node, JolieTokenType.RIGHT_BRACE, "}", 3);

        // 1.3.11: ID
        node = pluginNodeChildren.get(0).getChildren().get(2).getChildren().get(10);
        checkTokenNode(node, JolieTokenType.ID, "Service", 3);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }
}

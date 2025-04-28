package jolieReaderIntergrationTests.TestCases.ServiceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_ServiceDecl extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("serviceDeclTest1.ol", 1);
        fileNames.put("serviceDeclTest2.ol", 2);
        fileNames.put("serviceDeclTest3.ol", 3);
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

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 5);

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

        // 1.4: Execution
        node = pluginNodeChildren.get(0).getChildren().get(3);
        checkContextNode(node, NodeTypes.EXECUTION, 1);

        // 1.4.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sequential", 5);

        // 1.5: Main
        node = pluginNodeChildren.get(0).getChildren().get(4);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 1.5.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(4).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 1.5.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "\n" + "\n" + "  main {\n" + "    ", 7);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "println@console( \"Hello There\" )()\n" + "  ", 8);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "}", 9);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 10);
    }

    private void test2(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 8);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test", 1);

        // 1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "params", 1);

        // 1.3: ID
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.COLON, ":", 1);

        // 1.4: ID
        node = pluginNodeChildren.get(0).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "Binding", 1);

        // 1.5: Embed
        node = pluginNodeChildren.get(0).getChildren().get(4);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 1.5.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(4).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Console", 2);

        // 1.5.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(4).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "console", 2);

        // 1.6: Embed
        node = pluginNodeChildren.get(0).getChildren().get(5);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 1.6.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(5).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 3);

        // 1.6.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(5).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "stringUtils", 3);

        // 1.7: Execution
        node = pluginNodeChildren.get(0).getChildren().get(6);
        checkContextNode(node, NodeTypes.EXECUTION, 1);

        // 1.7.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(6).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sequential", 5);

        // 1.8: Main
        node = pluginNodeChildren.get(0).getChildren().get(7);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 1.8.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(7).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 1.8.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "\n" + "\n" + "  main {\n" + "    ", 7);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "println@console( \"Hello There\" )()\n" + "  ", 8);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "}", 9);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 10);
    }

    private void test3(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(3, pluginNodeChildren.size());

        Node.Op node;

        // 1: ServiceDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.SERVICEDECL, 5);

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

        // 1.4: Execution
        node = pluginNodeChildren.get(0).getChildren().get(3);
        checkContextNode(node, NodeTypes.EXECUTION, 1);

        // 1.4.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(3).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sequential", 5);

        // 1.5: Main
        node = pluginNodeChildren.get(0).getChildren().get(4);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 1.5.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(4).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 1.5.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "\n" + "\n" + "  main {\n" + "    ", 7);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "println@console( \"Hello There\" )()\n" + "  ", 8);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "}", 9);

        // 2: ServiceDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.SERVICEDECL, 5);

        // 2.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Test2", 12);

        // 2.2: Embed
        node = pluginNodeChildren.get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 2.2.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Console", 13);

        // 2.2.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "console", 13);

        // 2.3: Embed
        node = pluginNodeChildren.get(1).getChildren().get(2);
        checkContextNode(node, NodeTypes.EMBED, 2);

        // 2.3.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 14);

        // 2.3.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(2).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "stringUtils", 14);

        // 2.4: Execution
        node = pluginNodeChildren.get(1).getChildren().get(3);
        checkContextNode(node, NodeTypes.EXECUTION, 1);

        // 2.4.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(3).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sequential", 16);

        // 2.5: Main
        node = pluginNodeChildren.get(1).getChildren().get(4);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 2.5.1: Block
        node = pluginNodeChildren.get(1).getChildren().get(4).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 2.5.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "\n" + "\n" + "  main {\n" + "    ", 18);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "println@console( \"General Kenobi\" )()\n" + "  ", 19);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "}", 20);

        // 3: EndOfFile
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 3.1: EOF
        node = pluginNodeChildren.get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 21);
    }
}

package jolieReaderIntergrationTests.TestCases.Misc.EOF;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_EOF extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("EOFTest1.ol", 1);
        fileNames.put("EOFTest2.ol", 2);
        fileNames.put("EOFTest3.ol", 3);
        fileNames.put("EOFTest4.ol", 4);
        fileNames.put("EOFTest5.ol", 5);
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
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(1, pluginNodeChildren.size());

        Node.Op node;

        // 1: EOF
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 1.1: EOF
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 1);
    }

    private void test2(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(1, pluginNodeChildren.size());

        Node.Op node;

        // 1: EOF
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 1.1: EOF
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 2);
    }

    private void test3(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Import
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 1.1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "console", 1);

        // 1.1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Console", 1);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 1);
    }

    private void test4(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Import
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 1.1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "console", 1);

        // 1.1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Console", 1);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 2);
    }

    private void test5(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Import
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 1.1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "console", 1);

        // 1.1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Console", 1);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 3);
    }
}

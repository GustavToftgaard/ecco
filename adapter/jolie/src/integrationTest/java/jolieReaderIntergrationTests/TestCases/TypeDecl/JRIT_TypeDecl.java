package jolieReaderIntergrationTests.TestCases.TypeDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_TypeDecl extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("typeDeclTest1.ol", 1);
        fileNames.put("typeDeclTest2.ol", 2);
        fileNames.put("typeDeclTest3.ol", 3);
        fileNames.put("typeDeclTest4.ol", 4);
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
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: TypeDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.TYPEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 1);

        // 1.2: Block
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 4);

        // 1.2.*: Lines
        checkLineNode(node.getChildren().get(0), " {\n", 1);

        checkLineNode(node.getChildren().get(1), "  " + "m:int\n", 2);

        checkLineNode(node.getChildren().get(2), "  " + "n:int\n", 3);

        checkLineNode(node.getChildren().get(3), "}", 4);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1 EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }

    private void test2(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: TypeDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.TYPEDECL, 2);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Id", 1);

        // 1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 1);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1 EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 1);
    }

    private void test3(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(2, pluginNodeChildren.size());

        Node.Op node;

        // 1: TypeDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.TYPEDECL, 3);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Id_class", 1);

        // 1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 1);

        // 1.3: Block
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.BLOCK, 3);

        // 1.3.*: Lines
        checkLineNode(node.getChildren().get(0), " {\n", 1);

        checkLineNode(node.getChildren().get(1), "  " + "IdClass:string\n", 2);

        checkLineNode(node.getChildren().get(2), "}", 3);

        // 2: EOF
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1 EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 3);
    }

    private void test4(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(3, pluginNodeChildren.size());

        Node.Op node;

        // 1: TypeDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.TYPEDECL, 3);

        // 1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testType", 1);

        // 1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 1);

        // 1.3: ID
        node = pluginNodeChildren.get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "string", 1);

        // 2: TypeDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.TYPEDECL, 4);

        // 2.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "testType", 2);

        // 2.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 2);

        // 2.3: ID
        node = pluginNodeChildren.get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "string", 2);

        // 2.4: ID
        node = pluginNodeChildren.get(1).getChildren().get(3);
        checkTokenNode(node, JolieTokenType.ID, "void", 2);

        // 3: EOF
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 3.1 EOF
        node = pluginNodeChildren.get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 2);
    }
}

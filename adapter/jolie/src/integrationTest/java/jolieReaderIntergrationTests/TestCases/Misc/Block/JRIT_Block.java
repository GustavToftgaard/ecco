package jolieReaderIntergrationTests.TestCases.Misc.Block;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Block extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("blockTest1.ol", 1);
        fileNames.put("blockTest2.ol", 2);
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
        checkTokenNode(node, JolieTokenType.ID, "testType", 1);

        // 1.2: Block
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 1);

        // 1.2.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "{" + "}", 1);


        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 1);
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
        checkTokenNode(node, JolieTokenType.ID, "testType", 1);

        // 1.2: Block
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 4);

        // 1.2.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "{\n" + "  ", 1);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "m:int\n" + "  ", 2);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "n:int\n", 3);

        checkContextNode(node.getChildren().get(3), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(3).getChildren().get(0), "}", 4);


        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 4);
    }
}

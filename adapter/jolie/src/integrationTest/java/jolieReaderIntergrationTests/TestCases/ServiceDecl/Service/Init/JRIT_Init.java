package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Init;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Init extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("initTest1.ol", 1);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                test1(node);
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

        // 1.2: Init
        node = pluginNodeChildren.get(0).getChildren().get(1);
        checkContextNode(node, NodeTypes.INIT, 1);

        // 1.2.1: Block
        node = pluginNodeChildren.get(0).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 9);

        // 1.2.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "{\n" + "    ", 2);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "config.database << {\n" + "      ", 3);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "username = \"\"\n" + "      ", 4);

        checkContextNode(node.getChildren().get(3), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(3).getChildren().get(0), "password = \"\"\n" + "      ", 5);

        checkContextNode(node.getChildren().get(4), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(4).getChildren().get(0), "host = \"\"\n" + "      ", 6);

        checkContextNode(node.getChildren().get(5), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(5).getChildren().get(0), "database = \"file:db.sqlite\"\n" + "      ", 7);

        checkContextNode(node.getChildren().get(6), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(6).getChildren().get(0), "driver = \"sqlite\"\n" + "    ", 8);

        checkContextNode(node.getChildren().get(7), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(7).getChildren().get(0), "}\n" + "  ", 9);

        checkContextNode(node.getChildren().get(8), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(8).getChildren().get(0), "}", 10);

        // 2: EndOfFile
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 2.1: EOF
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 11);
    }
}

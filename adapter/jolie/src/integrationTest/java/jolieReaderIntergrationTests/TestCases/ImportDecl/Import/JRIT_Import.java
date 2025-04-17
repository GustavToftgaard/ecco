package jolieReaderIntergrationTests.TestCases.ImportDecl.Import;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Import extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("importTest1.ol", 1);
        fileNames.put("importTest2.ol", 2);
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
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(3, pluginNodeChildren.size());

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

        // ------

        // 2: ImportDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 2.1: Import
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 2.1.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "string_utils", 2);

        // 2.1.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 2);

        // ------

        // 3 EndOfFile
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 3.1 EOF
        node = pluginNodeChildren.get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 2);
    }

    private void test2(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(3, pluginNodeChildren.size());

        Node.Op node;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Import
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 3);

        // 1.1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "console", 1);

        // 1.1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Console", 1);

        // 1.1.3: Line
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.LINE, 1);

        // 1.1.3.1 LineContents
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(2).getChildren().get(0);
        checkLineNode(node, " as alias_1\n", 1);

        // ------

        // 2: ImportDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 2.1: Import
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 3);

        // 2.1.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "string_utils", 2);

        // 2.1.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 2);

        // 2.1.3: Line
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(2);
        checkContextNode(node, NodeTypes.LINE, 1);

        // 2.1.3.1 LineContents
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(2).getChildren().get(0);
        checkLineNode(node, " as alias_2", 2);

        // ------

        // 3 EndOfFile
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 3.1 EOF
        checkTokenNode(node.getChildren().get(0), JolieTokenType.EOF, "", 2);
    }
}

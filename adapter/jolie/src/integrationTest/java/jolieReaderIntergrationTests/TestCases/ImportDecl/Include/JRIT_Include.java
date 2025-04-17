package jolieReaderIntergrationTests.TestCases.ImportDecl.Include;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_Include extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("includeTest1.ol", 1);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                test1(node);
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(3, pluginNodeChildren.size());

        Node.Op node;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Include
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.INCLUDE, 1);

        // 1.1.1: String
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.STRING, "\"console.iol\"", 1);

        // 2: ImportDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 2.1: Include
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.INCLUDE, 1);

        // 2.1.1: String
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.STRING, "\"string_utils.iol\"", 2);

        // 3: EOF
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 3.1 EOF
        node = pluginNodeChildren.get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 2);
    }
}
package jolieReaderIntergrationTests.interfacesAndAbstractClasses;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class JolieReaderIntegrationTestCase {
    public static final Map<String, Integer> fileNames = new HashMap<>();

    public ArrayList<String> getFileNames() {
        Collection<String> values = fileNames.keySet();
        return new ArrayList<String>(values);
    }

    public void clearFileNames() {
        fileNames.clear();
    }

    public abstract void test(Node.Op resultPluginNode, String fileName);

    public void checkContextNode(Node.Op node, NodeTypes nodeType, int numberOfChildren) {
        assertInstanceOf(JolieContextArtifactData.class, node.getArtifact().getData());
        JolieContextArtifactData nodeContext = (JolieContextArtifactData) node.getArtifact().getData();
        assertSame(nodeType, nodeContext.getType());
        assertEquals(numberOfChildren, node.getChildren().size());
    }

    public void checkTokenNode(Node.Op node, JolieTokenType tokenType, String lexeme, int lineNumber) {
        assertInstanceOf(JolieTokenArtifactData.class, node.getArtifact().getData());
        JolieTokenArtifactData nodeToken = (JolieTokenArtifactData) node.getArtifact().getData();
        assertSame(tokenType, nodeToken.getType());
        assertEquals(lexeme, nodeToken.getLexeme());
    }

    public void checkLineNode(Node.Op node, String lineContents, int lineNumber) {
        assertInstanceOf(JolieLineArtifactData.class, node.getArtifact().getData());
        JolieLineArtifactData nodeLine = (JolieLineArtifactData) node.getArtifact().getData();
        assertEquals(lineContents, nodeLine.getLineContents());
    }
}

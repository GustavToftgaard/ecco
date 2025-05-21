package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.tree.Node;

public class ECCOToString {
    private final Node rootNode;

    public ECCOToString(Node rootNode) {
        this.rootNode = rootNode;
    }

    public String convert() {
        StringBuilder res = new StringBuilder();

        for (Node node : rootNode.getChildren()) {
            res.append(convertNode(node));
        }

        if (isContextNode(rootNode)) {
            JolieContextArtifactData contextNode = toContextNode(rootNode);
            res.append(contextNode.getPostLexeme());
        }

        return res.toString();
    }

    private String convertNode(Node node) {
        StringBuilder res = new StringBuilder();

        if (isContextNode(node)) {
            for (Node childNode : node.getChildren()) {
                res.append(convertNode(childNode));
            }
            JolieContextArtifactData contextNode = toContextNode(node);
            res.append(contextNode.getPostLexeme());

        } else if (isTokenNode(node)) {
            JolieTokenArtifactData tokenNode = toTokenNode(node);
            res.append(tokenNode.getPreWhitespace()).append(tokenNode.getLexeme()).append(tokenNode.getPostWhitespace());

        } else if (isLineNode(node)) {
            JolieLineArtifactData lineNode = toLineNode(node);
            res.append(lineNode.getLineContents());
        }

        return res.toString();
    }

    // ----

    private boolean isContextNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieContextArtifactData);
    }

    private boolean isTokenNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieTokenArtifactData);
    }

    private boolean isLineNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieLineArtifactData);
    }

    private JolieContextArtifactData toContextNode(Node node) {
        return (JolieContextArtifactData) node.getArtifact().getData();
    }

    private JolieTokenArtifactData toTokenNode(Node node) {
        return (JolieTokenArtifactData) node.getArtifact().getData();
    }

    private JolieLineArtifactData toLineNode(Node node) {
        return (JolieLineArtifactData) node.getArtifact().getData();
    }
}
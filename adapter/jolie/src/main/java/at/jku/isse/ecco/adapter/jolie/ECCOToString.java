package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.tree.Node;

public class ECCOToString {
    private final Node.Op rootNode;

    public ECCOToString(Node.Op rootNode) {
        this.rootNode = rootNode;
    }

    public String convert() {
        StringBuilder res = new StringBuilder();

        for (Node.Op node : rootNode.getChildren()) {
            res.append(convertNode(node));
        }

        if (isContextNode(rootNode)) {
            JolieContextArtifactData contextNode = toContextNode(rootNode);
            res.append(contextNode.getPostLexeme());
        }

        return res.toString();
    }

    private String convertNode(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (isContextNode(node)) {
            for (Node.Op childNode : node.getChildren()) {
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

    private boolean isContextNode(Node.Op node) {
        return (node.getArtifact().getData() instanceof JolieContextArtifactData);
    }

    private boolean isTokenNode(Node.Op node) {
        return (node.getArtifact().getData() instanceof JolieTokenArtifactData);
    }

    private boolean isLineNode(Node.Op node) {
        return (node.getArtifact().getData() instanceof JolieLineArtifactData);
    }

    private JolieContextArtifactData toContextNode(Node.Op node) {
        return (JolieContextArtifactData) node.getArtifact().getData();
    }

    private JolieTokenArtifactData toTokenNode(Node.Op node) {
        return (JolieTokenArtifactData) node.getArtifact().getData();
    }

    private JolieLineArtifactData toLineNode(Node.Op node) {
        return (JolieLineArtifactData) node.getArtifact().getData();
    }
}
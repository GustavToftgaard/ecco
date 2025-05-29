package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.tree.Node;

import java.nio.file.Path;

/**
 * ECCOToString turns Jolie artifact trees into
 * Jolie source code
 */
public class ECCOToString {
    private final Node rootNode;

    /**
     * ECCOToString is the class constructor.
     * Expects root node of Jolie artifact tree as input.
     *
     * @param rootNode Root node of a Jolie artifact tree
     */
    public ECCOToString(Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Recursively converts all children of rootNode into source code.
     * Return a string containing the source code form the
     * artifact tree given in the constructor.
     *
     * @return A string of Jolie source code
     */
    public String convert() {
        StringBuilder res = new StringBuilder();

        for (Node node : rootNode.getChildren()) {
            res.append(convertNode(node));
        }

        return res.toString();
    }

    /**
     * It is a helper function for convert.
     * Recursively checks the type of a node and all
     * its children, converts all nodes into source code.
     * Return a string containing the source code form the
     * artifact tree given.
     *
     * @param node Node of type context, token, or line
     * @return A string of Jolie source code
     */
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
            res.append(tokenNode.getPreLexeme()).append(tokenNode.getLexeme()).append(tokenNode.getPostLexeme());

        } else if (isLineNode(node)) {
            JolieLineArtifactData lineNode = toLineNode(node);
            res.append(lineNode.getLineContents());
        }

        return res.toString();
    }

    // ----

    /**
     * It is a helper function.
     * Checks if given node is of type context node.
     *
     * @param node Node ot check
     * @return A boolean telling if the given node is a context node
     */
    private boolean isContextNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieContextArtifactData);
    }

    /**
     * It is a helper function.
     * Checks if given node is of type token node.
     *
     * @param node Node ot check
     * @return A boolean telling if the given node is a token node
     */
    private boolean isTokenNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieTokenArtifactData);
    }

    /**
     * It is a helper function.
     * Checks if given node is of type line node.
     *
     * @param node Node ot check
     * @return A boolean telling if the given node is a line node
     */
    private boolean isLineNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieLineArtifactData);
    }

    /**
     * It is a helper function.
     * Converts given node into a context artifact data.
     *
     * @param node Node ot check
     * @return Context artifact data
     */
    private JolieContextArtifactData toContextNode(Node node) {
        return (JolieContextArtifactData) node.getArtifact().getData();
    }

    /**
     * It is a helper function.
     * Converts given node into a token artifact data.
     *
     * @param node Node ot check
     * @return Token artifact data
     */
    private JolieTokenArtifactData toTokenNode(Node node) {
        return (JolieTokenArtifactData) node.getArtifact().getData();
    }

    /**
     * It is a helper function.
     * Converts given node into a line artifact data.
     *
     * @param node Node ot check
     * @return Line artifact data
     */
    private JolieLineArtifactData toLineNode(Node node) {
        return (JolieLineArtifactData) node.getArtifact().getData();
    }
}
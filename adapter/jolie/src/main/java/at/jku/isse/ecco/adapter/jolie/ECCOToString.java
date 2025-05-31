package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.tree.Node;

/**
 * The {@code ECCOToString} class provides functionality for converting a Jolie artifact tree
 * into the corresponding string representation of the Jolie source code.
 * <p>
 * The class recursively traverses the artifact tree from the root node ({@link Node}).
 * It constructs the returned string (source code) by concatenating the {@code preLexeme}, {@code lexeme},
 * {@code postLexeme}, line contents, and context node {@code postLexeme} of
 * ({@link JolieContextArtifactData}, {@link JolieTokenArtifactData}, {@link JolieLineArtifactData}).
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * Node rootNode = ???; // Root node of the Jolie artifact tree
 * ECCOToString eccoToString = new ECCOToString(rootNode);
 * String jolieSourceCode = eccoToString.convert();
 * }</pre>
 * </p>
 *
 * @see JolieWriter
 *
 * @author Gustav Toftgaard <gustav@familientoftgaard.dk>
 */
public class ECCOToString {
    private final Node rootNode;

    /**
     * Constructs an {@code ECCOToString} instance.
     * <p>
     * The constructor expects the root node of a Jolie artifact tree, which it will convert
     * into Jolie source code.
     *
     * @param rootNode Root node of the Jolie artifact tree to convert
     */
    public ECCOToString(Node rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * Recursively converts all children of the {@code rootNode} into Jolie source code.
     * <p>
     * This method initiates the conversion process by calling the recursive helper method
     * {@link #convertNode(Node)} on each child of the root node.
     *
     * @return A {@link String} containing the Jolie source code representation of the artifact tree
     */
    public String convert() {
        StringBuilder res = new StringBuilder();

        for (Node node : rootNode.getChildren()) {
            res.append(convertNode(node));
        }

        return res.toString();
    }

    /**
     * Recursively converts the given {@link Node} and its children into a
     * {@link String} representing the Jolie source code.
     * <p>
     * Depending on the type of the node
     * ({@link JolieContextArtifactData}, {@link JolieTokenArtifactData}, {@link JolieLineArtifactData})
     * , the appropriate conversion is performed and the {@link String} is generated.
     *
     * @param node The node to convert
     * @return A {@link String} containing the Jolie source code representation of the node
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
     * Checks whether the given {@link Node} is an instance of {@link JolieContextArtifactData}.
     *
     * @param node The node to check
     * @return {@code true} if the node is an instance of {@link JolieContextArtifactData} - {@code false} otherwise
     */
    private boolean isContextNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieContextArtifactData);
    }

    /**
     * Checks whether the given {@link Node} is an instance of {@link JolieTokenArtifactData}.
     *
     * @param node The node to check
     * @return {@code true} if the node is an instance of {@link JolieTokenArtifactData} - {@code false} otherwise
     */
    private boolean isTokenNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieTokenArtifactData);
    }

    /**
     * Checks whether the given {@link Node} is an instance of {@link JolieLineArtifactData}.
     *
     * @param node The node to check
     * @return {@code true} if the node is an instance of {@link JolieLineArtifactData} - {@code false} otherwise
     */
    private boolean isLineNode(Node node) {
        return (node.getArtifact().getData() instanceof JolieLineArtifactData);
    }

    /**
     * Converts the given {@link Node} into a {@link JolieContextArtifactData}.
     *
     * @param node The node to convert
     * @return The {@link JolieContextArtifactData} of the given {@link Node}
     */
    private JolieContextArtifactData toContextNode(Node node) {
        return (JolieContextArtifactData) node.getArtifact().getData();
    }

    /**
     * Converts the given {@link Node} into a {@link JolieTokenArtifactData}.
     *
     * @param node The node to convert
     * @return The {@link JolieTokenArtifactData} of the given {@link Node}
     */
    private JolieTokenArtifactData toTokenNode(Node node) {
        return (JolieTokenArtifactData) node.getArtifact().getData();
    }

    /**
     * Converts the given {@link Node} into a {@link JolieLineArtifactData}.
     *
     * @param node The node to convert
     * @return The {@link JolieLineArtifactData} of the given {@link Node}
     */
    private JolieLineArtifactData toLineNode(Node node) {
        return (JolieLineArtifactData) node.getArtifact().getData();
    }
}
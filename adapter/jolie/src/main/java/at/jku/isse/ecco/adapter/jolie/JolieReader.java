package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.ArtifactReader;
import at.jku.isse.ecco.adapter.dispatch.PluginArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.JolieFileToAST;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.AstToECCO;
import at.jku.isse.ecco.artifact.Artifact;
import at.jku.isse.ecco.dao.EntityFactory;
import at.jku.isse.ecco.service.listener.ReadListener;
import at.jku.isse.ecco.tree.Node;
import com.google.inject.Inject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The {@code JolieReader} class implements {@link ArtifactReader}.
 * It provides functionality for converting Jolie source code files
 * into the corresponding artifact tree.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * JolieReader reader = new JolieReader(new MemEntityFactory());
 * Path[] relativePathToFiles = ???; // Get list of relative paths to files
 * Set<Node.Op> jolieArtifactTrees = reader.read(relativePathToFiles);
 * }</pre>
 * </p>
 *
 * @see JolieWriter
 *
 * @author Gustav Toftgaard <gustav@familientoftgaard.dk>
 */
public class JolieReader implements ArtifactReader<Path, Set<Node.Op>> {
    private List<ReadListener> listeners = new LinkedList<>();
    private final EntityFactory entityFactory;

    private static Map<Integer, String[]> prioritizedPatterns;

    static {
        prioritizedPatterns = new HashMap<>();
        prioritizedPatterns.put(Integer.MAX_VALUE, new String[]{"**.ol"});
    }

    /**
     * Constructs a new {@code JolieReader} with the specified {@link EntityFactory}.
     *
     * @param entityFactory the entity factory used to create artifacts and nodes
     * @throws NullPointerException if {@code entityFactory} is {@code null}
     */
    @Inject
    public JolieReader(EntityFactory entityFactory) {
        checkNotNull(entityFactory);
        this.entityFactory = entityFactory;
    }

    /**
     * Returns plugin ID for the {@link JoliePlugin}.
     *
     * @return A {@link String} containing the ID of the plugin
     */
    @Override
    public String getPluginId() {
        return JoliePlugin.class.getName();
    }

    /**
     * Retrieves the prioritized filename patterns associated with this the {@link JolieReader}.
     * <p>
     * By default, this reader prioritizes files ending with ".ol".
     *
     * @return A {@link Map} of priority levels of filename patterns
     */
    @Override
    public Map<Integer, String[]> getPrioritizedPatterns() {
        return prioritizedPatterns;
    }

    /**
     * Reads and parses all input files relative to the specified base directory,
     * converting them into artifact trees.
     * <p>
     * This method processes each file into an AST and translates it into an artifact tree.
     *
     * @param base The base directory
     * @param input Relative paths to input files (expected to be Jolie source files)
     * @return A set of artifact trees representing the parsed Jolie files
     * @see #read(Path[])
     */
    @Override
    public Set<Node.Op> read(Path base, Path[] input) {
        Set<Node.Op> nodes = new HashSet<>();

        for (Path path : input) {
            Path resolvedPath = base.resolve(path);
            Node.Op pluginNode = this.parseJolieFile(resolvedPath, path);
            nodes.add(pluginNode);
        }
        return nodes;
    }

    /**
     * Reads and parses the given Jolie file into an artifact tree.
     *
     * @param resolvedPath The absolute path to the file
     * @param path The relative path of the file
     * @return The root node of the generated artifact tree
     */
    private Node.Op parseJolieFile(Path resolvedPath, Path path) {
        Node.Op pluginNode = createPluginNode(path); // root node
        JolieFileToAST jolieFileToAST = new JolieFileToAST();
        List<at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node> ast = jolieFileToAST.scanAndParse(resolvedPath);
        AstToECCO translator = new AstToECCO(pluginNode, this.entityFactory, path);
        return translator.translate(ast);
    }

    /**
     * Creates a plugin artifact node for the given file.
     *
     * @param path The relative path of the file
     * @return The created plugin node
     */
    private Node.Op createPluginNode(Path path) {
        Artifact.Op<PluginArtifactData> pluginArtifactData =
                this.entityFactory.createArtifact(new PluginArtifactData(this.getPluginId(), path));
        return this.entityFactory.createOrderedNode(pluginArtifactData);
    }

    /**
     * Calls {@link #read(Path, Path[])} with the current working directory as the base.
     *
     * @param input Relative paths to Jolie source files
     * @return A set of artifact nodes representing the parsed Jolie files
     */
    @Override
    public Set<Node.Op> read(Path[] input) {
        return this.read(Paths.get("."), input);
    }

    /**
     * Adds a {@link ReadListener ReadListener} that
     * is notified everytime a file has been read.
     *
     * @param listener ReadListener to be added to notify list
     * @see ReadListener
     * @see #removeListener(ReadListener)
     */
    @Override
    public void addListener(ReadListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes the specified ReadListener from {@link ReadListener ReadListener}
     * so that it is on longer notified everytime a file has been read.
     *
     * @param listener ReadListener to be removed to notify list
     * @see ReadListener
     * @see #addListener(ReadListener)
     */
    @Override
    public void removeListener(ReadListener listener) {
        this.listeners.remove(listener);
    }
}

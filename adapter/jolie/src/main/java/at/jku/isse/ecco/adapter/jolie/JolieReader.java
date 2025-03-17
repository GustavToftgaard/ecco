package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.ArtifactReader;
import at.jku.isse.ecco.adapter.dispatch.PluginArtifactData;
import at.jku.isse.ecco.artifact.Artifact;
import at.jku.isse.ecco.dao.EntityFactory;
import at.jku.isse.ecco.service.listener.ReadListener;
import at.jku.isse.ecco.tree.Node;
import com.google.inject.Inject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class JolieReader implements ArtifactReader<Path, Set<Node.Op>> {

    private List<ReadListener> listeners = new LinkedList<>();
    private final EntityFactory entityFactory;

    private static Map<Integer, String[]> prioritizedPatterns =
            Collections.singletonMap(0, new String[]{"**.ol"});

    @Inject
    public JolieReader(EntityFactory entityFactory) {
        checkNotNull(entityFactory);
        this.entityFactory = entityFactory;
    }

    /**
     * Returns plugin ID for Jolie plugin
     *
     * @return plugin ID
     */
    @Override
    public String getPluginId() {
        // return JoliePlugin.getPluginId();
        return JoliePlugin.class.getName();
    }

    /**
     * Returns a map of filename patterns with priority to the JolieReader
     *
     * @return filename patterns
     */
    @Override
    public Map<Integer, String[]> getPrioritizedPatterns() {
        return prioritizedPatterns;
    }

    /**
     * Reads all files in input from the base parameter.
     * Return set of node operants representing Jolie code.
     * Expects input to be Jolie files.
     *
     * @param base Base path with input files
     * @param input Paths to input files from base
     * @return A set of node operants representing Jolie code
     * @see #read(Path[])
     */
    @Override
    public Set<Node.Op> read(Path base, Path[] input) {
        Set<Node.Op> nodes = new HashSet<>();
        for (Path path : input) {
            Path resolvedPath = base.resolve(path);
            nodes.add(createPluginNode(path));
            // TODO Parser
        }
        return nodes;
    }

    private Node.Op createPluginNode(Path path) {
        Artifact.Op<PluginArtifactData> pluginArtifactData =
                this.entityFactory.createArtifact(new PluginArtifactData(this.getPluginId(), path));
        return this.entityFactory.createNode(pluginArtifactData);
    }

    /**
     * Takes the input files and the base parameter as
     * parameters to calls {@link #read(Path, Path[]) }.
     * Return set of node operants representing Jolie code.
     * Expects input to be Jolie files.
     *
     * @param input Paths from working directory to input files
     * @return A set of node operants representing Jolie code
     * @see #read(Path, Path[])
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

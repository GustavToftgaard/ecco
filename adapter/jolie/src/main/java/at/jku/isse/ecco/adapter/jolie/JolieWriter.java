package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.EccoException;
import at.jku.isse.ecco.adapter.ArtifactReader;
import at.jku.isse.ecco.adapter.ArtifactWriter;
import at.jku.isse.ecco.adapter.dispatch.PluginArtifactData;
import at.jku.isse.ecco.artifact.Artifact;
import at.jku.isse.ecco.artifact.ArtifactData;
import at.jku.isse.ecco.service.listener.ReadListener;
import at.jku.isse.ecco.service.listener.WriteListener;
import at.jku.isse.ecco.tree.Node;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * The {@code JolieWriter} class implements {@link ArtifactWriter}.
 * It provides functionality for converting Jolie artifact trees
 *  back into Jolie source code.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * JolieWriter writer = new JolieWriter();
 * Set<Node> artifactTrees = ...; // ECCO artifact trees
 * Path outputDirectory = Paths.get("output");
 * Path[] writtenFiles = writer.write(outputDirectory, artifactTrees);
 * }</pre>
 * </p>
 *
 * @see JolieReader
 *
 * @author Gustav Toftgaard <gustav@familientoftgaard.dk>
 */
public class JolieWriter implements ArtifactWriter<Set<Node>, Path> {

    private List<WriteListener> listeners = new LinkedList<>();

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
     * Converts artifact trees to strings and writes
     * them the output directory.
     * <p>
     * For each {@link Node} in the input set, it
     * converts the node back to Jolie source code.
     *
     * @param base The base of the output directory
     * @param input The set of artifact trees
     * @return A list of {@link Path} of the written files
     * @throws EccoException If the artifact data of a node is not of type {@link PluginArtifactData}
     */
    @Override
    public Path[] write(Path base, Set<Node> input) {
        List<Path> output = new ArrayList<>();

        for (Node fileNode : input) {
            Artifact<?> fileArtifact = fileNode.getArtifact();
            ArtifactData artifactData = fileArtifact.getData();
            if (!(artifactData instanceof PluginArtifactData)) {
                throw new EccoException("Expected plugin artifact data.");
            }

            PluginArtifactData pluginArtifactData = (PluginArtifactData) artifactData;
            Path outputPath = base.resolve(pluginArtifactData.getPath());
            output.add(this.writeJolieFile(outputPath, fileNode));
        }

        return output.toArray(new Path[0]);
    }

    /**
     * Helper method for writing a single Jolie artifact tree to a file.
     * <p>
     * Calls {@link ECCOToString} to convert the given {@link Node}
     * into Jolie source code.
     * Then it writes it to the file given by the {@link Path}.
     *
     * @param filePath The path to the file to write to
     * @param orderedNode The artifact tree to convert
     * @return The {@link Path} to the written file
     */
    private Path writeJolieFile(Path filePath, Node orderedNode){
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

            ECCOToString eccoToString = new ECCOToString(orderedNode);
            String jolieFileCode = eccoToString.convert();
            bw.write(jolieFileCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * Calls {@link #write(Path, Set)} with the current working directory as the base.
     *
     * @param input The artifact trees to write
     * @return A list of {@link Path} representing the written Jolie files
     */
    @Override
    public Path[] write(Set<Node> input) {
        return this.write(Paths.get("."), input);
    }

    /**
     * Adds a {@link WriteListener WriteListener} that
     * is notified everytime a file has been written.
     *
     * @param listener WriteListener to be added to notify list
     * @see WriteListener
     * @see #removeListener(WriteListener)
     */
    @Override
    public void addListener(WriteListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Removes the specified WriteListener from {@link WriteListener WriteListener}
     * so that it is on longer notified everytime a file has been written.
     *
     * @param listener WriteListener to be removed to notify list
     * @see WriteListener
     * @see #addListener(WriteListener)
     */
    @Override
    public void removeListener(WriteListener listener) {
        this.listeners.remove(listener);
    }
}
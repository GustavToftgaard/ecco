package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.EccoException;
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

public class JolieWriter implements ArtifactWriter<Set<Node>, Path> {

    private List<WriteListener> listeners = new LinkedList<>();

    @Override
    public String getPluginId() {
        return JoliePlugin.class.getName();
    }

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

    private Path writeJolieFile(Path filePath, Node orderedNode){
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {

//            for (Node node : orderedNode.getChildren()){
//                ECCOToString eccoToString = new ECCOToString(node);
//                String jolieFileCode = eccoToString.convert();
//                bw.write(jolieFileCode);
//            }

            ECCOToString eccoToString = new ECCOToString(orderedNode);
            String jolieFileCode = eccoToString.convert();
            bw.write(jolieFileCode);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @Override
    public Path[] write(Set<Node> input) {
        return this.write(Paths.get("."), input);
    }

    @Override
    public void addListener(WriteListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(WriteListener listener) {
        this.listeners.remove(listener);
    }
}
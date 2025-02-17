package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.ArtifactWriter;
import at.jku.isse.ecco.service.listener.WriteListener;
import at.jku.isse.ecco.tree.Node;

import java.nio.file.Path;
import java.util.Set;

public class JolieWriter implements ArtifactWriter<Set<Node>, Path> {

    @Override
    public String getPluginId() {
        return "";
        // TODO
    }

    @Override
    public Path[] write(Path base, Set<Node> input) {
        return new Path[0];
        // TODO
    }

    @Override
    public Path[] write(Set<Node> input) {
        return new Path[0];
        // TODO
    }

    @Override
    public void addListener(WriteListener listener) {
        // TODO
    }

    @Override
    public void removeListener(WriteListener listener) {
        // TODO
    }
}
package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.ArtifactReader;
import at.jku.isse.ecco.service.listener.ReadListener;
import at.jku.isse.ecco.tree.Node;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class jolieReader implements ArtifactReader<Path, Set<Node.Op>> {

    @Override
    public String getPluginId() {
        return "";
        // TODO
    }

    @Override
    public Map<Integer, String[]> getPrioritizedPatterns() {
        return Map.of();
        // TODO
    }

    @Override
    public Set<Node.Op> read(Path base, Path[] input) {
        return Set.of();
        // TODO
    }

    @Override
    public Set<Node.Op> read(Path[] input) {
        return Set.of();
        // TODO
    }

    @Override
    public void addListener(ReadListener listener) {
        // TODO
    }

    @Override
    public void removeListener(ReadListener listener) {
        // TODO
    }
}

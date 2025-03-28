package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.artifact.ArtifactData;

import java.util.Objects;

public class JolieContextArtifactData implements ArtifactData {
    private final NodeTypes type;

    public JolieContextArtifactData(NodeTypes type) {
        this.type = type;
    }

    public NodeTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        JolieContextArtifactData that = (JolieContextArtifactData) object; // cast
        return Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}

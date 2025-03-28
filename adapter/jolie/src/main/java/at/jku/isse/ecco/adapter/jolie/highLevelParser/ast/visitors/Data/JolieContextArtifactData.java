package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data;

import at.jku.isse.ecco.artifact.ArtifactData;

import java.util.Objects;

public class JolieContextArtifactData implements ArtifactData {
    @Override
    public boolean equals(Object object) {
        return this == object || object != null && getClass() == object.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash("");
    }
}

package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.artifact.ArtifactData;

import java.util.Objects;

public class JolieLineArtifactData implements ArtifactData {
    private final String lineContents;
    private final int line;
    private final String postLexeme;

    public JolieLineArtifactData(String lineContents, int line, String postLexeme) {
        this.lineContents = lineContents;
        this.line = line;
        this.postLexeme = postLexeme;
    }

    public String getLineContents() {
        return lineContents;
    }

    public int getLine() {
        return line;
    }

    public String getPostLexeme() {
        return postLexeme;
    }

    @Override
    public String toString() {
        return "LineArtifactData{" + "\n" +
                "   lineContents: " + this.lineContents + "\n" +
                "   line: " +  this.line + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        JolieLineArtifactData that = (JolieLineArtifactData) object; // cast
        return Objects.equals(getLineContents(), that.getLineContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLineContents(), getLine());
    }

}
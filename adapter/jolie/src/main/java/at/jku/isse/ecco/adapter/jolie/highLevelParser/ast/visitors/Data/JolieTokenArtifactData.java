package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.artifact.ArtifactData;

import java.util.Objects;

public class JolieTokenArtifactData implements ArtifactData {
    public final JolieTokenType type;
    public final String preWhitespace;
    public final String lexeme;
    public final String postWhitespace;

    public JolieTokenArtifactData(JolieToken token) {
        this.type = token.getType();
        this.preWhitespace = token.getPreLexeme();
        this.lexeme = token.getLexeme();
        this.postWhitespace = token.getPostLexeme();
    }

    public JolieTokenType getType() {
        return type;
    }

    public String getPreWhitespace() {
        return preWhitespace;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getPostWhitespace() {
        return postWhitespace;
    }


    @Override
    public String toString() {
        return "TokenArtifactData{" + "\n" +
                "   tokenType: " + this.type + "\n" +
                "   preWhitespace: " + this.preWhitespace + "\n" +
                "   lexeme: " + this.lexeme + "\n" +
                "   postWhitespace: " + this.postWhitespace + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        JolieTokenArtifactData that = (JolieTokenArtifactData) object; // cast
        return getType() == that.getType() &&
                Objects.equals(getPreWhitespace(), that.getPreWhitespace()) &&
                Objects.equals(getLexeme(), that.getLexeme()) &&
                Objects.equals(getPostWhitespace(), that.getPostWhitespace());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getPreWhitespace(), getLexeme(), getPostWhitespace());
    }

}

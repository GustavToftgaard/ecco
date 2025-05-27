package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.artifact.ArtifactData;

import java.util.Objects;

public class JolieTokenArtifactData implements ArtifactData {
    public final JolieTokenType type;
    public final String preLexeme;
    public final String lexeme;
    public final String postLexeme;
    public final int line;

    public JolieTokenArtifactData(JolieToken token) {
        this.type = token.getType();
        this.preLexeme = token.getPreLexeme();
        this.lexeme = token.getLexeme();
        this.postLexeme = token.getPostLexeme();
        this.line = token.getLine();
    }

    public JolieTokenType getType() {
        return type;
    }

    public String getPreLexeme() {
        return preLexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getPostLexeme() {
        return postLexeme;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String toString() {
        return "TokenArtifactData{" + "\n" +
                "   tokenType: " + this.type + "\n" +
                "   preWhitespace: " + this.preLexeme + "\n" +
                "   lexeme: " + this.lexeme + "\n" +
                "   postWhitespace: " + this.postLexeme + "\n" +
                "   line: " +  this.line + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        JolieTokenArtifactData that = (JolieTokenArtifactData) object; // cast
        return getType() == that.getType() &&
                Objects.equals(getPreLexeme(), that.getPreLexeme()) &&
                Objects.equals(getLexeme(), that.getLexeme()) &&
                Objects.equals(getPostLexeme(), that.getPostLexeme());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getPreLexeme(), getLexeme(), getPostLexeme(), getLine());
    }

}

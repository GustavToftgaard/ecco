package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.artifact.ArtifactData;

import java.util.Objects;

public class JolieTokenArtifactData implements ArtifactData {
    public final JolieTokenType type;
    public final String lexeme;
    public final int line;
    public final int numberTokenInLine;

    public JolieTokenArtifactData(JolieToken token) {
        this.type = token.type;
        this.lexeme = token.lexeme;
        this.line = token.line;
        this.numberTokenInLine = token.numberTokenInLine;
    }

    public JolieTokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }

    public int getNumberTokenInLine() {
        return numberTokenInLine;
    }

    @Override
    public String toString() {
        return "TokenArtifactData{" +
                "   tokenType: " + this.type + "\n" +
                "   lexeme: " + this.lexeme + "\n" +
                "   line: " +  this.line + "\n" +
                "   numberInLine: " +  this.numberTokenInLine + "\n" +
                "}";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        JolieTokenArtifactData that = (JolieTokenArtifactData) object; // cast
        return getType() == that.getType() &&
                Objects.equals(getLexeme(), that.getLexeme()) &&
                getLine() == that.getLine() &&
                getNumberTokenInLine() == that.getNumberTokenInLine();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getLexeme(), getLine(), getNumberTokenInLine());
    }

}

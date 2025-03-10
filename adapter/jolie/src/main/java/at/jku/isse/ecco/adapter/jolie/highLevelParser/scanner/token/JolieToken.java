package at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token;



public class JolieToken {
    public final JolieTokenType type;
    public final String lexeme;
    public final Object literal;
    public final int line;
    public final int numberTokenInLine;


    public JolieToken(JolieTokenType type, String lexeme, int line, int numberTokenInLine) {
        this(type, lexeme, null, line, numberTokenInLine);
    }

    public JolieToken(JolieTokenType type, String lexeme, Object literal, int line, int numberTokenInLine) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
        this.numberTokenInLine = numberTokenInLine;
    }

    public String toString() {
        return "<" + type + "," + lexeme + "> " + "Literal: " + literal + ", Line: " + line + ", Number Token In Line: " + numberTokenInLine;
    }
}

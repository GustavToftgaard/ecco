package at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token;



public class JolieToken {
    private final JolieTokenType type;
    private String preLexeme;
    private final String lexeme;
    private String postLexeme;
    private final int line;

    public JolieToken(JolieTokenType type, String preWhitespace, String lexeme, String postWhitespace, int line) {
        this.type = type;
        this.preLexeme = preWhitespace;
        this.lexeme = lexeme;
        this.postLexeme = postWhitespace;
        this.line = line;
    }

    public JolieToken(JolieTokenType type, String preWhitespace, String lexeme, int line) {
        this.type = type;
        this.preLexeme = preWhitespace;
        this.lexeme = lexeme;
        this.postLexeme = "";
        this.line = line;
    }

    public void setPreLexeme(String preLexeme) {
        this.preLexeme = preLexeme;
    }

    public void setPostLexeme(String postLexeme) {
        this.postLexeme = postLexeme;
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

    public String toString() {
        return "<" + type + "," + lexeme + "> " + ", Line: " + line;
    }
}

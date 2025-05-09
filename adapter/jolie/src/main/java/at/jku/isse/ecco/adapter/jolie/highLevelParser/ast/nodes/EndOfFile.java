package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class EndOfFile implements Node {
    private String postLexeme = "";
    private final JolieToken endOfFileToken;

    public EndOfFile(JolieToken endOfFileToken) {
        this.endOfFileToken = endOfFileToken;
    }

    public JolieToken getEndOfFileToken() {
        return endOfFileToken;
    }

    @Override
    public String getPostLexeme() {
        return postLexeme;
    }

    @Override
    public void setPostLexeme(String postLexeme) {
        this.postLexeme = postLexeme;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitEndOfFile(this);
    }
}
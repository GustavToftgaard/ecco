package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class Embed implements Node {
    private String postLexeme = "";
    public final JolieToken embedID;
    public final ArrayList<JolieToken> params;
    public final JolieToken asID;

    public Embed(JolieToken embedID, JolieToken asID, ArrayList<JolieToken> params) {
        this.embedID = embedID;
        this.params = params;
        this.asID = asID;
    }

    public JolieToken getEmbedID() {
        return embedID;
    }

    public ArrayList<JolieToken> getParams() {
        return params;
    }

    public JolieToken getAsID() {
        return asID;
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
        return visitor.visitEmbed(this);
    }
}
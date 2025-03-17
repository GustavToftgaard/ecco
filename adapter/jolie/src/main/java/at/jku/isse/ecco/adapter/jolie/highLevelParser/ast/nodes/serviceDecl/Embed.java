package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class Embed implements Node {
    public final JolieToken embedID;
    public final JolieToken asID;

    public Embed(JolieToken embedID, JolieToken asID) {
        this.embedID = embedID;
        this.asID = asID;
    }

    public JolieToken getEmbedID() {
        return embedID;
    }

    public JolieToken getAsID() {
        return asID;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitEmbed(this);
    }
}

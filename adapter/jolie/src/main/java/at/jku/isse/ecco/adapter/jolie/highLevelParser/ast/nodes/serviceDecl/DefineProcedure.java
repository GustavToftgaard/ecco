package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class DefineProcedure implements Node {
    private String postLexeme = "";
    private final JolieToken defineID;
    private final Block block;

    public DefineProcedure(JolieToken defineID, Block block) {
        this.defineID = defineID;
        this.block = block;
    }

    public JolieToken getDefineID() {
        return defineID;
    }

    public Block getBlock() {
        return block;
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
        return visitor.visitDefineProcedure(this);
    }
}
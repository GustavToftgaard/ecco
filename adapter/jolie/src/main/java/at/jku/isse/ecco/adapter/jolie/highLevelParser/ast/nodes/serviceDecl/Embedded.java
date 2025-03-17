package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;

public class Embedded implements Node {
    public final Block block;

    public Embedded(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitEmbedded(this);
    }
}
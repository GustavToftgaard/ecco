package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.typeDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class TypeDecl implements Node {
    private final JolieToken typeID;
    private final JolieToken secondID; //?
    private final Block block; //?

    public TypeDecl(JolieToken typeID, JolieToken secondID) {
        this.typeID = typeID;
        this.secondID = secondID;
        this.block = null;
    }

    public TypeDecl(JolieToken typeID, Block block) {
        this.typeID = typeID;
        this.secondID = null;
        this.block = block;
    }

    public TypeDecl(JolieToken typeID, JolieToken secondID, Block block) {
        this.typeID = typeID;
        this.secondID = secondID;
        this.block = block;
    }

    public JolieToken getTypeID() {
        return typeID;
    }

    public JolieToken getSecondID() {
        return secondID;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitTypeDecl(this);
    }
}

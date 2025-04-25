package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.typeDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class TypeDecl implements Node {
    private final JolieToken typeID;
    private final ArrayList<JolieToken> typeTypesID; //?
    private final Block block; //?

    public TypeDecl(JolieToken typeID, ArrayList<JolieToken> typeTypesID, Block block) {
        this.typeID = typeID;
        this.typeTypesID = typeTypesID;
        this.block = block;
    }

    public JolieToken getTypeID() {
        return typeID;
    }

    public ArrayList<JolieToken> getTypeTypesID() {
        return typeTypesID;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitTypeDecl(this);
    }
}

package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class InterfaceDecl implements Node {
    private final JolieToken interfaceID;
    private final Boolean hasExtender;
    private final Block block;

    public InterfaceDecl(JolieToken interfaceID, Boolean hasExtender, Block block) {
        this.interfaceID = interfaceID;
        this.hasExtender = hasExtender;
        this.block = block;
    }

    public JolieToken getInterfaceID() {
        return interfaceID;
    }

    public Boolean getHasExtender() {
        return hasExtender;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitInterfaceDecl(this);
    }
}

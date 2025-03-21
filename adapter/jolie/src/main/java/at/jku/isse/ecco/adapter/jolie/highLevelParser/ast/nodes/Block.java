package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class Block implements Node {
    private final ArrayList<JolieToken> contents;

    public Block(ArrayList<JolieToken> contents) {
        this.contents = contents;
    }

    public ArrayList<JolieToken> getContents() {
        return contents;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitBlock(this);
    }
}

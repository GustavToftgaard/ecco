package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;

public class Block implements Node {
    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return null;
    }
}

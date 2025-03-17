package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;

public class Service implements Node {
    private final Node node;
    private final Line line;

    public Service(Node node) {
        this.node = node;
        this.line = null;
    }

    public Service(Line line) {
        this.node = null;
        this.line = line;
    }

    public Node getNode() {
        return node;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitService(this);
    }
}

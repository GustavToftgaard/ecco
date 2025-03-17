package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;

public class PortLocation implements Node {
    private final Line line;
    private final Boolean isCapital;

    public PortLocation(Line line, Boolean isCapital) {
        this.line = line;
        this.isCapital = isCapital;
    }

    public PortLocation(Line line) {
        this.line = line;
        this.isCapital = null;
    }

    public Line getLine() {
        return line;
    }

    public Boolean getCapital() {
        return isCapital;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitPortLocation(this);
    }
}

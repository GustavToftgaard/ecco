package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class PortLocation implements Node {
    private final Line line;
    private final JolieToken isCapital;

    public PortLocation(Line line, JolieToken isCapital) {
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

    public JolieToken getIsCapital() {
        return isCapital;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitPortLocation(this);
    }
}

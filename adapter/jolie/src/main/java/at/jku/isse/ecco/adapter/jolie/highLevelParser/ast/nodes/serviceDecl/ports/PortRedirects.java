package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class PortRedirects implements Node {
    private final ArrayList<Line> lines;
    private final JolieToken isCapital;

    public PortRedirects(ArrayList<Line> lines, JolieToken isCapital) {
        this.lines = lines;
        this.isCapital = isCapital;
    }

    public PortRedirects(ArrayList<Line> lines) {
        this.lines = lines;
        this.isCapital = null;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public JolieToken getIsCapital() {
        return isCapital;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitPortRedirects(this);
    }
}

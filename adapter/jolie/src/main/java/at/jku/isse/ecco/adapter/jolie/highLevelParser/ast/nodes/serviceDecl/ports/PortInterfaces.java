package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class PortInterfaces implements Node {
    private final ArrayList<Line> lines;

    public PortInterfaces(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitPortInterfaces(this);
    }
}

package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class PortAggregates implements Node {
    private final ArrayList<JolieToken> arguments;

    public PortAggregates(ArrayList<JolieToken> arguments) {
        this.arguments = arguments;
    }

    public ArrayList<JolieToken> getArguments() {
        return arguments;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitPortAggregates(this);
    }
}

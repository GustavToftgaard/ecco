package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class InputPort implements Node {
    private final JolieToken inputPortID;
    private final ArrayList<Node> portParameters;
//    private PortLocation location; // ?
//    private PortProtocol protocol; // ?
//    private PortInterfaces interfaces; // ?
//    private PortAggregates aggregates; // ?
//    private PortRedirects redirects; // ?


    public InputPort(JolieToken inputPortID, ArrayList<Node> portParameters) {
        this.inputPortID = inputPortID;
        this.portParameters = portParameters;
    }

    public JolieToken getInputPortID() {
        return inputPortID;
    }

    public ArrayList<Node> getPortParameters() {
        return portParameters;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitInputPort(this);
    }
}

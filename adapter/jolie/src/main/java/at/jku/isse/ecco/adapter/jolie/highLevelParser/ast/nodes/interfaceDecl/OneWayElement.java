package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class OneWayElement implements Node {
    private final JolieToken functionID;
    private final JolieToken requestID;

    public OneWayElement(JolieToken functionID, JolieToken requestID) {
        this.functionID = functionID;
        this.requestID = requestID;
    }

    public JolieToken getFunctionID() {
        return functionID;
    }

    public JolieToken getRequestID() {
        return requestID;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitOneWayElement(this);
    }
}

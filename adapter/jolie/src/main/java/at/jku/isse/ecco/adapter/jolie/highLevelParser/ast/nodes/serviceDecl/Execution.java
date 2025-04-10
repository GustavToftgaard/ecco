package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class Execution implements Node {
    private final JolieToken executionID;

    public Execution(JolieToken executionID) {
        this.executionID = executionID;
    }

    public JolieToken getExecutionID() {
        return executionID;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitExecution(this);
    }
}

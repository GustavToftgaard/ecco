package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class Execution implements Node {
    private final JolieToken executionID;
    private final JolieToken usesColon;

    public Execution(JolieToken executionID, JolieToken usesColon) {
        this.executionID = executionID;
        this.usesColon = usesColon;
    }

    public Execution(JolieToken executionID) {
        this.executionID = executionID;
        this.usesColon = null;
    }

    public JolieToken getExecutionID() {
        return executionID;
    }

    public JolieToken getUsesColon() {
        return usesColon;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitExecution(this);
    }
}

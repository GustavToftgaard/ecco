package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class OutputPort implements Node {
    private String postLexeme = "";
    private final JolieToken outputPortID;
    private final ArrayList<Node> portParameters;

    public OutputPort(JolieToken outputPortID, ArrayList<Node> portParameters) {
        this.outputPortID = outputPortID;
        this.portParameters = portParameters;
    }

    public JolieToken getOutputPortID() {
        return outputPortID;
    }

    public ArrayList<Node> getPortParameters() {
        return portParameters;
    }

    @Override
    public String getPostLexeme() {
        return postLexeme;
    }

    @Override
    public void setPostLexeme(String postLexeme) {
        this.postLexeme = postLexeme;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitOutputPort(this);
    }
}
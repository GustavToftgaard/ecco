package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class RequestResponseElement implements Node {
    private String postLexeme = "";
    private final JolieToken functionID;
    private final JolieToken requestID;
    private final JolieToken responseID;

    public RequestResponseElement(JolieToken functionID, JolieToken requestID, JolieToken responseID) {
        this.functionID = functionID;
        this.requestID = requestID;
        this.responseID = responseID;
    }

    public JolieToken getFunctionID() {
        return functionID;
    }

    public JolieToken getRequestID() {
        return requestID;
    }

    public JolieToken getResponseID() {
        return responseID;
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
        return visitor.visitRequestResponseElement(this);
    }
}
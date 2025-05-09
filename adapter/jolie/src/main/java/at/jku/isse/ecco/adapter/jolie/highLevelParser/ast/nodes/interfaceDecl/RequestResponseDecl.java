package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;

import java.util.ArrayList;

public class RequestResponseDecl implements Node {
    private String postLexeme = "";
    private final ArrayList<RequestResponseElement> requestResponseElements;

    public RequestResponseDecl(ArrayList<RequestResponseElement> requestResponseElements) {
        this.requestResponseElements = requestResponseElements;
    }

    public ArrayList<RequestResponseElement> getRequestResponseElements() {
        return requestResponseElements;
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
        return visitor.visitRequestResponseDecl(this);
    }
}
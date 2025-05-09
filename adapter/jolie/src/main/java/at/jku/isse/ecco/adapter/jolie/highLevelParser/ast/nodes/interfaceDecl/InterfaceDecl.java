package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class InterfaceDecl implements Node {
    private String postLexeme = "";
    private final JolieToken interfaceID;
    private final RequestResponseDecl requestResponse;
    private final OneWayDecl oneWay;

    public InterfaceDecl(JolieToken interfaceID, RequestResponseDecl requestResponse, OneWayDecl oneWay) {
        this.interfaceID = interfaceID;
        this.requestResponse = requestResponse;
        this.oneWay = oneWay;
    }

    public JolieToken getInterfaceID() {
        return interfaceID;
    }

    public RequestResponseDecl getRequestResponse() {
        return requestResponse;
    }

    public OneWayDecl getOneWay() {
        return oneWay;
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
        return visitor.visitInterfaceDecl(this);
    }
}
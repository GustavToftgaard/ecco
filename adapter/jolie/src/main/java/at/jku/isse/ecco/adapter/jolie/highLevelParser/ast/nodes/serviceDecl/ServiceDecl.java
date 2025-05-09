package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class ServiceDecl implements Node {
    private String postLexeme = "";
    private final JolieToken serviceID;
    private final ArrayList<JolieToken> params; // ?
    private final ArrayList<Node> services;

    public ServiceDecl(JolieToken serviceID, ArrayList<Node> services, ArrayList<JolieToken> params) {
        this.serviceID = serviceID;
        this.services = services;
        this.params = params;
    }

    public JolieToken getServiceID() {
        return serviceID;
    }

    public ArrayList<JolieToken> getParams() {
        return params;
    }

    public ArrayList<Node> getServices() {
        return services;
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
        return visitor.visitServiceDecl(this);
    }
}
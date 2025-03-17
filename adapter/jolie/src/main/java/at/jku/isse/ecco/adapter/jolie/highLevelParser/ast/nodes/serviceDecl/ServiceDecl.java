package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class ServiceDecl implements Node {
    private final JolieToken serviceID;
    private ArrayList<JolieToken> params; // ?
    private final ArrayList<Service> services;

    public ServiceDecl(JolieToken serviceID, ArrayList<Service> services) {
        this.serviceID = serviceID;
        this.services = services;
    }

    public ServiceDecl(JolieToken serviceID, ArrayList<Service> services, ArrayList<JolieToken> params) {
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

    public ArrayList<Service> getServices() {
        return services;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitServiceDecl(this);
    }
}

package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.Import;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.ImportDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.Include;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl.InterfaceDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.typeDecl.TypeDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.artifact.Artifact;
import at.jku.isse.ecco.dao.EntityFactory;

import java.nio.file.Path;
import java.util.List;

public class AstToECCO implements NodeVisitor<at.jku.isse.ecco.tree.Node.Op> {
    private at.jku.isse.ecco.tree.Node.Op rootNode;
    private final EntityFactory entityFactory;
    private final Path path;

    public AstToECCO(at.jku.isse.ecco.tree.Node.Op rootNode, EntityFactory entityFactory, Path path) {
        this.rootNode = rootNode;
        this.entityFactory = entityFactory;
        this.path = path;
    }

    public at.jku.isse.ecco.tree.Node.Op translate(List<Node> nodes) {
        for (Node node : nodes) {
            rootNode.addChild(node.accept(this));
        }

        return rootNode;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitImportDecl(ImportDecl importDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        if (importDecl.getImportE() != null) {
            node.addChild(importDecl.getImportE().accept(this));
        } else {
            node.addChild(importDecl.getInclude().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitImport(Import importE) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        node.addChild(createTokenNode(importE.getFromID()));
        node.addChild(createTokenNode(importE.getImportID()));

        if (importE.getLine() != null) {
            node.addChild(importE.getLine().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInclude(Include include) {
        return createTokenNode(include.getIncludeID());
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInterfaceDecl(InterfaceDecl interfaceDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(interfaceDecl.getInterfaceID());

        // TODO: use token instead of boolean for hasExtender

        node.addChild(interfaceDecl.getBlock().accept(this));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitTypeDecl(TypeDecl typeDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(typeDecl.getTypeID());

        if (typeDecl.getSecondID() != null) {
            node.addChild(createTokenNode(typeDecl.getSecondID()));
        }

        if (typeDecl.getBlock() != null) {
            node.addChild(typeDecl.getBlock().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitServiceDecl(ServiceDecl serviceDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(serviceDecl.getServiceID());

        if (serviceDecl.getParams() != null) {
            at.jku.isse.ecco.tree.Node.Op paramsNode = createContextNode();
            for (JolieToken param : serviceDecl.getParams()) {
                paramsNode.addChild(createTokenNode(param));
            }
            node.addChild(paramsNode);
        }

        at.jku.isse.ecco.tree.Node.Op servicesNode = createContextNode();
        for (Node service : serviceDecl.getServices()) {
            servicesNode.addChild(service.accept(this));
        }
        node.addChild(servicesNode);

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitService(Service service) {
        if (service.getNode() != null) {
            return service.getNode().accept(this);
        } else {
            return service.getLine().accept(this);
        }
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitExecution(Execution execution) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(execution.getExecutionID());

        if (execution.getUsesColon() != null) {
            node.addChild(createTokenNode(execution.getUsesColon()));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEmbed(Embed embed) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(embed.getEmbedID());

        node.addChild(createTokenNode(embed.getAsID()));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEmbedded(Embedded embedded) {
        return embedded.getBlock().accept(this);
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInputPort(InputPort inputPort) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(inputPort.getInputPortID());

        at.jku.isse.ecco.tree.Node.Op portParameterNode = createContextNode();
        for (Node service : inputPort.getPortParameters()) {
            portParameterNode.addChild(service.accept(this));
        }
        node.addChild(portParameterNode);

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitOutputPort(OutputPort outputPort) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(outputPort.getOutputPortID());

        at.jku.isse.ecco.tree.Node.Op portParameterNode = createContextNode();
        for (Node service : outputPort.getPortParameters()) {
            portParameterNode.addChild(service.accept(this));
        }
        node.addChild(portParameterNode);

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortLocation(PortLocation portLocation) {
        return portLocation.getLine().accept(this);
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortProtocol(PortProtocol portProtocol) {
        return portProtocol.getLine().accept(this);
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortInterfaces(PortInterfaces portInterfaces) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        for (Node line : portInterfaces.getLines()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortAggregates(PortAggregates portAggregates) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        for (Node line : portAggregates.getLines()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortRedirects(PortRedirects portRedirects) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        for (Node line : portRedirects.getLines()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInit(Init init) {
        return init.getBlock().accept(this);
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitMain(Main main) {
        return main.getBlock().accept(this);
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitCourier(Courier courier) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(courier.getCourierInterfaceID());
        node.addChild(courier.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitDefineProcedure(DefineProcedure defineProcedure) {
        at.jku.isse.ecco.tree.Node.Op node = createTokenNode(defineProcedure.getDefineID());
        node.addChild(defineProcedure.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitBlock(Block block) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        for (JolieToken token : block.getContents()) {
            node.addChild(createTokenNode(token));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitLine(Line line) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode();

        for (JolieToken token : line.getContents()) {
            node.addChild(createTokenNode(token));
        }

        return node;
    }

    // -----------------

    private at.jku.isse.ecco.tree.Node.Op createTokenNode(JolieToken token) {
        Artifact.Op<JolieTokenArtifactData> tokenArtifactData =
                this.entityFactory.createArtifact(new JolieTokenArtifactData(token));
        return this.entityFactory.createNode(tokenArtifactData);
    }

    private at.jku.isse.ecco.tree.Node.Op createContextNode() {
        Artifact.Op<JolieContextArtifactData> contextArtifactData =
                this.entityFactory.createArtifact(new JolieContextArtifactData());
        return this.entityFactory.createNode(contextArtifactData);
    }
}

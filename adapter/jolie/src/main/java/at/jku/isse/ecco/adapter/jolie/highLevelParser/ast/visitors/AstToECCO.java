package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.Import;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.ImportDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.Include;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl.InterfaceDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.typeDecl.TypeDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
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
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.IMPORTDECL);

        if (importDecl.getImportE() != null) {
            node.addChild(importDecl.getImportE().accept(this));
        } else {
            node.addChild(importDecl.getInclude().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitImport(Import importE) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.IMPORT);

        node.addChild(createTokenNode(importE.getFromID()));
        node.addChild(createTokenNode(importE.getImportID()));

        if (importE.getLine() != null) {
            node.addChild(importE.getLine().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInclude(Include include) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INCLUDE);
        node.addChild(createTokenNode(include.getIncludeID()));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInterfaceDecl(InterfaceDecl interfaceDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INTERFACEDECL);
        node.addChild(createTokenNode(interfaceDecl.getInterfaceID()));

        if (interfaceDecl.getHasExtender() != null) {
            node.addChild(createTokenNode(interfaceDecl.getHasExtender()));
        }

        node.addChild(interfaceDecl.getBlock().accept(this));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitTypeDecl(TypeDecl typeDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.TYPEDECL);
        node.addChild(createTokenNode(typeDecl.getTypeID()));

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
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.SERVICEDECL);

        node.addChild(createTokenNode(serviceDecl.getServiceID()));

        if (serviceDecl.getParams() != null) {
            for (JolieToken param : serviceDecl.getParams()) {
                node.addChild(createTokenNode(param));
            }
        }

        for (Node service : serviceDecl.getServices()) {
            node.addChild(service.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitService(Service service) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.SERVICE);

        if (service.getNode() != null) {
            node.addChild(service.getNode().accept(this));
        } else {
            node.addChild(service.getLine().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitExecution(Execution execution) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EXECUTION);

        if (execution.getUsesColon() != null) {
            node.addChild(createTokenNode(execution.getUsesColon()));
        }

        node.addChild(createTokenNode(execution.getExecutionID()));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEmbed(Embed embed) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EMBED);
        node.addChild(createTokenNode(embed.getEmbedID()));
        node.addChild(createTokenNode(embed.getAsID()));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEmbedded(Embedded embedded) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EMBEDDED);
        node.addChild(embedded.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInputPort(InputPort inputPort) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INPUTPORT);
        node.addChild(createTokenNode(inputPort.getInputPortID()));

        for (Node service : inputPort.getPortParameters()) {
            node.addChild(service.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitOutputPort(OutputPort outputPort) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.OUTPUTPORT);
        node.addChild(createTokenNode(outputPort.getOutputPortID()));

        for (Node service : outputPort.getPortParameters()) {
            node.addChild(service.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortLocation(PortLocation portLocation) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTLOCATION);
        if (portLocation.getIsCapital() != null) {
            node.addChild(createTokenNode(portLocation.getIsCapital()));
        }
        node.addChild(portLocation.getLine().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortProtocol(PortProtocol portProtocol) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTPROTOCOL);
        if (portProtocol.getIsCapital() != null) {
            node.addChild(createTokenNode(portProtocol.getIsCapital()));
        }
        node.addChild(portProtocol.getLine().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortInterfaces(PortInterfaces portInterfaces) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTINTERFACES);
        if (portInterfaces.getIsCapital() != null) {
            node.addChild(createTokenNode(portInterfaces.getIsCapital()));
        }

        for (Node line : portInterfaces.getLines()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortAggregates(PortAggregates portAggregates) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTAGGREGATES);
        if (portAggregates.getIsCapital() != null) {
            node.addChild(createTokenNode(portAggregates.getIsCapital()));
        }

        for (Node line : portAggregates.getLines()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortRedirects(PortRedirects portRedirects) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTREDIRECTS);
        if (portRedirects.getIsCapital() != null) {
            node.addChild(createTokenNode(portRedirects.getIsCapital()));
        }

        for (Node line : portRedirects.getLines()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInit(Init init) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INIT);
        node.addChild(init.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitMain(Main main) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.MAIN);
        node.addChild(main.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitCourier(Courier courier) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.COURIER);
        node.addChild(createTokenNode(courier.getCourierInterfaceID()));
        node.addChild(courier.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitDefineProcedure(DefineProcedure defineProcedure) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.DEFINEPROCEDURE);
        node.addChild(createTokenNode(defineProcedure.getDefineID()));
        node.addChild(defineProcedure.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitBlock(Block block) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.BLOCK);

        for (JolieToken token : block.getContents()) {
            node.addChild(createTokenNode(token));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitLine(Line line) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.LINE);

        String lineContents = line.getLineContents();
        node.addChild(createLineNode(lineContents, line.getLine()));

        return node;
    }

    // -----------------

    private at.jku.isse.ecco.tree.Node.Op createTokenNode(JolieToken token) {
        Artifact.Op<JolieTokenArtifactData> tokenArtifactData =
                this.entityFactory.createArtifact(new JolieTokenArtifactData(token));
        return this.entityFactory.createOrderedNode(tokenArtifactData);
    }

    private at.jku.isse.ecco.tree.Node.Op createLineNode(String lineContents, int line) {
        Artifact.Op<JolieLineArtifactData> lineArtifactData =
                this.entityFactory.createArtifact(new JolieLineArtifactData(lineContents, line));
        return this.entityFactory.createOrderedNode(lineArtifactData);
    }

    private at.jku.isse.ecco.tree.Node.Op createContextNode(NodeTypes type) {
        Artifact.Op<JolieContextArtifactData> contextArtifactData =
                this.entityFactory.createArtifact(new JolieContextArtifactData(type));
        return this.entityFactory.createOrderedNode(contextArtifactData);
    }
}

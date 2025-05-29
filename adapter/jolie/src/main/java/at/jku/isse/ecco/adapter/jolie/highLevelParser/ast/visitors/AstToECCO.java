package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Block;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.EndOfFile;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.Import;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.ImportDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.Include;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl.*;
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
    private final at.jku.isse.ecco.tree.Node.Op rootNode;
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
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.IMPORTDECL, importDecl.getPostLexeme());

        if (importDecl.getImportE() != null) {
            node.addChild(importDecl.getImportE().accept(this));
        } else {
            node.addChild(importDecl.getInclude().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitImport(Import importE) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.IMPORT, importE.getPostLexeme());

        node.addChild(createTokenNode(importE.getFromID()));
        node.addChild(createTokenNode(importE.getImportID()));

        if (importE.getLine() != null) {
            node.addChild(importE.getLine().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInclude(Include include) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INCLUDE, include.getPostLexeme());
        node.addChild(createTokenNode(include.getIncludeID()));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInterfaceDecl(InterfaceDecl interfaceDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INTERFACEDECL, interfaceDecl.getPostLexeme());
        node.addChild(createTokenNode(interfaceDecl.getInterfaceID()));

        if (interfaceDecl.getRequestResponse() != null) {
            node.addChild(interfaceDecl.getRequestResponse().accept(this));
        }
        if (interfaceDecl.getOneWay() != null) {
            node.addChild(interfaceDecl.getOneWay().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitRequestResponseDecl(RequestResponseDecl requestResponseDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.REQUEST_RESPONSE_DECL, requestResponseDecl.getPostLexeme());

        for (RequestResponseElement element : requestResponseDecl.getRequestResponseElements()) {
            node.addChild(element.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitRequestResponseElement(RequestResponseElement requestResponseElement) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.REQUEST_RESPONSE_ELEMENT, requestResponseElement.getPostLexeme());

        node.addChild(createTokenNode(requestResponseElement.getFunctionID()));
        node.addChild(createTokenNode(requestResponseElement.getRequestID()));
        node.addChild(createTokenNode(requestResponseElement.getResponseID()));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitOneWayDecl(OneWayDecl oneWayDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.ONE_WAY_DECL, oneWayDecl.getPostLexeme());

        for (OneWayElement element : oneWayDecl.getOneWayElements()) {
            node.addChild(element.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitOneWayElement(OneWayElement oneWayElement) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.ONE_WAY_ELEMENT, oneWayElement.getPostLexeme());

        node.addChild(createTokenNode(oneWayElement.getFunctionID()));
        node.addChild(createTokenNode(oneWayElement.getRequestID()));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitTypeDecl(TypeDecl typeDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.TYPEDECL, typeDecl.getPostLexeme());
        node.addChild(createTokenNode(typeDecl.getTypeID()));

        if (typeDecl.getTypeTypesID() != null) {
            for (JolieToken token : typeDecl.getTypeTypesID()) {
                node.addChild(createTokenNode(token));
            }
        }

        if (typeDecl.getBlock() != null) {
            node.addChild(typeDecl.getBlock().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitServiceDecl(ServiceDecl serviceDecl) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.SERVICEDECL, serviceDecl.getPostLexeme());

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
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.SERVICE, service.getPostLexeme());

        if (service.getNode() != null) {
            node.addChild(service.getNode().accept(this));
        } else {
            node.addChild(service.getLine().accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitExecution(Execution execution) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EXECUTION, execution.getPostLexeme());

        node.addChild(createTokenNode(execution.getExecutionID()));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEmbed(Embed embed) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EMBED, embed.getPostLexeme());

        node.addChild(createTokenNode(embed.getEmbedID()));

        if (embed.getParams() != null) {
            for (JolieToken param : embed.getParams()) {
                node.addChild(createTokenNode(param));
            }
        }

        node.addChild(createTokenNode(embed.getAsID()));

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEmbedded(Embedded embedded) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EMBEDDED, embedded.getPostLexeme());
        node.addChild(embedded.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInputPort(InputPort inputPort) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INPUTPORT, inputPort.getPostLexeme());
        node.addChild(createTokenNode(inputPort.getInputPortID()));

        for (Node service : inputPort.getPortParameters()) {
            node.addChild(service.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitOutputPort(OutputPort outputPort) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.OUTPUTPORT, outputPort.getPostLexeme());
        node.addChild(createTokenNode(outputPort.getOutputPortID()));

        for (Node service : outputPort.getPortParameters()) {
            node.addChild(service.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortLocation(PortLocation portLocation) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTLOCATION, portLocation.getPostLexeme());

        for (JolieToken argument : portLocation.getArguments()) {
            node.addChild(createTokenNode(argument));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortProtocol(PortProtocol portProtocol) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTPROTOCOL, portProtocol.getPostLexeme());

        for (JolieToken argument : portProtocol.getArguments()) {
            node.addChild(createTokenNode(argument));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortInterfaces(PortInterfaces portInterfaces) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTINTERFACES, portInterfaces.getPostLexeme());

        for (JolieToken token : portInterfaces.getArguments()) {
            node.addChild(createTokenNode(token));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortAggregates(PortAggregates portAggregates) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTAGGREGATES, portAggregates.getPostLexeme());

        for (JolieToken token : portAggregates.getArguments()) {
            node.addChild(createTokenNode(token));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitPortRedirects(PortRedirects portRedirects) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.PORTREDIRECTS, portRedirects.getPostLexeme());

        for (JolieToken token : portRedirects.getArguments()) {
            node.addChild(createTokenNode(token));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitInit(Init init) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.INIT, init.getPostLexeme());
        node.addChild(init.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitMain(Main main) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.MAIN, main.getPostLexeme());
        node.addChild(main.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitCourier(Courier courier) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.COURIER, courier.getPostLexeme());
        node.addChild(createTokenNode(courier.getCourierInterfaceID()));
        node.addChild(courier.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitDefineProcedure(DefineProcedure defineProcedure) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.DEFINEPROCEDURE, defineProcedure.getPostLexeme());
        node.addChild(createTokenNode(defineProcedure.getDefineID()));
        node.addChild(defineProcedure.getBlock().accept(this));
        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitBlock(Block block) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.BLOCK, block.getPostLexeme());

        for (Line line : block.getContents()) {
            node.addChild(line.accept(this));
        }

        return node;
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitLine(Line line) {
        return createLineNode(line.getLineContents(), line.getPostLexeme());
    }

    @Override
    public at.jku.isse.ecco.tree.Node.Op visitEndOfFile(EndOfFile endOfFile) {
        at.jku.isse.ecco.tree.Node.Op node = createContextNode(NodeTypes.EOF, endOfFile.getPostLexeme());

        node.addChild(createTokenNode(endOfFile.getEndOfFileToken()));

        return node;
    }

    // -----------------

    private at.jku.isse.ecco.tree.Node.Op createTokenNode(JolieToken token) {
        Artifact.Op<JolieTokenArtifactData> tokenArtifactData =
                this.entityFactory.createArtifact(new JolieTokenArtifactData(token));
        return this.entityFactory.createOrderedNode(tokenArtifactData);
    }

    private at.jku.isse.ecco.tree.Node.Op createLineNode(String lineContents, String postLexeme) {
        Artifact.Op<JolieLineArtifactData> lineArtifactData =
                this.entityFactory.createArtifact(new JolieLineArtifactData(lineContents, postLexeme));
        return this.entityFactory.createOrderedNode(lineArtifactData);
    }

    private at.jku.isse.ecco.tree.Node.Op createContextNode(NodeTypes type, String postLexeme) {
        Artifact.Op<JolieContextArtifactData> contextArtifactData =
                this.entityFactory.createArtifact(new JolieContextArtifactData(type, postLexeme));
        return this.entityFactory.createOrderedNode(contextArtifactData);
    }
}
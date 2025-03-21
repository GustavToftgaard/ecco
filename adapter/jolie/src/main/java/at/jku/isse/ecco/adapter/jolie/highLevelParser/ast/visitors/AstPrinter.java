package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
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
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.List;

public class AstPrinter implements NodeVisitor<String> {
    private int indentLevel = 0;

    private String indent() {
        return "  ".repeat(indentLevel);
    }

    public String print(List<Node> nodes) {
        StringBuilder res = new StringBuilder();
        for (Node node : nodes) {
            res.append(node.accept(this));
        }
        return res.toString();
    }

    @Override
    public String visitImportDecl(ImportDecl importDecl) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("ImportDecl").append("\n");
        indentLevel++;

        if (importDecl.getImportE() != null) {
            builder.append(importDecl.getImportE().accept(this));
        } else {
            builder.append(importDecl.getInclude().accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitImport(Import importE) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Import").append("\n");
        indentLevel++;

        builder.append(indent()).append(importE.getImportID().lexeme).append("\n");
        builder.append(indent()).append(importE.getFromID().lexeme).append("\n");
        if (importE.getLine() != null) {
            builder.append(importE.getLine().accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitInclude(Include include) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Include").append("\n");
        indentLevel++;

        builder.append(indent()).append(include.getIncludeID().lexeme).append("\n");

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitInterfaceDecl(InterfaceDecl interfaceDecl) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("InterfaceDecl").append("\n");
        indentLevel++;

        builder.append(indent()).append(interfaceDecl.getInterfaceID().lexeme).append("\n");
        if (interfaceDecl.getHasExtender()) {
            builder.append(indent()).append("Extender").append("\n");
        }
        builder.append(interfaceDecl.getBlock().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitTypeDecl(TypeDecl typeDecl) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("TypeDecl").append("\n");
        indentLevel++;

        builder.append(indent()).append(typeDecl.getTypeID().lexeme).append("\n");
        if (typeDecl.getSecondID() != null) {
            builder.append(indent()).append(typeDecl.getSecondID().lexeme).append("\n");
        }
        if (typeDecl.getBlock() != null) {
            builder.append(typeDecl.getBlock().accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitServiceDecl(ServiceDecl serviceDecl) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("ServiceDecl").append("\n");
        indentLevel++;

        builder.append(indent()).append(serviceDecl.getServiceID().lexeme).append("\n");
        if (serviceDecl.getParams() != null) {
            for (JolieToken token :serviceDecl.getParams()) {
                builder.append(indent()).append(token.lexeme);
            }
            builder.append(indent()).append("\n");
        }
        for (Node service : serviceDecl.getServices()) {
            builder.append(service.accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitService(Service service) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Service").append("\n");
        indentLevel++;

        if (service.getNode() != null) {
            builder.append(service.getNode().accept(this));
        } else {
            builder.append(service.getLine().accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitExecution(Execution execution) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Execution").append("\n");
        indentLevel++;

        builder.append(indent()).append(execution.getExecutionID().lexeme).append("\n");
        if (execution.getUsesColon() != null) {
            builder.append(indent()).append("Uses colon").append("\n");
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitEmbed(Embed embed) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Embed").append("\n");
        indentLevel++;

        builder.append(indent()).append(embed.getEmbedID().lexeme).append("\n");
        builder.append(indent()).append(embed.getAsID().lexeme).append("\n");

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitEmbedded(Embedded embedded) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Embedded").append("\n");
        indentLevel++;

        builder.append(embedded.getBlock().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitInputPort(InputPort inputPort) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("InputPort").append("\n");
        indentLevel++;

        builder.append(indent()).append(inputPort.getInputPortID().lexeme).append("\n");
        for (Node param : inputPort.getPortParameters()) {
            builder.append(param.accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitOutputPort(OutputPort outputPort) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("OutputPort").append("\n");
        indentLevel++;

        builder.append(indent()).append(outputPort.getOutputPortID().lexeme).append("\n");
        for (Node param : outputPort.getPortParameters()) {
            builder.append(param.accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPortLocation(PortLocation portLocation) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("PortLocation").append("\n");
        indentLevel++;

        builder.append(portLocation.getLine().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPortProtocol(PortProtocol portProtocol) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("PortProtocol").append("\n");
        indentLevel++;

        builder.append(portProtocol.getLine().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPortInterfaces(PortInterfaces portInterfaces) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("PortInterfaces").append("\n");
        indentLevel++;

        for (Line line : portInterfaces.getLines()) {
            builder.append(line.accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPortAggregates(PortAggregates portAggregates) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("PortAggregates").append("\n");
        indentLevel++;

        for (Line line : portAggregates.getLines()) {
            builder.append(line.accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPortRedirects(PortRedirects portRedirects) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("PortRedirects").append("\n");
        indentLevel++;

        for (Line line : portRedirects.getLines()) {
            builder.append(line.accept(this));
        }

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitInit(Init init) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Init").append("\n");
        indentLevel++;

        builder.append(init.getBlock().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitMain(Main main) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Main").append("\n");
        indentLevel++;

        builder.append(main.getBlock().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitCourier(Courier courier) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Courier").append("\n");
        indentLevel++;

        builder.append(indent()).append(courier.getCourierInterfaceID().lexeme).append("\n");
        builder.append(courier.getBlock().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitDefineProcedure(DefineProcedure defineProcedure) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("DefineProcedure").append("\n");
        indentLevel++;

        builder.append(indent()).append(defineProcedure.getDefineID().lexeme).append("\n");
        builder.append(defineProcedure.getBlock().accept(this));

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitBlock(Block block) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Block").append("\n");
        indentLevel++;

        for (JolieToken token : block.getContents()) {
            builder.append(indent()).append(token.lexeme);
        }
        builder.append(indent()).append("\n");

        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitLine(Line line) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("Line").append("\n");
        indentLevel++;

        for (JolieToken token : line.getContents()) {
            builder.append(indent()).append(token.lexeme);
        }
        builder.append(indent()).append("\n");

        indentLevel--;
        return builder.toString();
    }
}

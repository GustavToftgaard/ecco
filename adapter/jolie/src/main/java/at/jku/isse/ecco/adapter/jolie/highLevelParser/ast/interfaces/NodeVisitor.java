package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.typeDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports.*;

public interface NodeVisitor<T> {
    T visitImportDecl(ImportDecl importDecl);
    T visitImport(Import importE);
    T visitInclude(Include include);
    T visitInterfaceDecl(InterfaceDecl interfaceDecl);
    T visitTypeDecl(TypeDecl typeDecl);
    T visitServiceDecl(ServiceDecl serviceDecl);
    T visitService(Service service);
    T visitExecution(Execution execution);
    T visitEmbed(Embed embed);
    T visitEmbedded(Embedded embedded);
    T visitInputPort(InputPort inputPort);
    T visitOutputPort(OutputPort outputPort);
    T visitPortLocation(PortLocation portLocation);
    T visitPortProtocol(PortProtocol portProtocol);
    T visitPortInterfaces(PortInterfaces portInterfaces);
    T visitPortAggregates(PortAggregates portAggregates);
    T visitPortRedirects(PortRedirects portRedirects);
    T visitInit(Init init);
    T visitMain(Main main);
    T visitCourier(Courier courier);
    T visitDefineProcedure(DefineProcedure defineProcedure);
    T visitBlock(Block block);
    T visitLine(Line line);
    T visitEndOfFile(EndOfFile endOfFile);
}

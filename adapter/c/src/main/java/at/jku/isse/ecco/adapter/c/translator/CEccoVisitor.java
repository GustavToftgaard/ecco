package at.jku.isse.ecco.adapter.c.translator;

import at.jku.isse.ecco.adapter.c.parser.generated.CBaseVisitor;
import at.jku.isse.ecco.adapter.c.parser.generated.CParser;
import at.jku.isse.ecco.dao.EntityFactory;
import at.jku.isse.ecco.tree.Node;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;


public class CEccoVisitor extends CBaseVisitor<Node.Op> {

    private Node.Op pluginNode;
    private String[] codeLines;
    private EntityFactory entityFactory;
    private Collection<CParser.FunctionDefinitionContext> functionDefinitionContexts;
    private Path path;

    public CEccoVisitor(Node.Op pluginNode,
                        String[] codeLines,
                        EntityFactory entityFactory,
                        Path path){
        this.pluginNode = pluginNode;
        this.codeLines = codeLines;
        this.entityFactory = entityFactory;
        this.functionDefinitionContexts = new LinkedList<>();
        this.path = path;
    }

    public Node.Op translate(ParseTree tree){
        return tree.accept(this);
    }

    @Override
    public Node.Op visitTranslationUnit(CParser.TranslationUnitContext ctx){
        for(ParseTree parseTree : ctx.externalDeclaration()){
            parseTree.accept(this);
        }

        CEccoTranslator translator = new CEccoTranslator(
                this.codeLines, this.entityFactory, this.path);
        this.collectFunctions(translator);
        translator.addChildrenToPluginNode(this.pluginNode);
        return this.pluginNode;
    }

    private void collectFunctions(CEccoTranslator programStructure){
        for(CParser.FunctionDefinitionContext ctx : this.functionDefinitionContexts){
            if (this.checkFunction(ctx)) {
                String functionSignature = this.getFunctionSignature(ctx);
                programStructure.addFunctionStructure(ctx.start.getLine(), ctx.stop.getLine(), functionSignature);
            }
        }
    }

    private boolean checkFunction(CParser.FunctionDefinitionContext ctx){
        return ctx.declarationSpecifiers() != null && ctx.declarator() != null;
    }

    private String getFunctionSignature(CParser.FunctionDefinitionContext ctx){
        return ctx.declarationSpecifiers().getText() + ctx.declarator().getText();
    }

    @Override
    public Node.Op visitExternalDeclaration(CParser.ExternalDeclarationContext ctx){
        ParseTree parseTree = ctx.functionDefinition();
        if (parseTree != null){ ctx.functionDefinition().accept(this); }
        return null;
    }

    @Override
    public Node.Op visitFunctionDefinition(CParser.FunctionDefinitionContext ctx){
        this.functionDefinitionContexts.add(ctx);
        return null;
    }
}

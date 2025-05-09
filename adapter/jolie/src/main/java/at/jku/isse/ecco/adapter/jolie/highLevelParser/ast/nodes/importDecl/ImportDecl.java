package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;

public class ImportDecl implements Node {
    private String postLexeme = "";
    private final Import importE;
    private final Include include;

    public ImportDecl(Import importE, Include include) {
        this.importE = importE;
        this.include = include;
    }

    public Import getImportE() {
        return importE;
    }

    public Include getInclude() {
        return include;
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
        return visitor.visitImportDecl(this);
    }
}
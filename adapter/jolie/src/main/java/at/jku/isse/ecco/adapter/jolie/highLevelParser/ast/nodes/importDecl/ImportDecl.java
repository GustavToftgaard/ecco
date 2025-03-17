package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;

public class ImportDecl implements Node {
    private final Import importE;
    private final Include include;

    public ImportDecl(Import importE) {
        this.importE = importE;
        this.include = null;
    }

    public ImportDecl(Include include) {
        this.include = include;
        this.importE = null;
    }

    public Import getImportE() {
        return importE;
    }

    public Include getinclude() {
        return include;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitImportDecl(this);
    }
}
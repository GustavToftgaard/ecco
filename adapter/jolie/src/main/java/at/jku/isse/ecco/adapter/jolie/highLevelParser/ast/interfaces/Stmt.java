package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

public interface Stmt {
    public <T> T accept(StmtVisitor<T> visitor);
}

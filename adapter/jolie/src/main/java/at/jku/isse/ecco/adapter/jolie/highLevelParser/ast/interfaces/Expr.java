package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

public interface Expr {
    public <T> T accept(ExprVisitor<T> visitor);
}

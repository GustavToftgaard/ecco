package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;

public class GroupingExpr implements Expr {
    private final Expr expr;

    public GroupingExpr(Expr expr) {
        this.expr = expr;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitGrouping(this);
    }

    public Expr getExpr() {
        return this.expr;
    }
}

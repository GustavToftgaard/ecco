package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class LogicalExpr implements Expr {
    private final Expr left;
    private final Token operator;
    private final Expr right;

    public LogicalExpr(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitLogical(this);
    }

    public Expr getLeft() {
        return this.left;
    }

    public Token getOperator() {
        return this.operator;
    }

    public Expr getRight() {
        return this.right;
    }
}

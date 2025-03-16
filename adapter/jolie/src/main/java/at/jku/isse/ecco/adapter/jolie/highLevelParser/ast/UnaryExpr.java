package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class UnaryExpr implements Expr {
    private final Token operator;
    private final Expr expression;

    public UnaryExpr(Token operator, Expr expression) {
        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitUnary(this);
    }

    public Token getOperator() {
        return this.operator;
    }

    public Expr getExpression() {
        return this.expression;
    }

}

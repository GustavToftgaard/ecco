package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class AssignExpr implements Expr {
    private final Token id;
    private final Expr expr;

    public AssignExpr(Token id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitAssign(this);
    }

    public Token getId() {
        return this.id;
    }

    public Expr getExpr() {
        return this.expr;
    }
}

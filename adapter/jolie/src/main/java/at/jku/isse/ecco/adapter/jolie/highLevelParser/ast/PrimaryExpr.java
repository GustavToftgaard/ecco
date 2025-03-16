package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class PrimaryExpr implements Expr {
    private final Token value;

    public PrimaryExpr(Token value) {
        this.value = value;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitPrimary(this);
    }

    public Token getValue() {
        return this.value;
    }
}

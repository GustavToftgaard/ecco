package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class VariableExpr implements Expr {
    private final Token id;

    public VariableExpr(Token id) {
        this.id = id;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitVariable(this);
    }

    public Token getId() {
        return id;
    }

}

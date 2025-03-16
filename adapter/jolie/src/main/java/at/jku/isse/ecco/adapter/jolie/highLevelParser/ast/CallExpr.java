package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import java.util.List;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class CallExpr implements Expr {
    private Token callId;
    private List<Expr> args;

    public CallExpr(Token callId, List<Expr> args) {
        this.callId = callId;
        this.args = args;
    }

    public Token getCallId() {
        return this.callId;
    }

    public List<Expr> getArgs() {
        return this.args;
    }

    @Override
    public <T> T accept(ExprVisitor<T> visitor) {
        return visitor.visitCall(this);
    }

}

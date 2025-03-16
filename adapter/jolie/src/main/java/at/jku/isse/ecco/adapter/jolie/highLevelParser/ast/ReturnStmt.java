package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class ReturnStmt implements Stmt {
    private Token token;
    private Expr expr = null;

    public ReturnStmt(Token token) {
        this.token = token;
    }

    public ReturnStmt(Token token, Expr expr) {
        this.token = token;
        this.expr = expr;
    }

    public Token getToken() {
        return token;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitReturn(this);
    }

}

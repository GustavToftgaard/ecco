package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class WhileStmt implements Stmt {
    private final Token whileToken;
    private final Expr conditionExpr;
    private final Stmt executionStmt;

    public WhileStmt(Token whileToken, Expr conditionExpr, Stmt executionStmt) {
        this.whileToken = whileToken;
        this.conditionExpr = conditionExpr;
        this.executionStmt = executionStmt;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitWhile(this);
    }

    public Expr conditionExpr() {
        return this.conditionExpr;
    }

    public Stmt getExecutionStmt() {
        return this.executionStmt;
    }

    public Token getWhileToken() {
        return whileToken;
    }
}

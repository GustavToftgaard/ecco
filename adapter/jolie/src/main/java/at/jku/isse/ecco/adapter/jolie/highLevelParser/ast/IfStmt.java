package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class IfStmt implements Stmt {
    private final Token ifToken;
    private final Expr cond;
    private final Stmt thenBranch;
    private final Stmt elseBranch;

    public IfStmt(Token ifToken, Expr cond, Stmt thenBranch, Stmt elseBranch) {
        this.ifToken = ifToken;
        this.cond = cond;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitIf(this);
    }

    public Expr getCond() {
        return this.cond;
    }

    public Stmt getThenBranch() {
        return this.thenBranch;
    }

    public Stmt getElseBranch() {
        return this.elseBranch;
    }

    public Token getIfToken() {
        return ifToken;
    }
}

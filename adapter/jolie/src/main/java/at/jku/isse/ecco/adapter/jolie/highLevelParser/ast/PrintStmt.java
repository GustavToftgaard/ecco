package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;

public class PrintStmt implements Stmt {
    private final Expr expr;

    public PrintStmt(Expr expr) {
        this.expr = expr;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitPrint(this);
    }

    public Expr getExpr() {
        return expr;
    }
}

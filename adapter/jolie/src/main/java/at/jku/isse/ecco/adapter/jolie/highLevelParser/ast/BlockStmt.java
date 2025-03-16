package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import java.util.List;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class BlockStmt implements Stmt {
    private final List<Stmt> statements;
    private final Token start;
    private final Token end;

    public BlockStmt(List<Stmt> statements, Token leftBrace, Token rightBrace) {
        this.statements = statements;
        start = leftBrace;
        end = rightBrace;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitBlock(this);
    }

    public List<Stmt> getStatements() {
        return this.statements;
    }

    public Token getStart() {
        return start;
    }

    public Token getEnd() {
        return end;
    }

}

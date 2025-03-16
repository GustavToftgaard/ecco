package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class VarDecl implements Stmt {
    private final Token id;
    private final Type type;
    private final Expr expr;

    public VarDecl(Token id, Type type, Expr expr) {
        this.id = id;
        this.type = type;
        this.expr = expr;
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitVarDecl(this);
    }

    public Token getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public Expr getExpr() {
        return this.expr;
    }

}

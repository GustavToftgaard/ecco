package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast;

import java.util.List;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class FuncDecl implements Stmt {
    private final Token id;
    private final Function function;


    public FuncDecl(Token id, List<Token> paramsToken, List<Type> paramsType, Type type, BlockStmt blockStmt) {
        assert paramsToken.size() == paramsType.size();

        this.id = id;
        this.function = new Function(paramsToken, paramsType, type, blockStmt);
    }

    @Override
    public <T> T accept(StmtVisitor<T> visitor) {
        return visitor.visitFuncDecl(this);
    }

    public Token getId() {
        return id;
    }

    public Function getFunction() {
        return function;
    }

    public static class Function {
        private final List<Token> paramsToken;
        private final List<Type> paramsType;
        private final Type returnType;
        private final BlockStmt blockStmt;

        public Function(List<Token> paramsToken, List<Type> paramsType, Type returnType, BlockStmt blockStmt) {
            this.paramsToken = paramsToken;
            this.paramsType = paramsType;
            this.returnType = returnType;
            this.blockStmt = blockStmt;
        }

        public List<Token> getParamsToken() {
            return paramsToken;
        }

        public List<Type> getParamsType() {
            return paramsType;
        }


        public Type getReturnType() {
            return returnType;
        }


        public BlockStmt getBlockStmt() {
            return blockStmt;
        }
    }

}

package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.AssignExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.BinaryExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.BlockStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.CallExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.ExprStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.FuncDecl;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.GroupingExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.IfStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.LogicalExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.PrimaryExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.PrintStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.ReturnStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.UnaryExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.VarDecl;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.VariableExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.WhileStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Expr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.ExprVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.Stmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.interfaces.StmtVisitor;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.scan.Token;

public class ASTPrinter implements StmtVisitor<String>, ExprVisitor<String> {

    private int indentLevel = 0;

    private String indent() {
        return "  ".repeat(indentLevel);
    }

    public String print(Stmt node) {
        return node.accept(this);
    }

    @Override
    public String visitBlock(BlockStmt blockStmt) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("BlockStmt").append("\n");
        indentLevel++;
        for (var stmt : blockStmt.getStatements()) {
            builder.append(stmt.accept(this));
        }
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitIf(IfStmt ifStmt) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("IfStmt").append("\n");
        indentLevel++;
        builder.append(ifStmt.getCond().accept(this));
        builder.append(ifStmt.getThenBranch().accept(this));
        if (ifStmt.getElseBranch() != null) {
            builder.append(ifStmt.getElseBranch().accept(this));
        }
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPrint(PrintStmt printStmt) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("PrintStmt").append("\n");
        indentLevel++;
        builder.append(printStmt.getExpr().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitVarDecl(VarDecl varDecl) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("VarDecl").append("\n");
        indentLevel++;
        builder.append(indent()).append(varDecl.getId().lexeme).append("\n");
        builder.append(indent()).append(varDecl.getType()).append("\n");
        if (varDecl.getExpr() != null) {
            builder.append(varDecl.getExpr().accept(this));
        }
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitWhile(WhileStmt whileStmt) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("WhileStmt").append("\n");
        indentLevel++;
        builder.append(whileStmt.conditionExpr().accept(this));
        builder.append(whileStmt.getExecutionStmt().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitAssign(AssignExpr assignExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("AssignExpr").append("\n");
        indentLevel++;
        builder.append(indent()).append(assignExpr.getId().lexeme).append("\n");
        builder.append(assignExpr.getExpr().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitBinary(BinaryExpr binaryExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("BinaryExpr").append("\n");
        indentLevel++;
        builder.append(binaryExpr.getLeft().accept(this));
        builder.append(indent()).append(binaryExpr.getOperator().lexeme).append("\n");
        builder.append(binaryExpr.getRight().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitGrouping(GroupingExpr groupingExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("GroupingExpr").append("\n");
        indentLevel++;
        builder.append(groupingExpr.getExpr().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitLogical(LogicalExpr logicalExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("LogicalExpr").append("\n");
        indentLevel++;
        builder.append(logicalExpr.getLeft().accept(this));
        builder.append(indent()).append(logicalExpr.getOperator().lexeme).append("\n");
        builder.append(logicalExpr.getRight().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitUnary(UnaryExpr unaryExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("UnaryExpr").append("\n");
        indentLevel++;
        builder.append(indent()).append(unaryExpr.getOperator().lexeme).append("\n");
        builder.append(unaryExpr.getExpression().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitVariable(VariableExpr variableExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("VariableExpr").append("\n");
        indentLevel++;
        builder.append(indent()).append(variableExpr.getId().lexeme).append("\n");
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitExprStmt(ExprStmt exprStmt) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("ExprStmt").append("\n");
        indentLevel++;
        builder.append(exprStmt.getExpr().accept(this));
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitPrimary(PrimaryExpr primaryExpr) {
        Token value = primaryExpr.getValue();
        return switch (value.type) {
            case NUMBER -> indent() + value.literal.toString() + "\n";
            case STRING -> indent() + '\"' + value.literal.toString() + '\"' + "\n";
            default -> indent() + value.lexeme + "\n";
        };
    }

    @Override
    public String visitCall(CallExpr callExpr) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("CallExpr").append("\n");
        indentLevel++;
        VariableExpr variableExpr = new VariableExpr(callExpr.getCallId());
        builder.append(indent()).append("ID = ").append(variableExpr.accept(this)).append("\n");
        indentLevel++;
        for (Expr arg : callExpr.getArgs()) {
            builder.append(indent()).append(arg.accept(this)).append("\n");
        }
        indentLevel--;
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitFuncDecl(FuncDecl funcDecl) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("FuncDecl").append("\n");
        indentLevel++;
        builder.append(indent()).append(funcDecl.getId().lexeme).append("\n");
        var func = funcDecl.getFunction();
        builder.append(indent()).append("Params").append("\n");
        indentLevel++;
        for (int i = 0; i < func.getParamsToken().size(); i++) {
            builder.append(indent()).append("Param").append("\n");
            indentLevel++;
            VariableExpr variableExpr = new VariableExpr(func.getParamsToken().get(i));
            builder.append(indent()).append(variableExpr.accept(this)).append("\n");
            builder.append(indent()).append(func.getParamsType().get(i)).append("\n");
            indentLevel--;
        }
        builder.append(indent()).append("ReturnType").append("\n");
        indentLevel++;
        builder.append(indent()).append(func.getReturnType()).append("\n");
        indentLevel--;
        builder.append(indent()).append(func.getBlockStmt().accept(this)).append("\n");
        indentLevel--;
        return builder.toString();
    }

    @Override
    public String visitReturn(ReturnStmt returnStmt) {
        StringBuilder builder = new StringBuilder();
        builder.append(indent()).append("ReturnStmt").append("\n");
        indentLevel++;
        builder.append(indent()).append(returnStmt.getExpr().accept(this)).append("\n");
        indentLevel--;
        return builder.toString();
    }

}
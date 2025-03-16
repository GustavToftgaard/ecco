package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.AssignExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.BinaryExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.CallExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.GroupingExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.LogicalExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.PrimaryExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.UnaryExpr;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.VariableExpr;


public interface ExprVisitor<T> {

    T visitAssign(AssignExpr assignExpr);

    T visitBinary(BinaryExpr binaryExpr);

    T visitGrouping(GroupingExpr groupingExpr);

    T visitLogical(LogicalExpr logicalExpr);

    T visitUnary(UnaryExpr unaryExpr);

    T visitVariable(VariableExpr variableExpr);

    T visitPrimary(PrimaryExpr primaryExpr);

    T visitCall(CallExpr callExpr);

}
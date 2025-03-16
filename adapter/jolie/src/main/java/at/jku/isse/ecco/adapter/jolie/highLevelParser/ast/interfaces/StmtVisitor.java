package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.BlockStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.ExprStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.FuncDecl;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.IfStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.PrintStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.ReturnStmt;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.VarDecl;
import dk.sdu.imada.teaching.compiler.fs24.verbosepl.ast.WhileStmt;

public interface StmtVisitor<T> {
  T visitBlock(BlockStmt blockStmt);

  T visitIf(IfStmt ifStmt);

  T visitPrint(PrintStmt printStmt);

  T visitVarDecl(VarDecl varDecl);

  T visitWhile(WhileStmt whileStmt);

  T visitExprStmt(ExprStmt exprStmt);

  T visitFuncDecl(FuncDecl funcDecl);

  T visitReturn(ReturnStmt returnStmt);

}

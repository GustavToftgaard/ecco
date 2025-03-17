package at.jku.isse.ecco.adapter.jolie.highLevelParser.parser;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
//import at.jku.isse.ecco.adapter.jolie.highLevelParser.VerbosePL;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.AssignExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.BinaryExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.BlockStmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.CallExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.ExprStmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.FuncDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.GroupingExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.IfStmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.LogicalExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.PrimaryExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.PrintStmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.ReturnStmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.Type;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.UnaryExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.VarDecl;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.VariableExpr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.WhileStmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.interfaces.Expr;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.astTemp.interfaces.Stmt;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ParserTemp {
    private List<JolieToken> tokens = new LinkedList<>();
    private int current = 0;
    private List<Stmt> ast = new LinkedList<>();

    public List<Stmt> getAst() {
        return ast;
    }

    public ParserTemp(List<JolieToken> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        program();
        return ast;
    }

    // peogram
    private void program() {
        while (!isAtEnd() && peek().type != EOF) {
            try {
                ast.add(decl());
            } catch (ParseError e) {
                synchronize();
            }
        }
    }

    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case VAR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                case LEFT_BRACE:
                    return;
                default:
            }

            advance();
        }
    }

    // decl
    private Stmt decl() {
        Stmt res = null;
        JolieToken token = peek();
        switch (token.type) {
            case VAR:
                res = vardecl();
                break;

            case FUNC:
                res = funcDecl();
                break;

            default:
                res = statement();
                break;
        }
        return res;
    }

    // varDecl
    private VarDecl vardecl() {
        consume(VAR, "Expected 'var' before variable declaration.");
        var id = consume(IDENTIFIER, "Expected variable identifier.");
        consume(TYPE_DEF, "Expected 'of_type' after variable identifier.");
        Type type = type();

        Expr expr = null;
        if (match(ASSIGN)) {
            expr = expr();
        }
        consume(SEMICOLON, "Expected ';' after variable declaration.");

        return new VarDecl(id, type, expr);
    }

    // type
    private Type type() {
        Type res = null;
        JolieToken token = advance();
        switch (token.type) {
            case NUMBER_TYPE:
                res = Type.Number;
                break;

            case STRING_TYPE:
                res = Type.String;
                break;

            case BOOL_TYPE:
                res = Type.Bool;
                break;

            default:
                throw error(token, "Expected a type found: " + token.lexeme);
        }
        return res;
    }

    // statment
    private Stmt statement() {
        Stmt res = null;
        JolieToken token = peek();
        switch (token.type) {
            case IF:
                res = ifStmt();
                break;

            case WHILE:
                res = whileStmt();
                break;

            case PRINT:
                res = printStmt();
                break;

            case RETURN:
                res = returnStmt();
                break;

            case LEFT_BRACE:
                res = block();
                break;

            default:
                res = exprStmt();
                break;
        }
        return res;
    }

    // exprStmt
    private ExprStmt exprStmt() {
        Expr expr = expr();
        consume(SEMICOLON, "Expected ';' after expression.");
        return new ExprStmt(expr);
    }

    // ifStmt
    private IfStmt ifStmt() {
        JolieToken ifToken = consume(IF, "Expected 'if' statement.");
        consume(LEFT_PAREN, "Expected '(' after 'if'.");
        Expr cond = expr();
        consume(RIGHT_PAREN, "Expected ')' after expression.");
        Stmt thenBranch = statement();
        Stmt elseBranch = null;
        if (match(ELSE)) {
            elseBranch = statement();
        }
        return new IfStmt(ifToken, cond, thenBranch, elseBranch);
    }

    // printStmt
    private PrintStmt printStmt() {
        consume(PRINT, "Expected 'print' statement.");
        Expr res = expr();
        consume(SEMICOLON, "Expected ';' after expression.");
        return new PrintStmt(res);
    }

    // whileStmt
    private WhileStmt whileStmt() {
        JolieToken whileToken = consume(WHILE, "Expected 'while' statement.");
        consume(LEFT_PAREN, "Expected '(' after 'while'.");
        Expr expr = expr();
        consume(RIGHT_PAREN, "Expected ')' after expression.");
        Stmt stmt = statement();
        return new WhileStmt(whileToken, expr, stmt);
    }

    // block
    private BlockStmt block() {
        JolieToken leftBrace = consume(LEFT_BRACE, "Expected '{' before block.");
        List<Stmt> list = new LinkedList<>();
        while (!match(RIGHT_BRACE)) {
            list.add(decl());
            if (peek().type == EOF) {
                consume(RIGHT_BRACE, "Expected '}' at end of block.");
                break;
            }
        }
        return new BlockStmt(list, leftBrace, previous());
    }

    // expr
    private Expr expr() {
        return assignment();
    }

    // assignment
    private Expr assignment() {
        Expr expr = logicOr();
        JolieToken id = previous();
        if (match(ASSIGN)) {
            if (!(expr instanceof VariableExpr)) {
                throw error(previous(), "Invalid assignment target.");
            }
            Expr value = assignment();
            return new AssignExpr(id, value);
        }
        return expr;
    }

    // logicalOr
    private Expr logicOr() {
        Expr expr = logicAnd();
        while (match(OR)) {
            JolieToken op = previous();
            Expr right = logicAnd();
            expr = new LogicalExpr(expr, op, right);
        }
        return expr;
    }

    // logicalAnd
    private Expr logicAnd() {
        Expr expr = equality();
        while (match(AND)) {
            JolieToken op = previous();
            Expr right = equality();
            expr = new LogicalExpr(expr, op, right);
        }
        return expr;
    }

    // equality
    private Expr equality() {
        Expr expr = compr();
        while (match(EQUALS) || match(NOT_EQUALS)) {
            JolieToken op = previous();
            Expr right = compr();
            expr = new BinaryExpr(expr, op, right);
        }
        return expr;
    }

    // compr
    private Expr compr() {
        Expr expr = term();
        while (match(GREATER) || match(GREATER_OR_EQUAL) || match(LESS) || match(LESS_OR_EQUAL)) {
            JolieToken op = previous();
            Expr right = term();
            expr = new BinaryExpr(expr, op, right);
        }
        return expr;
    }

    // term
    private Expr term() {
        Expr expr = factor();
        while (match(PLUS) || match(MINUS)) {
            JolieToken op = previous();
            Expr right = factor();
            expr = new BinaryExpr(expr, op, right);
        }
        return expr;
    }

    // factor
    private Expr factor() {
        Expr expr = unary();
        while (match(MULTIPLY) || match(DIV)) {
            JolieToken op = previous();
            Expr right = unary();
            expr = new BinaryExpr(expr, op, right);
        }
        return expr;
    }

    // unary
    private Expr unary() {
        if (match(MINUS) || match(NOT)) {
            JolieToken op = previous();
            Expr right = unary();
            return new UnaryExpr(op, right);
        }
        return call();
    }

    // primary
    private Expr primary() {
        if (match(NUMBER) || match(STRING) || match(TRUE) || match(FALSE)) {
            return new PrimaryExpr(previous());
        }
        if (match(IDENTIFIER)) {
            return new VariableExpr(previous());
        }
        if (match(LEFT_PAREN)) {
            GroupingExpr expr = new GroupingExpr(expr());
            consume(RIGHT_PAREN, "Expected ')' after expression.");
            return expr;
        }
        throw error(previous(), "Expected expression.");
    }

    // funcDecl
    private Stmt funcDecl() {
        consume(FUNC, "Expected 'func' before function declaration.");
        return function();
    }

    // function + params
    private Stmt function() {
        JolieToken id = advance();
        if (id.type != IDENTIFIER) {
            error(id, "Expected an identifier.");
        }
        List<JolieToken> paramsId = new ArrayList<>();
        List<Type> paramsType = new ArrayList<>();
        Type type = Type.Nothing;

        consume(LEFT_PAREN, "Expected '(' after function identifier.");

        if (RIGHT_PAREN != peek().type) {
            do {
                paramsId.add(advance());
                consume(TYPE_DEF, "Expected 'of_type' after parameter identifier.");
                paramsType.add(type());
            } while (match(COMMA));
        }

        consume(RIGHT_PAREN, "Expected ')' after parameters.");

        if (match(TYPE_DEF)) {
            type = type();
        }

        BlockStmt blockStmt = block();

        return new FuncDecl(id, paramsId, paramsType, type, blockStmt);
    }

    // call
    private Expr call() {
        Expr expr = primary();

        if (peek().type == LEFT_PAREN) {
            if (!(expr instanceof VariableExpr)) {
                error(peek(), "Can only call functions.");
            }
            List<Expr> args = new ArrayList<>();
            consume(LEFT_PAREN, "Expected '(' after function call.");
            if (!match(RIGHT_PAREN)) {
                args.addAll(args());
                consume(RIGHT_PAREN, "Expected ')' after arguments.");
            }
            if (peek().type == LEFT_PAREN) {
                consume(null, "Not supporting function chaining ATM.");
            }
            expr = new CallExpr(((VariableExpr) expr).getId(), args);
        }

        return expr;
    }

    // args
    private List<Expr> args() {
        List<Expr> args = new ArrayList<>();
        args.add(expr());

        while (match(COMMA)) {
            args.add(expr());
        }

        return args;
    }

    // returnStmt
    private Stmt returnStmt() {
        JolieToken token = consume(RETURN, "Expected 'return' statement.");

        if (peek().type != SEMICOLON) {
            Stmt expr = new ReturnStmt(token, expr());
            consume(SEMICOLON, "Expected ';' after return statement.");
            return expr;
        } else {
            consume(SEMICOLON, "Expected ';' after return statement.");
            return new ReturnStmt(token);
        }
    }

    //---------------------------------------------------------------

    private JolieToken consume(JolieTokenType type, String message) {
        if (match(type)) return previous();

        throw error(previous(), message);
    }

    private JolieToken advance() {
        return this.tokens.get(this.current++);
    }

    private JolieToken previous() {
        JolieToken res = null;
        if (this.current > 0)
            res = this.tokens.get(this.current - 1);
        return res;
    }

    private boolean match(JolieTokenType type) {
        if (isAtEnd())
            return false;
        if (this.tokens.get(this.current).type == type) {
            advance();
            return true;
        }
        return false;
    }

    private JolieToken peek() {
        if (isAtEnd())
            return null;
        return this.tokens.get(this.current);
    }


    private boolean isAtEnd() {
        return this.current >= this.tokens.size();
    }

    private ParseError error(JolieToken token, String message) {
        VerbosePL.error(token, message, "PARSE_ERROR");
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
    }

}

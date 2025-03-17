package at.jku.isse.ecco.adapter.jolie.highLevelParser.parser;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.typeDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.serviceDecl.ports.*;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

public class Parser {
    private List<JolieToken> tokens = new LinkedList<>();
    private int current = 0;
    private List<Node ast = new LinkedList<>();

    public List<Node> getAst() {
        return ast;
    }

    public Parser(List<JolieToken> tokens) {
        this.tokens = tokens;
    }

    public List<Node> parse() {
        program();
        return ast;
    }

    // program
    private void program() {
        while (!isAtEnd() && peek().type != EOF) {
            try {
                ast.add(decl());
            } catch (ParseError e) {
                synchronize();
            }
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
//        VerbosePL.error(token, message, "PARSE_ERROR"); TODO: error handling
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
    }

}

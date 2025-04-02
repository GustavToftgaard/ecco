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
import jolie.Jolie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

public class Parser {
    private List<JolieToken> tokens = new LinkedList<>();
    private int current = 0;
    private List<Node> ast = new LinkedList<>();

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
        while (!isAtEnd() && peek().getType() != EOF) {
            try {
                ast.add(decl());
            } catch (ParseError e) {
                // synchronize();
                System.out.println(e.getMessage()); // temp
            }
        }
    }

    // TODO: implement if needed (might read line instead of errors)
//    private void synchronize() {
//        advance();
//
//        while (!isAtEnd()) {
//            if (previous().type == SEMICOLON) return;
//
//            switch (peek().type) {
//                case VAR:
//                case IF:
//                case WHILE:
//                case PRINT:
//                case RETURN:
//                case LEFT_BRACE:
//                    return;
//                default:
//            }
//
//            advance();
//        }
//    }

    //---------------------------------------------------------------

    // decl
    private Node decl() {
        Node res = null;
        JolieToken token = peek();
        switch (token.getType()) {
            case FROM:
            case INCLUDE:
//                System.out.println("import");
                res = importDecl();
                break;

            case INTERFACE:
//                System.out.println("interface");
                res = interfaceDecl();
                break;

            case TYPE:
//                System.out.println("type");
                res = typeDecl();
                break;

            case SERVICE:
//                System.out.println("service");
                res = serviceDecl();
                break;

            // TODO: Missing case for going around serviceDecl to service

            default:
//                System.out.println("line");
                res = readLine(token.getLine());
                break;
        }
        return res;
    }

    // ----

    private ImportDecl importDecl() {
        Node res = null;
        if (peek().getType() == FROM) {
            consume(FROM, "Expected FROM token in import decl");
            JolieToken fromID = consumeAndReturn(ID, "Expected ID after FROM in import decl");
            consume(IMPORT, "Expected IMPORT after ID in import decl");
            JolieToken importID = consumeAndReturn(ID, "Expected ID after IMPORT in import decl");

            // check of multiple 'as' statements
            if (previous().getLine() == peek().getLine()) {
                Node line = readLine(previous().getLine());
                return new ImportDecl(new Import(fromID, importID, line));
            } else {
                return new ImportDecl(new Import(fromID, importID));
            }
        } else {
            consume(INCLUDE, "Expected INCLUDE token in import decl");
            JolieToken id = consumeAndReturn(STRING, "Expected string as ID after INCLUDE in import decl");
            return new ImportDecl(new Include(id));
        }
    }

    // ----

    private InterfaceDecl interfaceDecl() {
        consume(INTERFACE, "Expected INTERFACE token");
        JolieToken hasExtender = null;
        if (peek().getType() == EXTENDER) {
            hasExtender = peek();
            consume(EXTENDER, "Expected EXTENDER token in interface decl");
        }
        JolieToken interfaceID = consumeAndReturn(ID, "Expected ID token after interface decl");
        Block block = block();

        if (hasExtender != null) {
            return new InterfaceDecl(interfaceID, hasExtender, block);
        } else {
            return new InterfaceDecl(interfaceID, block);
        }

        // can be expanded further with OW and RR
    }

    // ----

    private TypeDecl typeDecl() {
        consume(TYPE, "Expected TYPE token");
        JolieToken typeId = consumeAndReturn(ID, "Expected ID token after TYPE in type decl");
        // consume(COLON, "Expected COLON token after ID in type decl"); // TODO
        if (peek().getType() == ID) {
            JolieToken typeTypeId = consumeAndReturn(ID, "Expected ID token after COLON in type decl");
            return new TypeDecl(typeId, typeTypeId);
        } else {
            Block block = block();
            return new TypeDecl(typeId, block);
        }
    }

    // ----

    private ServiceDecl serviceDecl() {
        boolean hasParams = false;
        consume(SERVICE, "Expected SERVICE token");
        JolieToken serviceId = consumeAndReturn(ID, "Expected ID token after SERVICE in service decl");

        ArrayList<JolieToken> params = new ArrayList<>();
        if (peek().getType() == LEFT_PAREN) {
            consume(LEFT_PAREN, "Expected LEFT_PAREN token in service decl");
//            while (peek().type != RIGHT_PAREN) { // TODO: figure our params
//                JolieToken id = consume(ID, "Expected ID token in service decl");
//            }
            readLine(previous().getLine()); // temp
            consume(RIGHT_PAREN, "Expected RIGHT_BRACE token in service decl");
            hasParams = true;
        }

        consume(LEFT_BRACE, "Expected LEFT_BRACE token in service decl");
        ArrayList<Node> services = new ArrayList<>();
        while (peek().getType() != RIGHT_BRACE) {
            services.add(service());
        }
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token in service decl");

        if (hasParams) {
            return new ServiceDecl(serviceId, services, params);
        } else {
            return new ServiceDecl(serviceId, services);
        }
    }

    private Node service() {
        Node res = null;
        JolieToken token = peek();
        switch (token.getType()) {
            case EXECUTION:
//                System.out.println("execution");
                res = execution();
                break;

            case EMBED:
//                System.out.println("embed");
                res = embed();
                break;

            case EMBEDDED:
//                System.out.println("embedded");
                res = embedded();
                break;

            case INPUTPORT:
//                System.out.println("inputPort");
                res = inputPort();
                break;

            case OUTPUTPORT:
//                System.out.println("outputPort");
                res = outputPort();
                break;

            case INIT:
//                System.out.println("init");
                res = init();
                break;

            case MAIN:
//                System.out.println("main");
                res = mainE();
                break;

            case COURIER:
//                System.out.println("courier");
                res = courier();
                break;

            case DEFINE:
//                System.out.println("define");
                res = defineProcedure();
                break;

            default:
//                System.out.println("line");
                res = readLine(token.getLine());
                break;
        }
        return res;
    }

    private Execution execution() {
        consume(EXECUTION, "Expected EXECUTION token");
        if (peek().getType() == COLON) {
            JolieToken colon = peek();
            consume(COLON, "Expected COLON token after EXECUTION in execution");
            JolieToken executionId = consumeAndReturn(ID, "Expected ID token after COLON in execution");
            return new Execution(executionId, colon);
        } else {
            consume(LEFT_BRACE, "Expected LEFT_BRACE token after EXECUTION in execution");
            JolieToken executionId = consumeAndReturn(ID, "Expected ID token after LEFT_BRACE in execution");
            consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after ID in execution");
            return new Execution(executionId);
        }
    }

    private Embed embed() {
        consume(EMBED, "Expected EMBED token");
        JolieToken embedId = consumeAndReturn(ID, "Expected ID token after EMBED in embed");
        consume(AS, "Expected AS token after in embed");
        JolieToken asId = consumeAndReturn(ID, "Expected ID token after AS in embed");
        return new Embed(embedId, asId);
    }

    private Embedded embedded() {
        consume(EMBEDDED, "Expected EMBEDDED token");
        Block block = block();
        return new Embedded(block);
    }

    private InputPort inputPort() {
        consume(INPUTPORT, "Expected INPUTPORT token");
        JolieToken inputPortId = consumeAndReturn(ID, "Expected ID token after INPUTPORT in inputPort");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in inputPort");
        ArrayList<Node> portParams = new ArrayList<>();
        while (peek().getType() != RIGHT_BRACE) {
            JolieToken token = peek();
            switch (token.getType()) {
                case LOCATION:
//                    System.out.println("LOCATION");
                    portParams.add(portLocation());
                    break;

                case PROTOCOL:
//                    System.out.println("PROTOCOL");
                    portParams.add(portProtocol());
                    break;

                case INTERFACES:
//                    System.out.println("INTERFACES");
                    portParams.add(portInterfaces());
                    break;

                case AGGREGATES:
//                    System.out.println("AGGREGATES");
                    portParams.add(portAggregates());
                    break;

                case REDIRECTS:
//                    System.out.println("REDIRECTS");
                    portParams.add(portRedirects());
                    break;

                default:
                    System.out.println("error? in inputPort");
                    break;
            }
        }
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after port params in inputPort");
        return new InputPort(inputPortId, portParams);
    }

    private OutputPort outputPort() {
        consume(OUTPUTPORT, "Expected OUTPUTPORT token");
        JolieToken outputPortId = consumeAndReturn(ID, "Expected ID token after OUTPUTPORT in outputPort");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in outputPort");
        ArrayList<Node> portParams = new ArrayList<>();
        while (peek().getType() != RIGHT_BRACE) {
            JolieToken token = peek();
            switch (token.getType()) {
                case LOCATION:
                    portParams.add(portLocation());
                    break;

                case PROTOCOL:
                    portParams.add(portProtocol());
                    break;

                case INTERFACES:
                    portParams.add(portInterfaces());
                    break;
            }
        }
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after port params in outputPort");

        return new OutputPort(outputPortId, portParams);
    }

    // ----

    private PortLocation portLocation() {
        JolieToken location = consumeAndReturn(LOCATION, "Expected LOCATION token");
        consume(COLON, "Expected COLON token after LOCATION in location");
        Line line = readLine(previous().getLine());
        return new PortLocation(line, location);
    }

    private PortProtocol portProtocol() {
        JolieToken protocol = consumeAndReturn(PROTOCOL, "Expected PROTOCOL token");
        consume(COLON, "Expected COLON token after PROTOCOL in protocol");
        Line line = readLine(previous().getLine());
        return new PortProtocol(line, protocol);
    }

    private PortInterfaces portInterfaces() {
        JolieToken interfaces = consumeAndReturn(INTERFACES, "Expected INTERFACES token");
        consume(COLON, "Expected COLON token after INTERFACES in interfaces");
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(readLine(previous().getLine())); // TODO: account for multiline arguments
        return new PortInterfaces(lines, interfaces);
    }

    private PortAggregates portAggregates() {
        JolieToken aggregates = consumeAndReturn(AGGREGATES, "Expected AGGREGATES token");
        consume(COLON, "Expected COLON token after AGGREGATES in aggregates");
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(readLine(previous().getLine())); // TODO: account for multiline arguments
        return new PortAggregates(lines, aggregates);
    }

    private PortRedirects portRedirects() {
        JolieToken redirects = consumeAndReturn(REDIRECTS, "Expected REDIRECTS token");
        consume(COLON, "Expected COLON token after REDIRECTS in redirects");
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(readLine(previous().getLine())); // TODO: account for multiline arguments
        return new PortRedirects(lines, redirects);
    }

    // ----

    private Init init() {
        consume(INIT, "Expected INIT token");
        Block block = block();
        return new Init(block);
    }

    private Main mainE() {
        consume(MAIN, "Expected MAIN token");
        Block block = block();
        return new Main(block);
    }

    private Courier courier() {
        consume(COURIER, "Expected COURIER token");
        consume(LESS_THAN, "Expected LESS_THAN token after COURIER in courier");
        JolieToken courierId = consumeAndReturn(ID, "Expected ID token after LESS_THAN in courier");
        consume(GREATER_THAN, "Expected GREATER_THAN token after ID in courier");
        Block block = block();
        return new Courier(courierId, block);
    }

    private DefineProcedure defineProcedure() {
        consume(DEFINE, "Expected DEFINE token");
        JolieToken defineProcedureId = consumeAndReturn(ID, "Expected ID token after DEFINE in defineProcedure");
        Block block = block();
        return new DefineProcedure(defineProcedureId, block);
    }

    // ----

    private Block block() {
        consume(LEFT_BRACE, "Expected LEFT_BRACE token in block");
        ArrayList<JolieToken> contents = new ArrayList<>();
        int openBraces = 0;
        while (peek().getType() != RIGHT_BRACE || openBraces != 0) {
            JolieToken token = advance();
            if (token.getType() == LEFT_BRACE) {
                openBraces++;
            } else if (token.getType() == RIGHT_BRACE) {
                openBraces--;
            }
            contents.add(token);
//            System.out.println(previous().getType() + " | " + previous().getLexeme() + " | " + openBraces);
        }
//        System.out.println(" ");
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token in block");
        return new Block(contents);
    }

    private Line readLine(int lineNumber) {
        StringBuilder line = new StringBuilder();
        if (peek() != null && peek().getLine() == lineNumber) {
            line.append(peek().getPreLexeme());
        }
        while (peek() != null && peek().getLine() == lineNumber) {
            JolieToken token = advance();
            line.append(token.getLexeme());
            line.append(token.getPostLexeme());
        }

        return new Line(line.toString(), lineNumber);
    }

    //---------------------------------------------------------------

    private void consume(JolieTokenType type, String message) {
        if (match(type)) {
            JolieToken nextToken = peek();
            JolieToken token = previous();
            String fullNewLexeme = token.getPreLexeme() + token.getLexeme() + token.getPostLexeme();
            nextToken.setPreLexeme(fullNewLexeme);
            return;
        }

        throw error(previous(), message);
    }

    private JolieToken consumeAndReturn(JolieTokenType type, String message) {
        if (match(type)) {
            return previous();
        }

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
        if (this.tokens.get(this.current).getType() == type) {
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
        System.out.println(token + " | " + message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
    }

}

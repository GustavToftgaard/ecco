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
        while (!isAtEnd() && peek().type != EOF) {
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
        switch (token.type) {
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
                res = readLine(token.line);
                break;
        }
        return res;
    }

    // ----

    private ImportDecl importDecl() {
        Node res = null;
        JolieToken token = peek();
        if (token.type == FROM) {
            consume(FROM, "Expected FROM token in import decl");
            JolieToken fromID = consume(ID, "Expected ID after FROM in import decl");
            consume(IMPORT, "Expected IMPORT after ID in import decl");
            JolieToken importID = consume(ID, "Expected ID after IMPORT in import decl");

            // check of multiple 'as' statements
            if (previous().line == peek().line) {
                Node line = readLine(previous().line);
                return new ImportDecl(new Import(fromID, importID, line));
            } else {
                return new ImportDecl(new Import(fromID, importID));
            }
        } else {
            consume(INCLUDE, "Expected INCLUDE token in import decl");
            JolieToken id = consume(STRING, "Expected string as ID after INCLUDE in import decl");
            return new ImportDecl(new Include(id));
        }
    }

    // ----

    private InterfaceDecl interfaceDecl() {
        boolean hasExtender = false;
        consume(INTERFACE, "Expected INTERFACE token");
        if (peek().type == EXTENDER) {
            consume(EXTENDER, "Expected EXTENDER token in interface decl");
            hasExtender = true;
        }
        JolieToken interfaceID = consume(ID, "Expected ID token after interface decl");
        Block block = block();

        return new InterfaceDecl(interfaceID, hasExtender, block);

        // can be expanded further with OW and RR
    }

    // ----

    private TypeDecl typeDecl() {
        consume(TYPE, "Expected TYPE token");
        JolieToken typeId = consume(ID, "Expected ID token after TYPE in type decl");
        // consume(COLON, "Expected COLON token after ID in type decl"); // TODO
        if (peek().type == ID) {
            JolieToken typeTypeId = consume(ID, "Expected ID token after COLON in type decl");
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
        JolieToken serviceId = consume(ID, "Expected ID token after SERVICE in service decl");

        ArrayList<JolieToken> params = new ArrayList<>();
        if (peek().type == LEFT_PAREN) {
            consume(LEFT_PAREN, "Expected LEFT_PAREN token in service decl");
//            while (peek().type != RIGHT_PAREN) { // TODO: figure our params
//                JolieToken id = consume(ID, "Expected ID token in service decl");
//            }
            readLine(previous().line); // temp
            consume(RIGHT_PAREN, "Expected RIGHT_BRACE token in service decl");
            hasParams = true;
        }

        consume(LEFT_BRACE, "Expected LEFT_BRACE token in service decl");
        ArrayList<Node> services = new ArrayList<>();
        while (peek().type != RIGHT_BRACE) {
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
        switch (token.type) {
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
                res = readLine(token.line);
                break;
        }
        return res;
    }

    private Execution execution() {
        consume(EXECUTION, "Expected EXECUTION token");
        if (peek().type == COLON) {
            JolieToken colon = consume(COLON, "Expected COLON token after EXECUTION in execution");
            JolieToken executionId = consume(ID, "Expected ID token after COLON in execution");
            return new Execution(executionId, colon);
        } else {
            consume(LEFT_BRACE, "Expected LEFT_BRACE token after EXECUTION in execution");
            JolieToken executionId = consume(ID, "Expected ID token after LEFT_BRACE in execution");
            consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after ID in execution");
            return new Execution(executionId);
        }
    }

    private Embed embed() {
        consume(EMBED, "Expected EMBED token");
        JolieToken embedId = consume(ID, "Expected ID token after EMBED in embed");
        consume(AS, "Expected AS token after in embed");
        JolieToken asId = consume(ID, "Expected ID token after AS in embed");
        return new Embed(embedId, asId);
    }

    private Embedded embedded() {
        consume(EMBEDDED, "Expected EMBEDDED token");
        Block block = block();
        return new Embedded(block);
    }

    private InputPort inputPort() {
        consume(INPUTPORT, "Expected INPUTPORT token");
        JolieToken inputPortId = consume(ID, "Expected ID token after INPUTPORT in inputPort");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in inputPort");
        ArrayList<Node> portParams = new ArrayList<>();
        while (peek().type != RIGHT_BRACE) {
            JolieToken token = peek();
            switch (token.type) {
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
        JolieToken outputPortId = consume(ID, "Expected ID token after OUTPUTPORT in outputPort");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in outputPort");
        ArrayList<Node> portParams = new ArrayList<>();
        while (peek().type != RIGHT_BRACE) {
            JolieToken token = peek();
            switch (token.type) {
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

    private PortLocation portLocation() { // TODO: account for capital letter or not
        consume(LOCATION, "Expected LOCATION token");
        consume(COLON, "Expected COLON token after LOCATION in location");
        Line line = readLine(previous().line);
        return new PortLocation(line);
    }

    private PortProtocol portProtocol() { // TODO: account for capital letter or not
        consume(PROTOCOL, "Expected PROTOCOL token");
        consume(COLON, "Expected COLON token after PROTOCOL in protocol");
        Line line = readLine(previous().line);
        return new PortProtocol(line);
    }

    private PortInterfaces portInterfaces() { // TODO: account for capital letter or not
        consume(INTERFACES, "Expected INTERFACES token");
        consume(COLON, "Expected COLON token after INTERFACES in interfaces");
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(readLine(previous().line)); // TODO: account for multiline arguments
        return new PortInterfaces(lines);
    }

    private PortAggregates portAggregates() { // TODO: account for capital letter or not
        consume(AGGREGATES, "Expected AGGREGATES token");
        consume(COLON, "Expected COLON token after AGGREGATES in aggregates");
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(readLine(previous().line)); // TODO: account for multiline arguments
        return new PortAggregates(lines);
    }

    private PortRedirects portRedirects() { // TODO: account for capital letter or not
        consume(REDIRECTS, "Expected REDIRECTS token");
        consume(COLON, "Expected COLON token after REDIRECTS in redirects");
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(readLine(previous().line)); // TODO: account for multiline arguments
        return new PortRedirects(lines);
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
        JolieToken courierId = consume(ID, "Expected ID token after LESS_THAN in courier");
        consume(GREATER_THAN, "Expected GREATER_THAN token after ID in courier");
        Block block = block();
        return new Courier(courierId, block);
    }

    private DefineProcedure defineProcedure() {
        consume(DEFINE, "Expected DEFINE token");
        JolieToken defineProcedureId = consume(ID, "Expected ID token after DEFINE in defineProcedure");
        Block block = block();
        return new DefineProcedure(defineProcedureId, block);
    }

    // ----

    private Block block() {
        consume(LEFT_BRACE, "Expected LEFT_BRACE token in block");
        ArrayList<JolieToken> contents = new ArrayList<>();
        int openBraces = 0;
        while (peek().type != RIGHT_BRACE || openBraces != 0) {
            JolieToken token = advance();
            if (token.type == LEFT_BRACE) {
                openBraces++;
            } else if (token.type == RIGHT_BRACE) {
                openBraces--;
            }
            contents.add(token);
//            System.out.println(previous().type + " | " + previous().lexeme + " | " + openBraces);
        }
//        System.out.println(" ");
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token in block");
        return new Block(contents);
    }

    private Line readLine(int lineNumber) {
        ArrayList<JolieToken> contents = new ArrayList<>();
        while (peek().line == lineNumber) {
            contents.add(advance());
        }
        return new Line(contents);
    }

    //---------------------------------------------------------------

    private JolieToken consume(JolieTokenType type, String message) {
        if (match(type)) return previous(); // TODO: add checking for comment token

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
        System.out.println(token + " | " + message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
    }

}

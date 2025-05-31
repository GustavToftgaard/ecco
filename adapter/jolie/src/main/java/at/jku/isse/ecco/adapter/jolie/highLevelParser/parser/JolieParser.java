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


/**
 * The {@code JolieParser} class provides functionality
 * for converting a list of {@link JolieToken} into an AST.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * JolieParser jolieParser = new JolieParser(???); // List of JolieToken
 * List<Node> statements = jolieParser.parse();
 * }</pre>
 * </p>
 *
 * @see at.jku.isse.ecco.adapter.jolie.highLevelParser.JolieFileToAST
 *
 * @author Gustav Toftgaard <gustav@familientoftgaard.dk>
 */
public class JolieParser {
    private final List<JolieToken> tokens;
    private int current = 0;
    private JolieToken lastConsumedAndReturnedToken = null;
    private String savedLexeme = "";
    private final List<Node> ast = new LinkedList<>();

    public List<Node> getAst() {
        return ast;
    }

    public JolieParser(List<JolieToken> tokens) {
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
                System.out.println(e.getMessage());
            }
        }
        peek().addToStartPreLexeme(savedLexeme);
        ast.add(new EndOfFile(peek()));
    }

    //---------------------------------------------------------------

    // decl
    private Node decl() {
        Node res;
        JolieToken token = peek();
        switch (token.getType()) {
            case FROM:
            case INCLUDE:
                res = importDecl();
                break;

            case INTERFACE:
                res = interfaceDecl();
                break;

            case TYPE:
                res = typeDecl();
                break;

            case SERVICE:
                res = serviceDecl();
                break;

            default:
                res = null;
                break;
        }
        return res;
    }

    // ----

    private ImportDecl importDecl() {
        lastConsumedAndReturnedToken = null;
        Import importE = null;
        Include include = null;

        if (peek().getType() == FROM) {
            consume(FROM, "Expected FROM token in import decl");
            JolieToken fromID = consumeAndReturn(ID, "Expected ID after FROM in import decl");
            consume(IMPORT, "Expected IMPORT after ID in import decl");
            JolieToken importID = consumeAndReturn(ID, "Expected ID after IMPORT in import decl");

            // check of multiple 'as' statements
            if (previous().getLine() == peek().getLine() && peek().getType() != EOF) {
                Node line = readLine(previous().getLine());
                importE = new Import(fromID, importID, line);

            } else {
                importE = new Import(fromID, importID);
            }

        } else {
            consume(INCLUDE, "Expected INCLUDE token in import decl");
            JolieToken id = consumeAndReturn(STRING, "Expected string as ID after INCLUDE in import decl");
            include = new Include((id));
        }

        ImportDecl importDecl = new ImportDecl(importE, include);
        endNode(importDecl);
        return importDecl;
    }

    // ----

    private InterfaceDecl interfaceDecl() {
        lastConsumedAndReturnedToken = null;

        consume(INTERFACE, "Expected INTERFACE token");

        if (peek().getType() == EXTENDER) {
            consume(EXTENDER, "Expected EXTENDER token in interface decl");
        }

        JolieToken interfaceID = consumeAndReturn(ID, "Expected ID token in interface decl");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in interface decl");

        RequestResponseDecl requestResponseDecl = null;

        if (peek().getType() == REQUEST_RESPONSE) {
            ArrayList<RequestResponseElement> requestResponseElements = new ArrayList<>();
            consume(REQUEST_RESPONSE, "Expected REQUEST_RESPONSE token after ID in interface decl");
            consume(COLON, "Expected COLON token after REQUEST_RESPONSE in interface decl");
            while (peek().getType() == ID) {
                requestResponseElements.add(requestResponseElement());
                if (peek().getType() == ID) {
                    consume(ID, "Expected ID (',') token after IDs from R.R. in interface decl");
                }
            }
            requestResponseDecl = new RequestResponseDecl(requestResponseElements);
        }

        OneWayDecl oneWayDecl = null;
        if (peek().getType() == ONE_WAY) {
            ArrayList<OneWayElement> oneWayElements = new ArrayList<>();
            consume(ONE_WAY, "Expected ONE_WAY token after ID or REQUEST_RESPONSE in interface decl");
            consume(COLON, "Expected COLON token after ONE_WAY in interface decl");
            while (peek().getType() == ID) {
                oneWayElements.add(oneWayElement());
                if (peek().getType() == ID) {
                    consume(ID, "Expected ID (',') token after IDs from O.W. in interface decl");
                }
            }
            oneWayDecl = new OneWayDecl(oneWayElements);
        }

        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token in interface decl");

        InterfaceDecl interfaceDecl = new InterfaceDecl(interfaceID, requestResponseDecl, oneWayDecl);
        endNode(interfaceDecl);
        return interfaceDecl;
    }

    private RequestResponseElement requestResponseElement() {
        lastConsumedAndReturnedToken = null;

        JolieToken functionID = consumeAndReturn(ID, "Expected ID token in R.R. element");
        consume(LEFT_PAREN, "Expected LEFT_PAREN token after ID in R.R. element");
        JolieToken requestID = consumeAndReturn(ID, "Expected ID token after LEFT_PAREN in R.R. element 1");
        consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after ID in R.R. element");
        consume(LEFT_PAREN, "Expected LEFT_PAREN token after RIGHT_PAREN in R.R. element");
        JolieToken responseID = consumeAndReturn(ID, "Expected ID token after LEFT_PAREN in R.R. element 2");
        consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after ID in R.R. element");

        RequestResponseElement requestResponseElement = new RequestResponseElement(functionID, requestID, responseID);
        endNode(requestResponseElement);
        return requestResponseElement;
    }

    private OneWayElement oneWayElement() {
        lastConsumedAndReturnedToken = null;

        JolieToken functionID = consumeAndReturn(ID, "Expected ID token in O.W. element");
        consume(LEFT_PAREN, "Expected LEFT_PAREN token after ID in O.W. element");
        JolieToken requestID = consumeAndReturn(ID, "Expected ID token after LEFT_PAREN in O.W. element");
        consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after ID in O.W. element");

        OneWayElement oneWayElement = new OneWayElement(functionID, requestID);
        endNode(oneWayElement);
        return oneWayElement;
    }

    // ----

    private TypeDecl typeDecl() {
        lastConsumedAndReturnedToken = null;

        consume(TYPE, "Expected TYPE token");
        JolieToken typeId = consumeAndReturn(ID, "Expected ID token after TYPE in type decl");
        ArrayList<JolieToken> typeTypesId = new ArrayList<>();
        Block block = null;

        if (peek().getType() == COLON) {
            consume(COLON, "Expected COLON token after ID in type decl");
            typeTypesId.add(consumeAndReturn(ID, "Expected ID token after COLON in type decl"));

            while (!isAtEnd() && peek().getType() == VERTICAL_BAR) {
                consume(VERTICAL_BAR, "Expected VERTICAL_BAR token after ID in type decl");
                typeTypesId.add(consumeAndReturn(ID, "Expected ID token after VERTICAL_BAR in type decl"));
            }
        }

        if (peek().getType() == LEFT_BRACE){
            block = block();
        }

        TypeDecl typeDecl = new TypeDecl(typeId, typeTypesId, block);
        endNode(typeDecl);
        return typeDecl;
    }

    // ----

    private ServiceDecl serviceDecl() {
        lastConsumedAndReturnedToken = null;

        consume(SERVICE, "Expected SERVICE token");
        JolieToken serviceId = consumeAndReturn(ID, "Expected ID token after SERVICE in service decl");

        ArrayList<JolieToken> params = null;
        if (peek().getType() == LEFT_PAREN) {
            params = new ArrayList<>();
            consume(LEFT_PAREN, "Expected LEFT_PAREN token in service decl");
            while (peek().getType() != RIGHT_PAREN) {
                params.add(consumeAndReturnNoTypeCheck());
            }
            consume(RIGHT_PAREN, "Expected RIGHT_BRACE token in service decl");
        }

        consume(LEFT_BRACE, "Expected LEFT_BRACE token in service decl");
        ArrayList<Node> services = new ArrayList<>();
        while (peek().getType() != RIGHT_BRACE) {
            services.add(service());
        }
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token in service decl");

        ServiceDecl serviceDecl = new ServiceDecl(serviceId, services, params);
        endNode(serviceDecl);
        return serviceDecl;
    }

    private Node service() {
        lastConsumedAndReturnedToken = null;

        Node res;
        JolieToken token = peek();
        switch (token.getType()) {
            case EXECUTION:
                res = execution();
                break;

            case EMBED:
                res = embed();
                break;

            case EMBEDDED:
                res = embedded();
                break;

            case INPUTPORT:
                res = inputPort();
                break;

            case OUTPUTPORT:
                res = outputPort();
                break;

            case INIT:
                res = init();
                break;

            case MAIN:
                res = mainE();
                break;

            case COURIER:
                res = courier();
                break;

            case DEFINE:
                res = defineProcedure();
                break;

            default:
                res = readLine(token.getLine());
                break;
        }

        return res;
    }

    private Execution execution() {
        lastConsumedAndReturnedToken = null;

        consume(EXECUTION, "Expected EXECUTION token");

        JolieToken executionId;

        if (peek().getType() == COLON) {
            consume(COLON, "Expected COLON token after EXECUTION in execution");
            executionId = consumeAndReturn(ID, "Expected ID token after COLON in execution");

        } else {
            consume(LEFT_BRACE, "Expected LEFT_BRACE token after EXECUTION in execution");
            executionId = consumeAndReturn(ID, "Expected ID token after LEFT_BRACE in execution");
            consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after ID in execution");
        }

        Execution execution = new Execution(executionId);
        endNode(execution);
        return execution;
    }

    private Embed embed() {
        lastConsumedAndReturnedToken = null;

        consume(EMBED, "Expected EMBED token");
        JolieToken embedId = consumeAndReturn(ID, "Expected ID token after EMBED in embed");

        ArrayList<JolieToken> params = null;
        if (peek().getType() == LEFT_PAREN) {
            consume(LEFT_PAREN, "Expected LEFT_PAREN token after ID in embed");
            params = new ArrayList<>();
            while (peek().getType() != RIGHT_PAREN) {
                params.add(consumeAndReturnNoTypeCheck());
            }
            consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after tokens in embed");
        }

        if (peek().getType() == AS) {
            consume(AS, "Expected AS token after ID in embed");
        } else {
            consume(IN, "Expected IN token after ID in embed");
        }
        JolieToken asId = consumeAndReturn(ID, "Expected ID token after AS in embed");

        Embed embed = new Embed(embedId, asId, params);
        endNode(embed);
        return embed;
    }

    private Embedded embedded() {
        lastConsumedAndReturnedToken = null;

        consume(EMBEDDED, "Expected EMBEDDED token");
        Block block = block();

        Embedded embedded = new Embedded(block);
        endNode(embedded);
        return embedded;
    }

    private InputPort inputPort() {
        lastConsumedAndReturnedToken = null;

        consume(INPUTPORT, "Expected INPUTPORT token");
        JolieToken inputPortId = consumeAndReturn(ID, "Expected ID token after INPUTPORT in inputPort");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in inputPort");

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

                case AGGREGATES:
                    portParams.add(portAggregates());
                    break;

                case REDIRECTS:
                    portParams.add(portRedirects());
                    break;

                default:
                    System.out.println("Parser Error: inputPort");
                    advance();
                    break;
            }
        }

        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after port params in inputPort");

        InputPort inputPort = new InputPort(inputPortId, portParams);
        endNode(inputPort);
        return inputPort;
    }

    private OutputPort outputPort() {
        lastConsumedAndReturnedToken = null;

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
                default:
                    System.out.println("Parser Error: outputPort");
                    advance();
                    break;
            }
        }
        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token after port params in outputPort");

        OutputPort outputPort = new OutputPort(outputPortId, portParams);
        endNode(outputPort);
        return outputPort;
    }

    // ----

    private PortLocation portLocation() {
        lastConsumedAndReturnedToken = null;

        consume(LOCATION, "Expected LOCATION token");
        consume(COLON, "Expected COLON token after LOCATION in location");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        int numberOfOpenBraces = 0;
        while (readNextTokenInPortParams(numberOfOpenBraces)) {
            arguments.add(consumeAndReturnNoTypeCheck());
            if (previous().getType() == LEFT_BRACE) {
                numberOfOpenBraces++;
            } else if (previous().getType() == RIGHT_BRACE) {
                numberOfOpenBraces--;
            }
        }

        PortLocation portLocation = new PortLocation(arguments);
        endNode(portLocation);
        return portLocation;
    }

    private PortProtocol portProtocol() {
        lastConsumedAndReturnedToken = null;

        consume(PROTOCOL, "Expected PROTOCOL token");
        consume(COLON, "Expected COLON token after PROTOCOL in protocol");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        int numberOfOpenBraces = 0;
        while (readNextTokenInPortParams(numberOfOpenBraces)) {
            arguments.add(consumeAndReturnNoTypeCheck());
            if (previous().getType() == LEFT_BRACE) {
                numberOfOpenBraces++;
            } else if (previous().getType() == RIGHT_BRACE) {
                numberOfOpenBraces--;
            }
        }

        PortProtocol portProtocol = new PortProtocol(arguments);
        endNode(portProtocol);
        return portProtocol;
    }

    private PortInterfaces portInterfaces() {
        lastConsumedAndReturnedToken = null;

        consume(INTERFACES, "Expected INTERFACES token");
        consume(COLON, "Expected COLON token after INTERFACES in interfaces");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        int numberOfOpenBraces = 0;
        while (readNextTokenInPortParams(numberOfOpenBraces)) {
            arguments.add(consumeAndReturnNoTypeCheck());
            if (previous().getType() == LEFT_BRACE) {
                numberOfOpenBraces++;
            } else if (previous().getType() == RIGHT_BRACE) {
                numberOfOpenBraces--;
            }
        }

        PortInterfaces portInterfaces = new PortInterfaces(arguments);
        endNode(portInterfaces);
        return portInterfaces;
    }

    private PortAggregates portAggregates() {
        lastConsumedAndReturnedToken = null;

        consume(AGGREGATES, "Expected AGGREGATES token");
        consume(COLON, "Expected COLON token after AGGREGATES in aggregates");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        int numberOfOpenBraces = 0;
        while (readNextTokenInPortParams(numberOfOpenBraces)) {
            arguments.add(consumeAndReturnNoTypeCheck());
            if (previous().getType() == LEFT_BRACE) {
                numberOfOpenBraces++;
            } else if (previous().getType() == RIGHT_BRACE) {
                numberOfOpenBraces--;
            }
        }

        PortAggregates portAggregates = new PortAggregates(arguments);
        endNode(portAggregates);
        return portAggregates;
    }

    private PortRedirects portRedirects() {
        lastConsumedAndReturnedToken = null;

        consume(REDIRECTS, "Expected REDIRECTS token");
        consume(COLON, "Expected COLON token after REDIRECTS in redirects");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        int numberOfOpenBraces = 0;
        while (readNextTokenInPortParams(numberOfOpenBraces)) {
            arguments.add(consumeAndReturnNoTypeCheck());
            if (previous().getType() == LEFT_BRACE) {
                numberOfOpenBraces++;
            } else if (previous().getType() == RIGHT_BRACE) {
                numberOfOpenBraces--;
            }
        }

        PortRedirects portRedirects = new PortRedirects(arguments);
        endNode(portRedirects);
        return portRedirects;
    }

    // helper function
    private boolean readNextTokenInPortParams(int numberOfOpenBraces) {
        JolieTokenType cur = peek().getType();
        boolean res = false;

        if (numberOfOpenBraces == 0) {
            res = cur != RIGHT_BRACE &&
                    cur != LOCATION &&
                    cur != PROTOCOL &&
                    cur != INTERFACES &&
                    cur != AGGREGATES &&
                    cur != REDIRECTS;
        } else {
            res = previous().getType() != RIGHT_BRACE && // use previous because we have open braces
                    cur != LOCATION &&
                    cur != PROTOCOL &&
                    cur != INTERFACES &&
                    cur != AGGREGATES &&
                    cur != REDIRECTS;
        }
        return res;
    }

    // ----

    private Init init() {
        lastConsumedAndReturnedToken = null;

        consume(INIT, "Expected INIT token");
        Block block = block();

        Init init = new Init(block);
        endNode(init);
        return init;
    }

    private Main mainE() {
        lastConsumedAndReturnedToken = null;

        consume(MAIN, "Expected MAIN token");
        Block block = block();

        Main main = new Main(block);
        endNode(main);
        return main;
    }

    private Courier courier() {
        lastConsumedAndReturnedToken = null;

        consume(COURIER, "Expected COURIER token");
        consume(LESS_THAN, "Expected LESS_THAN token after COURIER in courier");
        JolieToken courierId = consumeAndReturn(ID, "Expected ID token after LESS_THAN in courier");
        consume(GREATER_THAN, "Expected GREATER_THAN token after ID in courier");
        Block block = block();

        Courier courier = new Courier(courierId, block);
        endNode(courier);
        return courier;
    }

    private DefineProcedure defineProcedure() {
        lastConsumedAndReturnedToken = null;

        consume(DEFINE, "Expected DEFINE token");
        JolieToken defineProcedureId = consumeAndReturn(ID, "Expected ID token after DEFINE in defineProcedure");
        Block block = block();

        DefineProcedure defineProcedure = new DefineProcedure(defineProcedureId, block);
        endNode(defineProcedure);
        return defineProcedure;
    }

    // ----

    private Block block() {
        lastConsumedAndReturnedToken = null;

        ArrayList<Line> contents = new ArrayList<>();
        int openBraces = 0;
        int lineNumber = peek().getLine();
        StringBuilder line = new StringBuilder();

        JolieToken leftBrace = consumeAndReturn(LEFT_BRACE, "Expected LEFT_BRACE token in block");
        line.append(leftBrace.getPreLexeme()).append(leftBrace.getLexeme()).append(leftBrace.getPostLexeme());

        while (peek().getType() != RIGHT_BRACE || openBraces != 0) {
            JolieToken token = advance();

            if (token.getType() == LEFT_BRACE) {
                openBraces++;

            } else if (token.getType() == RIGHT_BRACE) {
                openBraces--;
            }

            if (token.getLine() != lineNumber || (token.getType() == RIGHT_BRACE && openBraces == 0 && peek().getLine() != lineNumber)) {
                contents.add(new Line(line.toString(), lineNumber));
                line = new StringBuilder();
                lineNumber = token.getLine();
            }
            line.append(token.getPreLexeme()).append(token.getLexeme()).append(token.getPostLexeme());
        }

        if (peek().getLine() != lineNumber) { // check if RIGHT_BRACE is on a different line
            contents.add(new Line(line.toString(), lineNumber));
            line = new StringBuilder();
            lineNumber = peek().getLine();
        }

        JolieToken rightBrace = consumeAndReturn(RIGHT_BRACE, "Expected RIGHT_BRACE token in block");
        line.append(rightBrace.getPreLexeme()).append(rightBrace.getLexeme()).append(rightBrace.getPostLexeme());

        contents.add(new Line(line.toString(), lineNumber));

        Block block = new Block(contents);
        endNode(block);
        return block;
    }

    private Line readLine(int lineNumber) {
        lastConsumedAndReturnedToken = null;
        StringBuilder line = new StringBuilder();

        while (peek().getType() != EOF && peek().getLine() == lineNumber) {
            JolieToken token = advance();
            line.append(token.getPreLexeme()).append(token.getLexeme()).append(token.getPostLexeme());
        }

        Line lineE = new Line(line.toString(), lineNumber);
        endNode(lineE);
        return lineE;
    }

    //---------------------------------------------------------------

    private void endNode(Node node) {
        if (lastConsumedAndReturnedToken == null) {
            node.setPostLexeme(savedLexeme);
            savedLexeme = "";
        } else {
            lastConsumedAndReturnedToken = null;
        }
    }

    private void consume(JolieTokenType type, String message) {
        if (match(type)) {
            JolieToken token = previous();
            String fullNewLexeme = token.getPreLexeme() + token.getLexeme() + token.getPostLexeme();
            if (lastConsumedAndReturnedToken == null) {
                savedLexeme += fullNewLexeme;
            } else {
                lastConsumedAndReturnedToken.addToPostLexeme(fullNewLexeme);
            }
        } else {
            throw error(previous(), message);
        }
    }

    private JolieToken consumeAndReturn(JolieTokenType type, String message) {
        if (match(type)) {
            if (lastConsumedAndReturnedToken == null) {
                previous().addToStartPreLexeme(savedLexeme);
                savedLexeme = "";
            }
            this.lastConsumedAndReturnedToken = previous();
            return previous();
        }

        throw error(previous(), message);
    }

    private JolieToken consumeAndReturnNoTypeCheck() {
        if (peek().getType() == COMMENT) { // skip comment tokens
            advance();
        }

        advance();
        if (lastConsumedAndReturnedToken == null) {
            previous().addToStartPreLexeme(savedLexeme);
            savedLexeme = "";
        }
        this.lastConsumedAndReturnedToken = previous();
        return previous();
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
        return this.tokens.get(this.current);
    }


    private boolean isAtEnd() {
        return this.current >= (this.tokens.size() - 1);
    }

    private ParseError error(JolieToken token, String message) {
        System.out.println(token + " | " + message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
    }

}
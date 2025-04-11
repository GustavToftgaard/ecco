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
    private final List<JolieToken> tokens;
    private int current = 0;
    private final List<Node> ast = new LinkedList<>();

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
                System.out.println(e.getMessage()); // temp
            }
        }
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

            // TODO: Missing case for going around serviceDecl to service

            default:
                res = readLine(token.getLine());
                break;
        }
        return res;
    }

    // ----

    private ImportDecl importDecl() {
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

        if (peek().getType() == EXTENDER) {
            consume(EXTENDER, "Expected EXTENDER token in interface decl");
        }
        JolieToken interfaceID = consumeAndReturn(ID, "Expected ID token in interface decl");

        consume(LEFT_BRACE, "Expected LEFT_BRACE token after ID in interface decl");

        ArrayList<RequestResponseElement> requestResponseElements = new ArrayList<>();
        if (peek().getType() == REQUEST_RESPONSE) {
            consume(REQUEST_RESPONSE, "Expected REQUEST_RESPONSE token after ID in interface decl");
            consume(COLON, "Expected COLON token after REQUEST_RESPONSE in interface decl");
            while (peek().getType() == ID) {
                requestResponseElements.add(requestResponseElement());
                if (peek().getType() == ID) {
                    consume(ID, "Expected ID (',') token after IDs from R.R. in interface decl");
                }
            }
        }

        ArrayList<OneWayElement> oneWayElements = new ArrayList<>();
        if (peek().getType() == ONE_WAY) {
            consume(ONE_WAY, "Expected ONE_WAY token after ID or REQUEST_RESPONSE in interface decl");
            consume(COLON, "Expected COLON token after ONE_WAY in interface decl");
            while (peek().getType() == ID) {
                oneWayElements.add(oneWayElement());
                if (peek().getType() == ID) {
                    consume(ID, "Expected ID (',') token after IDs from O.W. in interface decl");
                }
            }
        }

        consume(RIGHT_BRACE, "Expected RIGHT_BRACE token in interface decl");

        if (!requestResponseElements.isEmpty() && !oneWayElements.isEmpty()) {
            return new InterfaceDecl(interfaceID, new RequestResponseDecl(requestResponseElements), new OneWayDecl(oneWayElements));

        } else if (!requestResponseElements.isEmpty()) {
            return new InterfaceDecl(interfaceID, new RequestResponseDecl(requestResponseElements));

        } else if (!oneWayElements.isEmpty()) {
            return new InterfaceDecl(interfaceID, new OneWayDecl(oneWayElements));
        }

        return null; // ERROR
    }

    private RequestResponseElement requestResponseElement() {
        JolieToken functionID = consumeAndReturn(ID, "Expected ID token in R.R. element");
        consume(LEFT_PAREN, "Expected LEFT_PAREN token after ID in R.R. element");
        JolieToken requestID = consumeAndReturn(ID, "Expected ID token after LEFT_PAREN in R.R. element");
        consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after ID in R.R. element");
        consume(LEFT_PAREN, "Expected LEFT_PAREN token after RIGHT_PAREN in R.R. element");
        JolieToken responseID = consumeAndReturn(ID, "Expected ID token after LEFT_PAREN in R.R. element");
        consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after ID in R.R. element");
        return new RequestResponseElement(functionID, requestID, responseID);
    }

    private OneWayElement oneWayElement() {
        JolieToken functionID = consumeAndReturn(ID, "Expected ID token in O.W. element");
        consume(LEFT_PAREN, "Expected LEFT_PAREN token after ID in O.W. element");
        JolieToken requestID = consumeAndReturn(ID, "Expected ID token after LEFT_PAREN in O.W. element");
        consume(RIGHT_PAREN, "Expected RIGHT_PAREN token after ID in O.W. element");
        return new OneWayElement(functionID, requestID);
    }

    // ----

    private TypeDecl typeDecl() {
        consume(TYPE, "Expected TYPE token");
        JolieToken typeId = consumeAndReturn(ID, "Expected ID token after TYPE in type decl");

        if (peek().getType() == COLON) {
            consume(COLON, "Expected COLON token after ID in type decl");
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
            while (peek().getType() != RIGHT_PAREN) {
                params.add(consumeAndReturn(ID, "Expected ID token in service decl"));
            }
            // readLine(previous().getLine());
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

        return new Execution(executionId);
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
                    System.out.println("error? in inputPort");
                    advance();
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
        consume(LOCATION, "Expected LOCATION token");
        consume(COLON, "Expected COLON token after LOCATION in location");
        Line line = readLine(previous().getLine());
        return new PortLocation(line);
    }

    private PortProtocol portProtocol() {
        consume(PROTOCOL, "Expected PROTOCOL token");
        consume(COLON, "Expected COLON token after PROTOCOL in protocol");
        Line line = readLine(previous().getLine());
        return new PortProtocol(line);
    }

    private PortInterfaces portInterfaces() {
        consume(INTERFACES, "Expected INTERFACES token");
        consume(COLON, "Expected COLON token after INTERFACES in interfaces");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        while (peek().getType() == ID) {
            arguments.add(consumeAndReturn(ID, "Expected ID token as params in interfaces"));
        }

        return new PortInterfaces(arguments);
    }

    private PortAggregates portAggregates() {
        consume(AGGREGATES, "Expected AGGREGATES token");
        consume(COLON, "Expected COLON token after AGGREGATES in aggregates");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        while (peek().getType() == ID) {
            arguments.add(consumeAndReturn(ID, "Expected ID token as params in aggregates"));
        }

        return new PortAggregates(arguments);
    }

    private PortRedirects portRedirects() {
        consume(REDIRECTS, "Expected REDIRECTS token");
        consume(COLON, "Expected COLON token after REDIRECTS in redirects");

        ArrayList<JolieToken> arguments = new ArrayList<>();
        while (peek().getType() == ID) {
            arguments.add(consumeAndReturn(ID, "Expected ID token as params in redirects"));
        }

        return new PortRedirects(arguments);
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
        ArrayList<Line> contents = new ArrayList<>();
        int openBraces = 0;
        int lineNumber = peek().getLine();
        StringBuilder line = new StringBuilder();

        JolieToken leftBrace = consumeAndReturn(LEFT_BRACE, "Expected LEFT_BRACE token in block");
        line.append(leftBrace.getLexeme()).append(leftBrace.getPostLexeme());

        while (peek().getType() != RIGHT_BRACE || openBraces != 0) {
            JolieToken token = advance();

            if (token.getType() == LEFT_BRACE) {
                openBraces++;

            } else if (token.getType() == RIGHT_BRACE) {
                openBraces--;
            }

            if (peek().getType() == RIGHT_BRACE && openBraces == 0) { // check if last token
                if (peek().getLine() != lineNumber) { // check if RIGHT_BRACE is on a different line
                    line.append(token.getLexeme()).append(token.getPostLexeme());
                    contents.add(new Line(line.toString(), lineNumber));
                    line = new StringBuilder();
                    lineNumber = peek().getLine();

                } else {
                    line.append(token.getLexeme()).append(token.getPostLexeme());
                }

            } else if (token.getLine() != lineNumber) { // add line to contents and start new line if token in on a new line
                contents.add(new Line(line.toString(), lineNumber));
                line = new StringBuilder();
                lineNumber = token.getLine();
                line.append(token.getLexeme()).append(token.getPostLexeme());

            } else {
                line.append(token.getLexeme()).append(token.getPostLexeme());
            }
        }

        line.append(consumeAndReturn(RIGHT_BRACE, "Expected RIGHT_BRACE token in block").getLexeme());
        contents.add(new Line(line.toString(), lineNumber));

        return new Block(contents);
    }

    private Line readLine(int lineNumber) {
        StringBuilder line = new StringBuilder();
        if (peek().getType() != EOF && peek().getLine() == lineNumber) {
            line.append(peek().getPreLexeme());
        }
        while (peek().getType() != EOF && peek().getLine() == lineNumber) {
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
        return this.tokens.get(this.current);
    }


    private boolean isAtEnd() {
        return this.current >= (this.tokens.size() - 1);
    }

    private ParseError error(JolieToken token, String message) {
        // TODO: error handling
        System.out.println(token + " | " + message);
        return new ParseError();
    }

    private static class ParseError extends RuntimeException {
    }

}

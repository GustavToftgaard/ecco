package at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

// import Jolie_Parser.HL_Jolie;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;

public class JolieScanner {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new Error("needs path arg");
        }
        try {
            String sampleInputByteString = new String(Files.readAllBytes(Paths.get(args[0])));
            JolieScanner lexer = new JolieScanner(sampleInputByteString);
            List<JolieToken> tokens = lexer.scanTokens();
            for (JolieToken token : tokens) {
                System.out.println(token);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    // Keyword-map
    private static final Map<String, JolieTokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("from", FROM);
        keywords.put("import", IMPORT);
        keywords.put("include", INCLUDE);
        keywords.put("as", AS);

        keywords.put("interface", INTERFACE);
        keywords.put("extender", EXTENDER);
        keywords.put("type", TYPE);

//        keywords.put("requestResponse", REQUEST_RESPONSE);
//        keywords.put("oneWay", ONE_WAY);

        keywords.put("service", SERVICE);
        keywords.put("execution", EXECUTION);
        keywords.put("embed", EMBED);
        keywords.put("embedded", EMBEDDED);

        keywords.put("inputPort", INPUTPORT);
        keywords.put("outputPort", OUTPUTPORT);

        keywords.put("location", LOCATION);
        keywords.put("Location", LOCATION);
        keywords.put("protocol", PROTOCOL);
        keywords.put("Protocol", PROTOCOL);
        keywords.put("interfaces", INTERFACES);
        keywords.put("Interfaces", INTERFACES);

        keywords.put("init", INIT);
        keywords.put("main", MAIN);
        keywords.put("courier", COURIER);

        keywords.put("define", DEFINE);
    }

    // In- and output
    private final String source;
    private final List<JolieToken> tokens = new LinkedList<>();

    // Scanning state
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int numberOfTokens = 0;
    private String preWhitespace = "";

    public JolieScanner(String source) {
        this.source = source;
    }

    // Scan tokens
    public List<JolieToken> scanTokens() {
        while (!isAtEnd()) {
            // beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new JolieToken(EOF, preWhitespace, "", line));
        return tokens;
    }

    // Scan token
    private void scanToken() {
        char c = advance();
        switch (c) {
            // single-char-tokens
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case '[':
                addToken(LEFT_SQUARE_BRACKET);
                break;
            case ']':
                addToken(RIGHT_SQUARE_BRACKET);
                break;
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '<':
                addToken(LESS_THAN);
                break;
            case '>':
                addToken(GREATER_THAN);
                break;
            case ':':
                addToken(COLON);
                break;
            case ';':
                addToken(SEMI_COLON);
                break;

            // white space
            case ' ':
                preWhitespace += " ";
                break;
            case '\t':
                addToken(TAB);
                break;

            // new line / end of line
            case '\n':
                break;

            // rest
            default: {

                if (c == '"') { // string
                    do {
                        c = advance();
                    } while (c != '"' && !isAtEnd());
                    if (isAtEnd()) {
                         // error("Unterminated string."); // TODO: add error for unterminated strings
                         return;
                     }
                    String text = source.substring(start, current);
                    addToken(STRING);

                } else if (c == '/' && (peek() == '/' || peek() == '*')) { // comments
                    if (peek() == '/') {
                        while (!isAtEnd() && peek() != '\n') {
                            advance();
                        }
                        addCommentToToken();
                    } else {
                        do {
                            c = advance();
                        } while (!isAtEnd() && c != '*' && peek() != '/'); // TODO: add error for no end mult_comment
                        advance(); // to include '/' at the end
                        addCommentToToken();
                    }
                } else { // keywords, identifier
                    while (!isWhiteSpace(peek()) && !isSeperator(peek()) && !isAtEnd()) {
                        advance();
                    }
                    String text = source.substring(start, current);
                    addToken(keywords.getOrDefault(text, ID));
                }
            }
        }
    }

    private boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n';
    }

    private boolean isSeperator(char c) {
        return c == ':' || c == ';' || c == '(';
    }

    // helper function for scanToken
    private void addToken(JolieTokenType type) {

        // add postWhitespace for pre token if possible
        if (!tokens.isEmpty() && tokens.get(numberOfTokens - 1).getLine() == this.line){
            tokens.get(numberOfTokens - 1).setPostLexeme(preWhitespace);
        } else if (!tokens.isEmpty() && tokens.get(numberOfTokens - 1).getLine() < this.line){
            String newLines = "\n".repeat((this.line - tokens.get(numberOfTokens - 1).getLine()));
            tokens.get(numberOfTokens - 1).setPostLexeme(newLines + preWhitespace);
        }

        // add token
        String lexeme = this.source.substring(this.start, this.current);
        if (tokens.isEmpty()) {
            String newLines = "\n".repeat(this.line);
            this.tokens.add(new JolieToken(type, (newLines + preWhitespace), lexeme, this.line));
        } else {
            this.tokens.add(new JolieToken(type, preWhitespace, lexeme, this.line));
        }

        preWhitespace = "";
        numberOfTokens++;
    }

    // helper function for scanToken
    private void addCommentToToken() {
        if (!tokens.isEmpty() && tokens.get(numberOfTokens - 1).getLine() == this.line){
            tokens.get(numberOfTokens - 1).setPostLexeme(preWhitespace + (this.source.substring(this.start, this.current)));

        } else if (!tokens.isEmpty() && tokens.get(numberOfTokens - 1).getLine() < this.line){
            String newLines = "\n".repeat((this.line - tokens.get(numberOfTokens - 1).getLine()));
            tokens.get(numberOfTokens - 1).setPostLexeme(newLines + preWhitespace + (this.source.substring(this.start, this.current)));
        }
        addToken(COMMENT); // added so later tokens do not override postLexeme

        preWhitespace = "";
    }

    private char advance() {
        char c = source.charAt(current++);
        if (c == '\n') {
            this.line++;
        }
        return c;
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }

        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    // private void error(String message) {
    //     HL_Jolie.error(line, message, "SCAN_ERROR");
    // }

}
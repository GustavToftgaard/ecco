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
        keywords.put("interface", INTERFACE);
        keywords.put("type", TYPE);
        keywords.put("service", SERVICE);
        keywords.put("exercution", EXECUTION);
        keywords.put("embed", EMBED);
        keywords.put("as", AS);
        keywords.put("imputPort", IMPUTPORT);
        keywords.put("outputPort", OUTPUTPORT);
        keywords.put("main", MAIN);
        keywords.put("requestResponse", REQUEST_RESPONSE);
        keywords.put("oneWay", ONE_WAY);

        // keywords.put("true", TRUE);
        // keywords.put("false", FALSE);
        // keywords.put("AND", AND);
        // keywords.put("OR", OR);
        // keywords.put("var", VAR);
        // keywords.put("print", PRINT);
        // keywords.put("if", IF);
        // keywords.put("else", ELSE);
        // keywords.put("while", WHILE);
        // keywords.put("NOT", NOT);
        // keywords.put("return", RETURN);
        // keywords.put("is", ASSIGN);
        // keywords.put("of_type", TYPE_DEF);
        // keywords.put("equals", EQUALS);
        // keywords.put("not_equals", NOT_EQUALS);
        // keywords.put("greater_than", GREATER);
        // keywords.put("greater_or_equal", GREATER_OR_EQUAL);
        // keywords.put("less_than", LESS);
        // keywords.put("less_or_equal", LESS_OR_EQUAL);
        // keywords.put("func", FUNC);
        // keywords.put("Number", NUMBER_TYPE);
        // keywords.put("String", STRING_TYPE);
        // keywords.put("Bool", BOOL_TYPE);
    }

    // In- and output
    private final String source;
    private final List<JolieToken> tokens = new LinkedList<>();

    // Scanning state
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private int numberTokenInLine = 1;

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

        tokens.add(new JolieToken(EOF, "", null, line, numberTokenInLine));
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
            case ':':
                addToken(COLON);
                break;
            case ';':
                addToken(SEMI_COLON);
                break;

            // white space
            case ' ':
                addToken(SPACE);
                break;
            case '\t':
                addToken(TAB);
                break;
            case '\n':
            case '\r':
                break;

            // comment
            // case '/': {
            //     while (!isAtEnd() && peek() != '\n') {
            //         advance();
            //     }
            //     break;
            // }

            // rest
            default: {

                if (c == '"') { // string
                    do {
                        c = advance();
                    } while (c != '"' && !isAtEnd());
                    String text = source.substring(start, current);
                    addToken(STRING);

                } else { // keywords, identifier
                    while (!isWhiteSpace(peek()) && !isSeperator(peek()) && !isAtEnd()) {
                        advance();
                    }
                    String text = source.substring(start, current);
                    addToken(keywords.getOrDefault(text, ID));
                }

                // } else { // Encountered an unexpected character. Throw error and recover.
                //     error("Unexpected character.");
                //     while (!isWhiteSpace(peek()) && !isAtEnd()) {
                //         advance();
                //     }
                //     break;
                // }
            }
        }
    }

    private boolean isWhiteSpace(char c) {
        return c == ' ' || c == '\t' || c == '\r' || c == '\n';
    }

    private boolean isSeperator(char c) {
        return c == ':' || c == ';';
    }

    // private void string() {
    //     while (peek() != '"' && !isAtEnd() && peek() != '\n') {
    //         advance();
    //     }

    //     if (isAtEnd() || peek() == '\n') {
    //         error("Unterminated string.");
    //         return;
    //     }

    //     // The closing "
    //     advance();

    //     addToken(STRING);
    // }

    // private void number() {
    //     while (isDigit(peek())) {
    //         advance();
    //     }

    //     // Look for a fractional part.
    //     if (match('.')) {

    //         while (isDigit(peek())) {
    //             advance();
    //         }
    //     }

    //     addToken(NUMBER);
    // }

    // helper function for scanToken
    // private boolean isAlpha(char c) {
    //     return Character.isLetter(c);
    // }

    // helper function for scanToken
    // private boolean isAlphaNumerical(char c) {
    //     return (Character.isLetter(c) || Character.isDigit(c));
    // }

    // helper function for scanToken
    // private boolean isDigit(char c) {
    //     return Character.isDigit(c);
    // }

    // helper function for scanToken
    private void addToken(JolieTokenType type) {
        String lexeme = this.source.substring(this.start, this.current);
        Object literal = null;
        this.tokens.add(new JolieToken(type, lexeme, literal, this.line, this.numberTokenInLine));
        this.numberTokenInLine++;
    }


    private char advance() {
        char c = source.charAt(current++);
        if (c == '\n') {
            this.line++;
            this.numberTokenInLine = 0;
        }
        return c;
    }

    // private boolean match(char expected) {
    //     if (isAtEnd()) {
    //         return false;
    //     } else if (source.charAt(current) != expected) {
    //         return false;
    //     }

    //     advance();
    //     return true;
    // }

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
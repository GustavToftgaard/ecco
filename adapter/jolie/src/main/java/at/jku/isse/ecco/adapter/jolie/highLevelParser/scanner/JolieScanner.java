package at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;

public class JolieScanner {
    // Keyword-map
    private static final Map<String, JolieTokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("from", FROM);
        keywords.put("import", IMPORT);
        keywords.put("include", INCLUDE);
        keywords.put("as", AS);
        keywords.put("in", IN);

        keywords.put("interface", INTERFACE);
        keywords.put("extender", EXTENDER);
        keywords.put("type", TYPE);

        keywords.put("requestResponse", REQUEST_RESPONSE);
        keywords.put("RequestResponse", REQUEST_RESPONSE);
        keywords.put("oneWay", ONE_WAY);
        keywords.put("OneWay", ONE_WAY);

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
        keywords.put("aggregates", AGGREGATES);
        keywords.put("Aggregates", AGGREGATES);
        keywords.put("redirects", REDIRECTS);
        keywords.put("Redirects", REDIRECTS);

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
    private String preLexeme = "";

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

        if (tokens.isEmpty()) {
            String newLines = "\n".repeat(line - 1);
            tokens.add(new JolieToken(EOF, newLines, "", line));

        } else if (tokens.get(numberOfTokens - 1).getLine() != line) {
            tokens.add(new JolieToken(EOF, preLexeme, "", line));

        } else {
            tokens.add(new JolieToken(EOF, preLexeme, "", line));
        }

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
            case '|':
                addToken(VERTICAL_BAR);
                break;

            // white space
            case ' ':
                preLexeme += " ";
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
                    addToken(STRING);

                } else if (c == '/' && (peek() == '/' || peek() == '*')) { // comments
                    int startCommentLine = this.line;
                    char commentChar = c;
                    if (peek() == '/') {
                        while (!isAtEnd() && peek() != '\n') {
                            advance();
                        }
                        addCommentToToken(startCommentLine);
                    } else {
                        commentChar = advance(); // move past '*'
                        while (!isAtEnd() && (commentChar != '*' || peek() != '/')){ // TODO: add error for no end mult_comment
                            commentChar = advanceMultiLineComment();
                        }
                        advance(); // to include '/' at the end
                        addCommentToToken(startCommentLine);
                    }

                } else { // keywords, identifier
                    while (!isWhiteSpace(peek()) && !isSeparator(peek()) && !isAtEnd()) {
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

    private boolean isSeparator(char c) {
        return c == ':' || c == ';' ||
                c == '(' || c == ')' ||
                c == '{' || c == '}' ||
                c == '<' || c == '>';
    }

    private void addToken(JolieTokenType type) {
        String lexeme = this.source.substring(this.start, this.current);
        this.tokens.add(new JolieToken(type, preLexeme, lexeme, this.line));
        preLexeme = "";
        numberOfTokens++;
    }

    private void addCommentToToken(int startLine) {
        String lexeme = this.source.substring(this.start, this.current);

        if (tokens.size() > 1 && tokens.get(numberOfTokens - 1).getType() == COMMENT) {
            tokens.get(numberOfTokens - 2).addToPostLexeme(preLexeme + lexeme);
            if (tokens.get(numberOfTokens - 1).getLine() < this.line) {
                tokens.get(numberOfTokens - 1).setLine(line);
            }

        } else if (!tokens.isEmpty() && tokens.get(numberOfTokens - 1).getType() != COMMENT) {
            tokens.get(numberOfTokens - 1).addToPostLexeme(preLexeme + lexeme);
            // added COMMENT token so later tokens add to postLexeme of token before comment token
            this.tokens.add(new JolieToken(COMMENT, "", "", this.line));
            numberOfTokens++;
        }

        if (tokens.isEmpty()) {
            preLexeme +=  lexeme;
            this.tokens.add(new JolieToken(COMMENT, "", "", this.line));
            numberOfTokens++;

        } else if (numberOfTokens == 1 && tokens.get(0).getType() == COMMENT) {
            tokens.get(0).setLine(line);
            preLexeme += lexeme;

        } else {
            preLexeme = "";
        }
    }

    private char advance() {
        char c = source.charAt(current++);
        if (c == '\n') {
            if (tokens.isEmpty() || (numberOfTokens == 1 && tokens.get(0).getType() == COMMENT)) {
                this.preLexeme += '\n';

            } else if (numberOfTokens >= 2 && tokens.get(numberOfTokens - 1).getType() == COMMENT) {
                tokens.get(numberOfTokens - 2).addToPostLexeme(preLexeme + '\n');

            } else {
                tokens.get(numberOfTokens - 1).addToPostLexeme(preLexeme + '\n');
                preLexeme = "";
            }

            this.line++;
        }
        return c;
    }

    private char advanceMultiLineComment() {
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
}
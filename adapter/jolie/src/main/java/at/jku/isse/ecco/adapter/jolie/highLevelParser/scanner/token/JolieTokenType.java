package at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token;

public enum JolieTokenType {
    // Single-character tokens
    // SEMICOLON, COMMA, LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, PLUS, MINUS, MULTIPLY, DIV,

    // Keywords
    // AND, OR, VAR, PRINT, IF, ELSE, WHILE, RETURN, NOT, ASSIGN, TYPE_DEF, EQUALS, NOT_EQUALS, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL, FUNC, NUMBER_TYPE, STRING_TYPE, BOOL_TYPE,

    // Literals
    // NUMBER, STRING, TRUE, FALSE,

    // Identifier
    // IDENTIFIER,

    // End-of-file
    // EOF

    //--------------------------------------------

    // Single-character tokens
    LEFT_BRACE, RIGHT_BRACE, LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET, COLON,

    // Single-character tokens (white space)
    SEMI_COLON, SPACE, TAB,

    // Keywords
    FROM, IMPORT, INTERFACE, TYPE, SERVICE, EXECUTION, EMBED, AS,
    IMPUTPORT, OUTPUTPORT, MAIN, REQUEST_RESPONSE, ONE_WAY,

    // Literals
    STRING,

    // Comments
    COMMENT, MULTILINE_COMMENT,

    // Identifier
    ID,

    // End-of-file
    EOF
}

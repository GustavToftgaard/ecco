package at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token;

public enum JolieTokenType {
    // Single-character tokens
    LEFT_BRACE, RIGHT_BRACE,
    LEFT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET,
    LEFT_PAREN, RIGHT_PAREN,
    LESS_THAN, GREATER_THAN,
    COLON, SEMI_COLON,

    // Single-character tokens (white space)
    SPACE, TAB,

    // Keywords
    FROM, IMPORT, INCLUDE, AS, IN,
    INTERFACE, EXTENDER, TYPE,
    REQUEST_RESPONSE, ONE_WAY,
    SERVICE, EXECUTION, EMBED, EMBEDDED,
    INPUTPORT, OUTPUTPORT,
    LOCATION, PROTOCOL, INTERFACES,
    AGGREGATES, REDIRECTS,
    INIT, MAIN, COURIER,
    DEFINE,

    // Literals
    STRING,

    // Comments
    COMMENT, MULTILINE_COMMENT,

    // Identifier
    ID,

    // End-of-file
    EOF
}

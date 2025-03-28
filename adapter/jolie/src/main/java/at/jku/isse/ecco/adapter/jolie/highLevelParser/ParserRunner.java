package at.jku.isse.ecco.adapter.jolie.highLevelParser;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.JolieScanner;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.parser.Parser;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;


public class ParserRunner {
    public static boolean hadError = false;
    public static List<String> errors = new ArrayList<>();

    public List<Node> parse(String source) { // List<String>
        hadError = false;
        errors.clear();
        JolieScanner scanner = new JolieScanner(source);
        List<JolieToken> tokens = scanner.scanTokens();
        // if (hadError) return errors;

        List<JolieToken> parserInput = new ArrayList<>();
        for (JolieToken token : tokens) {
            if (token.type != SPACE && token.type != COMMENT && token.type != MULTILINE_COMMENT) {
                parserInput.add(token);
            }
        }

        Parser parser = new Parser(parserInput);
        List<Node> statements = parser.parse();
        // if (hadError) return errors;

        return statements;
    }

//    public static void error(JolieToken token, String message, String errorType) {
//        report(token.line, message, errorType);
//    }
//
//    public static void error(int line, String message, String errorType) {
//        report(line, message, errorType);
//    }
//
//    private static void report(int line, String message, String errorType) {
//        var error = errorType + ", line " + line + " " + message;
//        errors.add(error);
//        System.err.println(error);
//        hadError = true;
//    }

}



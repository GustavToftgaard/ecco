package at.jku.isse.ecco.adapter.jolie.highLevelParser;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.JolieScanner;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.parser.Parser;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;


public class ParserRunner {
    public static boolean hadError = false;
    public static List<String> errors = new ArrayList<>();

    public List<Node> parse(Path source) { // List<String>
        hadError = false;
        errors.clear();

        List<String> lineList = null;
        char lastChar = ' ';
        try {
            lineList = Files.readAllLines(source);
            char[] charArray = new String(Files.readAllBytes(source)).toCharArray();
            if (charArray.length > 0) {
                lastChar = charArray[charArray.length - 1];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] lines = lineList.toArray(new String[0]);
        StringBuilder scannerInput = new StringBuilder();

//        for (String line : lines) {
//            scannerInput.append(line).append('\n');
//        }

        for (int i = 0; i < lines.length; i++) {
            if (i == lines.length - 1 && !Objects.equals(lines[i], "") && (lastChar != '\n' && lastChar != '\r')) {
                scannerInput.append(lines[i]);
            } else {
                scannerInput.append(lines[i]).append('\n');
            }
        }

        JolieScanner scanner = new JolieScanner(scannerInput.toString());
        List<JolieToken> tokens = scanner.scanTokens();
        // if (hadError) return errors;

        List<JolieToken> parserInput = new ArrayList<>();
        for (JolieToken token : tokens) {
            if (token.getType() != SPACE && token.getType() != COMMENT && token.getType() != MULTILINE_COMMENT) {
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



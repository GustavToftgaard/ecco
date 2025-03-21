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


public class tempParserRunner {
    public static boolean hadError = false;
    public static List<String> errors = new ArrayList<>();

    // Expects files that comprise a Jolie program as arguments
    public static void main(String[] args) throws IOException {
        for (String s : args)
            new tempParserRunner().interpretFile(s);
    }

    private void interpretFile(String path) throws IOException {
        System.out.println(" ------------ Processing file " + path + " ------------ \n");

        execute(new String(Files.readAllBytes(Paths.get(path))));

    }

    @SuppressWarnings("unused")
    private void run(String source) {
        JolieScanner scanner = new JolieScanner(source);
        List<JolieToken> tokens = scanner.scanTokens();

        List<JolieToken> parserInput = new ArrayList<>();
        for (JolieToken token : tokens) {
            if (token.type != SPACE && token.type != COMMENT && token.type != MULTILINE_COMMENT) {
                parserInput.add(token);
            }
        }

         Parser parser = new Parser(parserInput);
         parser.parse();
    }

    public void saveToFile(StringBuilder stringContent, String target) {
        try {
            Path targetPath = Path.of(target);
            Files.deleteIfExists(targetPath);
            if (Files.notExists(targetPath.getParent()))
                Files.createDirectory(targetPath.getParent());
            Files.createFile(targetPath);
            Files.writeString(targetPath, stringContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Node> execute(String source) { // List<String>
        hadError = false;
        errors.clear();
        JolieScanner scanner = new JolieScanner(source);
        List<JolieToken> tokens = scanner.scanTokens();
        // if (hadError) return errors;

        // System.out.println(tokens);

        List<JolieToken> parserInput = new ArrayList<>();
        for (JolieToken token : tokens) {
            if (token.type != SPACE && token.type != COMMENT && token.type != MULTILINE_COMMENT) {
                parserInput.add(token);
//                System.out.println(token);
            }
        }
//        System.out.println(" ");

         Parser parser = new Parser(parserInput);
         List<Node> statements = parser.parse();
        // if (hadError) return errors;

        return statements;
    }

    public static void error(JolieToken token, String message, String errorType) {
        report(token.line, message, errorType);
    }

    public static void error(int line, String message, String errorType) {
        report(line, message, errorType);
    }

    private static void report(int line, String message, String errorType) {
        var error = errorType + ", line " + line + " " + message;
        errors.add(error);
        System.err.println(error);
        hadError = true;
    }

}


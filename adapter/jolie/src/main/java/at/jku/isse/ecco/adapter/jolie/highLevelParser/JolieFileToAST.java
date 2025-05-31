package at.jku.isse.ecco.adapter.jolie.highLevelParser;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.parser.JolieParser;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.JolieScanner;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

/**
 * The {@code JolieFileToAST} class provides functionality for converting Jolie source code
 * into an AST.
 *
 * <p>
 * Example usage:
 * <pre>{@code
 * JolieFileToAST scannerParser = new JolieFileToAST();
 * Path jolieFile = Paths.get("example.ol");
 * List<Node> ast = scannerParser.scanAndParse(jolieFile);
 * }</pre>
 * </p>
 *
 * @see at.jku.isse.ecco.adapter.jolie.JolieReader
 *
 * @author Gustav Toftgaard <gustav@familientoftgaard.dk>
 */
public class JolieFileToAST {
    public static boolean hadError = false;
    public static List<String> errors = new ArrayList<>();

    public List<Node> scanAndParse(Path source) { // List<String>
        hadError = false;
        errors.clear();

        // extract lines for file
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

        for (int i = 0; i < lines.length; i++) {
            // check if last line or not
            if (i == lines.length - 1 && !Objects.equals(lines[i], "") && (lastChar != '\n' && lastChar != '\r')) {
                scannerInput.append(lines[i]);
            } else {
                scannerInput.append(lines[i]).append('\n');
            }
        }

        JolieScanner scanner = new JolieScanner(scannerInput.toString());
        List<JolieToken> tokens = scanner.scanTokens();

        // remove COMMENT tokens before parsing
        List<JolieToken> parserInput = new ArrayList<>();
        for (JolieToken token : tokens) {
            if (token.getType() != COMMENT) {
                parserInput.add(token);
            }
        }

        JolieParser jolieParser = new JolieParser(parserInput);
        List<Node> statements = jolieParser.parse();

        return statements;
    }
}



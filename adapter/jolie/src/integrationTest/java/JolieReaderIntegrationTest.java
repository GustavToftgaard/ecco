import at.jku.isse.ecco.adapter.jolie.JolieReader;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.storage.mem.dao.MemEntityFactory;
import at.jku.isse.ecco.tree.Node;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JolieReaderIntegrationTest {

    @Test
    public void readFileTest() throws URISyntaxException {
        String relativeResourceFolderPath = "Jolie_Test_Code/simple_file";
        URI resourceFolderUri = Objects.requireNonNull(getClass().getClassLoader().getResource(relativeResourceFolderPath)).toURI();
        String resourceFolderPathString = Paths.get(resourceFolderUri).toString();
        Path resourceFolderPath = Paths.get(resourceFolderPathString);
        Set<Node.Op> nodes = readFolder(resourceFolderPath);

        assertEquals(1, nodes.size());
        Node.Op resultPluginNode = nodes.iterator().next();

//        printECCO(resultPluginNode);

        testSimpleFile(resultPluginNode);
    }

//    public void printECCO(Node.Op rootNode) {
//        System.out.println(rootNode.toString());
//        for (int i = 0; i < rootNode.getChildren().size(); i++) {
//            printECCO(rootNode.getChildren().get(i));
//        }
//    }

//    @Test
//    public void readMultipleFiles() throws URISyntaxException {
//        String relativeResourceFolderPath = "C_SPL/multiple_files";
//        URI resourceFolderUri = Objects.requireNonNull(getClass().getClassLoader().getResource(relativeResourceFolderPath)).toURI();
//        String resourceFolderPathString = Paths.get(resourceFolderUri).toString();
//        Path resourceFolderPath = Paths.get(resourceFolderPathString);
//
//        Set<Node.Op> nodes = readFolder(resourceFolderPath);
//        nodes.forEach(this::testSimpleFile);
//    }

    private Set<Node.Op> readFolder(Path folderPath) {
        JolieReader reader = new JolieReader(new MemEntityFactory());
        Path[] relativeFiles = this.getRelativeDirContent(reader, folderPath);
        return reader.read(folderPath, relativeFiles);
    }

    private Path[] getRelativeDirContent(JolieReader reader, Path dir){
        Map<Integer, String[]> prioritizedPatterns = reader.getPrioritizedPatterns();
        String[] patterns = prioritizedPatterns.values().iterator().next();
        Collection<PathMatcher> pathMatcher = new ArrayList<>();
        for (String pattern : patterns) {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
            pathMatcher.add(matcher);
        }

        Set<Path> fileSet = new HashSet<>();
        try (Stream<Path> pathStream = Files.walk(dir)) {
            pathStream.forEach( path -> {
                Boolean applicableFile = pathMatcher.stream().map(pm -> pm.matches(path)).reduce(Boolean::logicalOr).get();
                if (applicableFile) {
                    fileSet.add(path);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileSet.stream().map(dir::relativize).toArray(Path[]::new);
    }

    private void testSimpleFile(Node.Op pluginNode){
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(5, pluginNodeChildren.size());

        Node.Op node = null;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Import
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 1.1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "console", 1, 0);

        // 1.1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Console", 1, 0);

        // ------

        // 2: ImportDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 2.1: Import
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 2.1.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "string_utils", 2, 0);

        // 2.1.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 2, 0);

        // ------

        // 3: TypeDecl
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.TYPEDECL, 2);

        // 3.1: ID
        node = pluginNodeChildren.get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 4, 0);

        // 3.2: Block
        node = pluginNodeChildren.get(2).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 6);

        // 3.2.*:
        checkTokenNode(node.getChildren().get(0), JolieTokenType.ID, "m", 5, 0);
        checkTokenNode(node.getChildren().get(1), JolieTokenType.COLON, ":", 5, 0);
        checkTokenNode(node.getChildren().get(2), JolieTokenType.ID, "int", 5, 0);
        checkTokenNode(node.getChildren().get(3), JolieTokenType.ID, "n", 6, 0);
        checkTokenNode(node.getChildren().get(4), JolieTokenType.COLON, ":", 6, 0);
        checkTokenNode(node.getChildren().get(5), JolieTokenType.ID, "int", 6, 0);

        // ------

        // 4: InterfaceDecl
        node = pluginNodeChildren.get(3);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 4.1: ID
        node = pluginNodeChildren.get(3).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "NumbersAPI", 9, 0);

        // 4.2: Block
        node = pluginNodeChildren.get(3).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 17);

        // 4.2.*:
        checkTokenNode(node.getChildren().get(0), JolieTokenType.ID, "requestResponse", 10, 0);
        checkTokenNode(node.getChildren().get(1), JolieTokenType.COLON, ":", 10, 0);
        checkTokenNode(node.getChildren().get(2), JolieTokenType.ID, "sumUpTo", 11, 0);
        checkTokenNode(node.getChildren().get(3), JolieTokenType.LEFT_PAREN, "(", 11, 0);
        checkTokenNode(node.getChildren().get(4), JolieTokenType.ID, "int", 11, 0);
        checkTokenNode(node.getChildren().get(5), JolieTokenType.RIGHT_PAREN, ")", 11, 0);
        checkTokenNode(node.getChildren().get(6), JolieTokenType.LEFT_PAREN, "(", 11, 0);
        checkTokenNode(node.getChildren().get(7), JolieTokenType.ID, "int", 11, 0);
        checkTokenNode(node.getChildren().get(8), JolieTokenType.RIGHT_PAREN, ")", 11, 0);
        checkTokenNode(node.getChildren().get(9), JolieTokenType.ID, ",", 11, 0);
        checkTokenNode(node.getChildren().get(10), JolieTokenType.ID, "sumBetween", 12, 0);
        checkTokenNode(node.getChildren().get(11), JolieTokenType.LEFT_PAREN, "(", 12, 0);
        checkTokenNode(node.getChildren().get(12), JolieTokenType.ID, "twoArguments", 12, 0);
        checkTokenNode(node.getChildren().get(13), JolieTokenType.RIGHT_PAREN, ")", 12, 0);
        checkTokenNode(node.getChildren().get(14), JolieTokenType.LEFT_PAREN, "(", 12, 0);
        checkTokenNode(node.getChildren().get(15), JolieTokenType.ID, "int", 12, 0);
        checkTokenNode(node.getChildren().get(16), JolieTokenType.RIGHT_PAREN, ")", 12, 0);

        // ------

        // 5: ServiceDecl
        node = pluginNodeChildren.get(4);
        checkContextNode(node, NodeTypes.SERVICEDECL, 4);

        // 5.1: ID
        node = pluginNodeChildren.get(4).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Numbers", 15, 0);

        // 5.2: Execution
        node = pluginNodeChildren.get(4).getChildren().get(1);
        checkContextNode(node, NodeTypes.EXECUTION, 2);

        // 5.2.1: COLON
        node = pluginNodeChildren.get(4).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.COLON, ":", 16, 0);

        // 5.2.2: ID
        node = pluginNodeChildren.get(4).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "sequential", 16, 0);

        // 5.3:InputPort
        node = pluginNodeChildren.get(4).getChildren().get(2);
        checkContextNode(node, NodeTypes.INPUTPORT, 3);

        // 5.3.1: ID
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "numbersInput", 18, 0);

        // 5.3.2: PortLocation
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1);
        checkContextNode(node, NodeTypes.PORTLOCATION, 2);

        // 5.3.2.1: Location
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.LOCATION, "location", 19, 0);

        // 5.3.2.2: Line
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.LINE, 1);

        // 5.3.2.2.1: String
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.STRING, "\"local\"", 19, 0);

        // 5.3.3: PortInterfaces
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(2);
        checkContextNode(node, NodeTypes.PORTINTERFACES, 2);

        // 5.3.3.1: Interfaces
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.INTERFACES, "interfaces", 20, 0);

        // 5.3.3.2: Line
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(2).getChildren().get(1);
        checkContextNode(node, NodeTypes.LINE, 1);

        // 5.3.3.2.1: ID
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(2).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "NumbersAPI", 20, 0);

        // 5.4: Main
        node = pluginNodeChildren.get(4).getChildren().get(3);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 5.4.1: Block
        node = pluginNodeChildren.get(4).getChildren().get(3).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 24);

        // 5.4.1.*:
        checkTokenNode(node.getChildren().get(0), JolieTokenType.LEFT_SQUARE_BRACKET, "[", 25, 0);
        checkTokenNode(node.getChildren().get(1), JolieTokenType.ID, "sumUpTo", 25, 0);
        checkTokenNode(node.getChildren().get(2), JolieTokenType.LEFT_PAREN, "(", 25, 0);
        checkTokenNode(node.getChildren().get(3), JolieTokenType.ID, "n", 25, 0);
        checkTokenNode(node.getChildren().get(4), JolieTokenType.RIGHT_PAREN, ")", 25, 0);
        checkTokenNode(node.getChildren().get(5), JolieTokenType.LEFT_PAREN, "(", 25, 0);
        checkTokenNode(node.getChildren().get(6), JolieTokenType.ID, "response", 25, 0);
        checkTokenNode(node.getChildren().get(7), JolieTokenType.RIGHT_PAREN, ")", 25, 0);
        checkTokenNode(node.getChildren().get(8), JolieTokenType.LEFT_BRACE, "{", 25, 0);
        checkTokenNode(node.getChildren().get(9), JolieTokenType.ID, "...", 26, 0);
        checkTokenNode(node.getChildren().get(10), JolieTokenType.RIGHT_BRACE, "}", 27, 0);
        checkTokenNode(node.getChildren().get(11), JolieTokenType.RIGHT_SQUARE_BRACKET, "]", 27, 0);
        checkTokenNode(node.getChildren().get(12), JolieTokenType.LEFT_SQUARE_BRACKET, "[", 29, 0);
        checkTokenNode(node.getChildren().get(13), JolieTokenType.ID, "sumBetween", 29, 0);
        checkTokenNode(node.getChildren().get(14), JolieTokenType.LEFT_PAREN, "(", 29, 0);
        checkTokenNode(node.getChildren().get(15), JolieTokenType.ID, "request", 29, 0);
        checkTokenNode(node.getChildren().get(16), JolieTokenType.RIGHT_PAREN, ")", 29, 0);
        checkTokenNode(node.getChildren().get(17), JolieTokenType.LEFT_PAREN, "(", 29, 0);
        checkTokenNode(node.getChildren().get(18), JolieTokenType.ID, "response", 29, 0);
        checkTokenNode(node.getChildren().get(19), JolieTokenType.RIGHT_PAREN, ")", 29, 0);
        checkTokenNode(node.getChildren().get(20), JolieTokenType.LEFT_BRACE, "{", 29, 0);
        checkTokenNode(node.getChildren().get(21), JolieTokenType.ID, "...", 30, 0);
        checkTokenNode(node.getChildren().get(22), JolieTokenType.RIGHT_BRACE, "}", 31, 0);
        checkTokenNode(node.getChildren().get(23), JolieTokenType.RIGHT_SQUARE_BRACKET, "]", 31, 0);
    }

    private void checkContextNode(Node.Op node, NodeTypes nodeType, int numberOfChildren) {
        assertInstanceOf(JolieContextArtifactData.class, node.getArtifact().getData());
        JolieContextArtifactData nodeContext = (JolieContextArtifactData) node.getArtifact().getData();
        assertSame(nodeType, nodeContext.getType());
        assertEquals(numberOfChildren, node.getChildren().size());
    }

    private void checkTokenNode(Node.Op node, JolieTokenType tokenType, String lexeme, int lineNumber, int numberOfChildren) {
        assertInstanceOf(JolieTokenArtifactData.class, node.getArtifact().getData());
        JolieTokenArtifactData nodeContext = (JolieTokenArtifactData) node.getArtifact().getData();
        assertSame(tokenType, nodeContext.getType());
        assertEquals(lexeme, nodeContext.getLexeme());
        assertEquals(lineNumber, nodeContext.getLine());
        assertEquals(numberOfChildren, node.getChildren().size());
    }
}

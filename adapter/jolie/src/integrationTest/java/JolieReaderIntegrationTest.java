import at.jku.isse.ecco.adapter.jolie.JolieReader;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieLineArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.storage.mem.dao.MemEntityFactory;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.simpleFiles.JRIT_SimpleFiles;
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
        String relativeResourceFolderPath = "jolieTestCode/simple_file";
        URI resourceFolderUri = Objects.requireNonNull(getClass().getClassLoader().getResource(relativeResourceFolderPath)).toURI();
        String resourceFolderPathString = Paths.get(resourceFolderUri).toString();
        Path resourceFolderPath = Paths.get(resourceFolderPathString);
        Set<Node.Op> nodes = readFolder(resourceFolderPath);

        assertEquals(1, nodes.size());
        Node.Op resultPluginNode = nodes.iterator().next();

//        printECCO(resultPluginNode);

        JRIT_SimpleFiles tester = new JRIT_SimpleFiles();
        tester.test(resultPluginNode, "simpleFile1.ol");
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
}

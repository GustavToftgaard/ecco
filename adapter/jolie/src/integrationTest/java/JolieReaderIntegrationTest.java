import at.jku.isse.ecco.adapter.jolie.JolieReader;
import at.jku.isse.ecco.storage.mem.dao.MemEntityFactory;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.TestCases.simpleFiles.JRIT_SimpleFiles;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JolieReaderIntegrationTest {
    private final Map<String, String> filesAndPaths = createHashMapOfTestFiles();

    @Test
    public void readSimpleFilesTest() {
        JRIT_SimpleFiles tester = new JRIT_SimpleFiles();

        for (String fileName : tester.getFileNames()) {
            Set<Node.Op> nodes = readFolder(Paths.get(filesAndPaths.get(fileName)));
            assertEquals(1, nodes.size());
            Node.Op resultPluginNode = nodes.iterator().next();
            tester.test(resultPluginNode, fileName);
        }
    }

//    public void printECCO(Node.Op rootNode) {
//        System.out.println(rootNode.toString());
//        for (int i = 0; i < rootNode.getChildren().size(); i++) {
//            printECCO(rootNode.getChildren().get(i));
//        }
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

    private List<Path> listAllFilesInDir(Path path) throws IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;
    }

    private Map<String, String> createHashMapOfTestFiles() {
        String totalPath = Objects.requireNonNull(getClass().getClassLoader().getResource("jolieTestCode")).toString();
        totalPath = totalPath.substring(6); // remove "file:/" from start of string
        totalPath = totalPath.replace("%20", " "); // in getting totalPath all " " are replaced with "%20" so this reverses this

        Map<String, String> res = new HashMap<>();
        try {
            Path path = Paths.get(totalPath);
            List<Path> paths = listAllFilesInDir(path);

            for(Path filePath : paths) {
                String file = filePath.toString();
                for (int i = file.length() - 1; i > 0 ; i--) {
                    if (file.charAt(i) == '\\') {
                        res.put(file.substring(i + 1), file.substring(0, i));
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}

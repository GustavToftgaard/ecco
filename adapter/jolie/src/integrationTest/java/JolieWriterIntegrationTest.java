import at.jku.isse.ecco.adapter.jolie.JolieReader;
import at.jku.isse.ecco.adapter.jolie.JolieWriter;
import at.jku.isse.ecco.storage.mem.dao.MemEntityFactory;
import at.jku.isse.ecco.tree.Node;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JolieWriterIntegrationTest {
    private final static String autoTestFolder = "src/integrationTest/resources/jolieTestCode/autoTestFolder";
    private final static String fileTestFolder = "src/integrationTest/resources/jolieTestCode";

    @BeforeAll
    public static void testPreparation(){
        createTestFolder();
    }

    @BeforeEach
    public void singleTestPreparation(){
        createTestFolder();
    }

    @AfterAll
    public static void testCleanUp(){
        deleteAllTestFiles();
    }

    @AfterEach
    public void singleTestCleanUp(){
        deleteAllTestFiles();
    }

    private static void createTestFolder() {
        String fileName = "placeHolder.txt";

        File directory = new File(autoTestFolder);
        if (! directory.exists()){
            assertTrue(directory.mkdir());
        }

        createFile(fileName, "Hello There!");
    }

    private static void createFile(String fileName, String fileInput) {
        File file = new File(autoTestFolder + "/" + fileName);
        try{
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileInput);
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private static void deleteAllTestFiles() {
        assertTrue(deleteDirectory(Paths.get(autoTestFolder).toFile()));
    }

    private static boolean deleteDirectory(File directoryToBeDeleted) {
        if (!directoryToBeDeleted.exists()) {
            return true;
        }

        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

//    private static Path getResourceTestFolderPath(){
//        URI resourceFolderUri = null;
//        try {
//            var temp = JolieWriterIntegrationTest.class.getClassLoader().getResource(testFolder);
//            resourceFolderUri = Objects.requireNonNull(temp).toURI();
//            String resourceFolderPathString = Paths.get(resourceFolderUri).toString();
//            Path resourceFolderPath = Paths.get(resourceFolderPathString);
//            assertNotNull(resourceFolderPath);
//            return resourceFolderPath;
//        } catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }

//    private static Path getResourceTestFolderSubpath(String relativePath){
//        Path testFolderPath = getResourceTestFolderPath();
//        return testFolderPath.resolve(relativePath);
//    }

    // copied from CWriterIntegrationTest
    private static String[] fileToStringArray(Path filePath){
//        List<String> lineList = new LinkedList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                lineList.add(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String[] lineArray = new String[lineList.size()];
//        lineList.toArray(lineArray);

        List<String> lineList = null;
        try {
            lineList = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lineList.toArray(new String[0]);
    }

    @Test
    public void writeSingleFile() {
        MemEntityFactory factory = new MemEntityFactory();
        Path testFilePath = Paths.get(autoTestFolder + "/jolie_test_1.ol");

        // create ecco tree with some line nodes
        String testCodeFile = "jolieTestCode/simple_file";
        URI resourceFolderUri = null;

        try {
            resourceFolderUri = Objects.requireNonNull(getClass().getClassLoader().getResource(testCodeFile)).toURI();
            String resourceFolderPathString = Paths.get(resourceFolderUri).toString();
            Path resourceFolderPath = Paths.get(resourceFolderPathString);
            Set<Node.Op> nodes = readFolder(resourceFolderPath);

            assertEquals(1, nodes.size());
            Node.Op resultPluginNode = nodes.iterator().next();

            // write tree
            JolieWriter writer = new JolieWriter();
            Set<Node> nodeSet = new HashSet<>();
            nodeSet.add(resultPluginNode);
            writer.write(Paths.get(autoTestFolder), nodeSet);

            // check new file exists
            assertTrue(Files.exists(testFilePath));

            // check lines in new file
            String[] writtenContent = fileToStringArray(testFilePath);
            String[] originalContent = fileToStringArray(testFilePath);

            for (int i = 0; i < originalContent.length; i++) {
                assertEquals(originalContent[i], writtenContent[i]);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

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

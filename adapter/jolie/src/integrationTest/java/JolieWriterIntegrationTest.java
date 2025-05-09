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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JolieWriterIntegrationTest {
    private final static String autoTestFolder = "src/integrationTest/resources/autoTestFolder";
    private final static String fileTestFolder = "src/integrationTest/resources/jolieTestCode";
    private final static String testFolder = "src/integrationTest/resources";
    private final Map<String, String> filesAndPaths = createHashMapOfTestFiles();

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

    private static String[] fileToStringArray(Path filePath){
        List<String> lineList = null;
        try {
            lineList = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lineList.toArray(new String[0]);
    }

    // ----

    // ImportDecl - Import
    @Test
    public void readImportTest() {
        writeTestCase("importTest1.ol");
        writeTestCase("importTest2.ol");
    }

    // ImportDecl - Include
    @Test
    public void readIncludeTest() {
        writeTestCase("includeTest1.ol");
    }

    // InterfaceDecl
    @Test
    public void readInterfaceDeclTest() {
        writeTestCase("interfaceDeclTest1.ol");
        writeTestCase("interfaceDeclTest2.ol");
        writeTestCase("interfaceDeclTest3.ol");
        writeTestCase("interfaceDeclTest4.ol");
        writeTestCase("interfaceDeclTest5.ol");
        writeTestCase("interfaceDeclTest6.ol");

    }

    // InterfaceDecl - RequestResponseDecl
    @Test
    public void readRequestResponseDeclTest() {
        writeTestCase("requestResponseDeclTest1.ol");
        writeTestCase("requestResponseDeclTest2.ol");
        writeTestCase("requestResponseDeclTest3.ol");
        writeTestCase("requestResponseDeclTest4.ol");
    }

    // InterfaceDecl - OneWayDecl
    @Test
    public void readOneWayDeclTest() {
        writeTestCase("oneWayDeclTest1.ol");
        writeTestCase("oneWayDeclTest2.ol");
        writeTestCase("oneWayDeclTest3.ol");
        writeTestCase("oneWayDeclTest4.ol");
    }

    // TypeDecl
    @Test
    public void readTypeDeclTest() {
        writeTestCase("typeDeclTest1.ol");
        writeTestCase("typeDeclTest2.ol");
        writeTestCase("typeDeclTest3.ol");
        writeTestCase("typeDeclTest4.ol");
    }

    // ServiceDecl
    @Test
    public void readServiceDeclTest() {
        writeTestCase("serviceDeclTest1.ol");
        writeTestCase("serviceDeclTest2.ol");
        writeTestCase("serviceDeclTest3.ol");
    }

    // ServiceDecl - Execution
    @Test
    public void readExecutionTest() {
        writeTestCase("executionTest1.ol");
        writeTestCase("executionTest2.ol");
    }

    // ServiceDecl - Embed
    @Test
    public void readEmbedTest() {
        writeTestCase("embedTest1.ol");
        writeTestCase("embedTest2.ol");
        writeTestCase("embedTest3.ol");
        writeTestCase("embedTest4.ol");
    }

    // ServiceDecl - Embedded
    @Test
    public void readEmbeddedTest() {
        writeTestCase("embeddedTest1.ol");
    }

    // ServiceDecl - InputPort
    @Test
    public void readInputPortTest() {
        writeTestCase("inputPortTest1.ol");
        writeTestCase("inputPortTest2.ol");
        writeTestCase("inputPortTest3.ol");
        writeTestCase("inputPortTest4.ol");
        writeTestCase("inputPortTest5.ol");
        writeTestCase("inputPortTest6.ol");
    }

    // ServiceDecl - OutputPort
    @Test
    public void readOutputPortTest() {
        writeTestCase("outputPortTest1.ol");
        writeTestCase("outputPortTest2.ol");
        writeTestCase("outputPortTest3.ol");
        writeTestCase("outputPortTest4.ol");
    }

    // ServiceDecl - Init
    @Test
    public void readInitTest() {
        writeTestCase("initTest1.ol");
    }

    // ServiceDecl - DefineProcedure
    @Test
    public void readDefineProcedureTest() {
        writeTestCase("defineProcedureTest1.ol");
    }

    // ServiceDecl - Main
    @Test
    public void readMainTest() {
        writeTestCase("mainTest1.ol");
    }

    // ServiceDecl - Courier
    @Test
    public void readCourierTest() {
        writeTestCase("courierTest1.ol");
    }

    // ServiceDecl - PortParameters - PortLocation
    @Test
    public void readPortLocationTest() {
        writeTestCase("portLocationTest1.ol");
        writeTestCase("portLocationTest2.ol");
    }

    // ServiceDecl - PortParameters - PortProtocol
    @Test
    public void readPortProtocolTest() {
        writeTestCase("portProtocolTest1.ol");
        writeTestCase("portProtocolTest2.ol");
        writeTestCase("portProtocolTest3.ol");
        writeTestCase("portProtocolTest4.ol");
    }

    // ServiceDecl - PortParameters - PortInterfaces
    @Test
    public void readPortInterfacesTest() {
        writeTestCase("portInterfacesTest1.ol");
        writeTestCase("portInterfacesTest2.ol");
        writeTestCase("portInterfacesTest3.ol");
        writeTestCase("portInterfacesTest4.ol");
    }

    // ServiceDecl - PortParameters - PortAggregates
    @Test
    public void readPortAggregatesTest() {
        writeTestCase("portAggregatesTest1.ol");
        writeTestCase("portAggregatesTest2.ol");
        writeTestCase("portAggregatesTest3.ol");
        writeTestCase("portAggregatesTest4.ol");
    }

    // ServiceDecl - PortParameters - PortRedirects
    @Test
    public void readPortRedirectsTest() {
        writeTestCase("portRedirectsTest1.ol");
        writeTestCase("portRedirectsTest2.ol");
        writeTestCase("portRedirectsTest3.ol");
        writeTestCase("portRedirectsTest4.ol");
    }

    // Misc - Block
    @Test
    public void readBlockTest() {
        writeTestCase("blockTest1.ol");
        writeTestCase("blockTest2.ol");
    }

    // Misc - Line
    @Test
    public void readLineTest() {
        writeTestCase("lineTest1.ol");
        writeTestCase("lineTest2.ol");
    }

    // Misc - Comments
    @Test
    public void readCommentsTest() {
        writeTestCase("commentsTest1.ol");
        writeTestCase("commentsTest2.ol");
        writeTestCase("commentsTest3.ol");
        writeTestCase("commentsTest4.ol");
        writeTestCase("commentsTest5.ol");
        writeTestCase("commentsTest6.ol");
        writeTestCase("commentsTest7.ol");
        writeTestCase("commentsTest8.ol");
        writeTestCase("commentsTest9.ol");
    }

    // Misc - EOF
    @Test
    public void readEOFTest() {
        writeTestCase("EOFTest1.ol");
        writeTestCase("EOFTest2.ol");
        writeTestCase("EOFTest3.ol");
        writeTestCase("EOFTest4.ol");
        writeTestCase("EOFTest5.ol");
    }

    // Simple file
    @Test
    public void writeSimpleFile() {
        writeTestCase("simpleFile1.ol");
    }

    // Multiple files

    // ----

    public void writeTestCase(String fileName) {
        writeTestCase(fileName, false);
    }

    public void writeTestCase(String fileName, boolean printFile) {
        MemEntityFactory factory = new MemEntityFactory();
        Path testFilePath = Paths.get(autoTestFolder + "/" + fileName);

        // create ecco tree with some line nodes
        URI resourceFolderUri = null;

        try {
            resourceFolderUri = Objects.requireNonNull(getClass().getClassLoader().getResource(filesAndPaths.get(fileName))).toURI();
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
            String[] originalContent = fileToStringArray(Paths.get(testFolder + "/" + filesAndPaths.get(fileName) + "/" + fileName));

            if (printFile) {
                System.out.println("\n" + fileName + ":\n--- START ---");
                for (int i = 0; i < originalContent.length; i++) {
                    System.out.println(writtenContent[i]);
                }
                System.out.println("---  END  ---\n");
            } else {
                assertEquals(originalContent.length, writtenContent.length);
                for (int i = 0; i < originalContent.length; i++) {
                    assertEquals(originalContent[i], writtenContent[i]);
                }
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

    private Map<String, String> createHashMapOfTestFiles() {
        String totalPath = System.getProperty("user.dir") + "/build/resources/integrationTest/jolieTestCode";
        Map<String, String> res = new HashMap<>();
        List<String> paths = extractFilesFromPath(totalPath);

        for (String file : paths) {
            int index = file.lastIndexOf(File.separator);
            int startIndex = file.indexOf("jolieTestCode");
            res.put(file.substring(index + 1), file.substring(startIndex, index) );
        }
        return res;
    }

//    private Map<String, String> createHashMapOfTestFiles() {
//        String totalPath = Objects.requireNonNull(getClass().getClassLoader().getResource("jolieTestCode")).toString();
//        totalPath = totalPath.substring(6); // remove "file:/" from start of string
//        totalPath = totalPath.replace("%20", " "); // in getting totalPath all " " are replaced with "%20" so this reverses this
//
//        Map<String, String> res = new HashMap<>();
//        try {
//            Path path = Paths.get(totalPath);
//            List<Path> paths = listAllFilesInDir(path);
//
//            for(Path filePath : paths) {
//                String filePathString = filePath.toString();
//                StringBuilder folderName = new StringBuilder();
//
//                for (int i = filePathString.length() - 1; i > 0 ; i--) { // get relative path after "JolieTestCode"
//                    folderName.insert(0, filePathString.charAt(i));
//                    if (filePathString.charAt(i) == '\\') {
//                        if (folderName.toString().equals("\\jolieTestCode")) {
//                            filePathString = filePathString.substring(i + 1);
//                            break;
//                        } else {
//                            folderName = new StringBuilder();
//                        }
//                    }
//                }
//
//                for (int i = filePathString.length() - 1; i > 0 ; i--) {
//                    if (filePathString.charAt(i) == '\\') {
//                        res.put(filePathString.substring(i + 1), filePathString.substring(0, i));
//                        break;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }

//    private List<Path> listAllFilesInDir(Path path) throws IOException {
//        List<Path> result;
//        try (Stream<Path> walk = Files.walk(path)) {
//            result = walk.filter(Files::isRegularFile)
//                    .collect(Collectors.toList());
//        }
//        return result;
//    }

    /**
     * @author sandragreiner <greiner@imada.sdu.dk>
     */
    public List<String> extractFilesFromPath(String path) {
        List<String> filesToScan = new LinkedList<String>();
        // get all files in directory
        FileIterator<Path> fiMV = new FileIterator<Path>();
        try {
            Files.walkFileTree(Paths.get(path), fiMV);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // store the files
        for (Path srcFile : fiMV.getRegFiles()) {
            filesToScan.add(srcFile.toString());
        }
        return filesToScan;
    }
}

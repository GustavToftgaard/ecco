import at.jku.isse.ecco.adapter.jolie.JolieReader;
import at.jku.isse.ecco.storage.mem.dao.MemEntityFactory;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.TestCases.ImportDecl.Import.JRIT_Import;
import jolieReaderIntergrationTests.TestCases.ImportDecl.Include.JRIT_Include;
import jolieReaderIntergrationTests.TestCases.InterfaceDecl.JRIT_InterfaceDecl;
import jolieReaderIntergrationTests.TestCases.InterfaceDecl.OneWayDecl.JRIT_OneWayDecl;
import jolieReaderIntergrationTests.TestCases.InterfaceDecl.RequestResponseDecl.JRIT_RequestResponseDecl;
import jolieReaderIntergrationTests.TestCases.Misc.Block.JRIT_Block;
import jolieReaderIntergrationTests.TestCases.Misc.Comments.JRIT_Comments;
import jolieReaderIntergrationTests.TestCases.Misc.EOF.JRIT_EOF;
import jolieReaderIntergrationTests.TestCases.Misc.Line.JRIT_Line;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.JRIT_ServiceDecl;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Courier.JRIT_Courier;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.DefineProcedure.JRIT_DefineProcedure;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Embed.JRIT_Embed;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Embedded.JRIT_Embedded;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Execution.JRIT_Execution;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Init.JRIT_Init;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.InputPort.JRIT_InputPort;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.Main.JRIT_Main;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.OutputPort.JRIT_OutputPort;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortAggregates.JRIT_PortAggregates;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortInterfaces.JRIT_PortInterfaces;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortLocation.JRIT_PortLocation;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortProtocol.JRIT_PortProtocol;
import jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortRedirects.JRIT_PortRedirects;
import jolieReaderIntergrationTests.TestCases.TypeDecl.JRIT_TypeDecl;
import jolieReaderIntergrationTests.SimpleFiles.JRIT_SimpleFiles;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JolieReaderIntegrationTest {
    private final Map<String, String> filesAndPaths = createHashMapOfTestFiles();

    // ImportDecl - Import
    @Test
    public void readImportTest() {
        testCase(new JRIT_Import());
    }

    // ImportDecl - Include
    @Test
    public void readIncludeTest() {
        testCase(new JRIT_Include());
    }

    // InterfaceDecl
    @Test
    public void readInterfaceDeclTest() {
        testCase(new JRIT_InterfaceDecl());
    }

    // InterfaceDecl - RequestResponseDecl
    @Test
    public void readRequestResponseDeclTest() {
        testCase(new JRIT_RequestResponseDecl());
    }

    // InterfaceDecl - OneWayDecl
    @Test
    public void readOneWayDeclTest() {
        testCase(new JRIT_OneWayDecl());
    }

    // TypeDecl
    @Test
    public void readTypeDeclTest() {
        testCase(new JRIT_TypeDecl());
    }

    // ServiceDecl
    @Test
    public void readServiceDeclTest() {
        testCase(new JRIT_ServiceDecl());
    }

    // ServiceDecl - Execution
    @Test
    public void readExecutionTest() {
        testCase(new JRIT_Execution());
    }

    // ServiceDecl - Embed
    @Test
    public void readEmbedTest() {
        testCase(new JRIT_Embed());
    }

    // ServiceDecl - Embedded
    @Test
    public void readEmbeddedTest() {
        testCase(new JRIT_Embedded());
    }

    // ServiceDecl - InputPort
    @Test
    public void readInputPortTest() {
        testCase(new JRIT_InputPort());
    }

    // ServiceDecl - OutputPort
    @Test
    public void readOutputPortTest() {
        testCase(new JRIT_OutputPort());
    }

    // ServiceDecl - Init
    @Test
    public void readInitTest() {
        testCase(new JRIT_Init());
    }

    // ServiceDecl - DefineProcedure
    @Test
    public void readDefineProcedureTest() {
        testCase(new JRIT_DefineProcedure());
    }

    // ServiceDecl - Main
    @Test
    public void readMainTest() {
        testCase(new JRIT_Main());
    }

    // ServiceDecl - Courier
    @Test
    public void readCourierTest() {
        testCase(new JRIT_Courier());
    }

    // ServiceDecl - PortParameters - PortLocation
    @Test
    public void readPortLocationTest() {
        testCase(new JRIT_PortLocation());
    }

    // ServiceDecl - PortParameters - PortProtocol
    @Test
    public void readPortProtocolTest() {
        testCase(new JRIT_PortProtocol());
    }

    // ServiceDecl - PortParameters - PortInterfaces
    @Test
    public void readPortInterfacesTest() {
        testCase(new JRIT_PortInterfaces());
    }

    // ServiceDecl - PortParameters - PortAggregates
    @Test
    public void readPortAggregatesTest() {
        testCase(new JRIT_PortAggregates());
    }

    // ServiceDecl - PortParameters - PortRedirects
    @Test
    public void readPortRedirectsTest() {
        testCase(new JRIT_PortRedirects());
    }

    // Misc - Block
    @Test
    public void readBlockTest() {
        testCase(new JRIT_Block());
    }

    // Misc - Line
    @Test
    public void readLineTest() {
        testCase(new JRIT_Line());
    }

    // Misc - Comments
    @Test
    public void readCommentsTest() {
        testCase(new JRIT_Comments());
    }

    // Misc - EOF
    @Test
    public void readEOFTest() {
        testCase(new JRIT_EOF());
    }

    // Simple file
    @Test
    public void readSimpleFilesTest() {
        testCase(new JRIT_SimpleFiles());
    }

    // Multiple files


    // ----

    private void testCase(JolieReaderIntegrationTestCase tester) {
        testCase(tester, false);
    }

    private void testCase(JolieReaderIntegrationTestCase tester, Boolean printTree) {
        for (String fileName : tester.getFileNames()) {
            Set<Node.Op> nodes = readFolder(Paths.get(filesAndPaths.get(fileName)));
            assertEquals(1, nodes.size());
            Node.Op resultPluginNode = nodes.iterator().next();

            if (printTree) { // prints ecco tree instead of testing
                System.out.println("\n");
                printECCO(resultPluginNode);
                System.out.println("\n" + "End of " + fileName + "\n\n");
            } else { // tests ecco tree
                tester.test(resultPluginNode, fileName);
            }
        }
        tester.clearFileNames(); // clear fileNames so that tester ready for next test
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

    public void printECCO(Node.Op rootNode) {
        System.out.println(rootNode.toString());
        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            printECCO(rootNode.getChildren().get(i));
        }
    }
}

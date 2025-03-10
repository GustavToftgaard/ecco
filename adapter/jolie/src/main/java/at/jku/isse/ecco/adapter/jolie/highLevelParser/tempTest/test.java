package at.jku.isse.ecco.adapter.jolie.highLevelParser.tempTest;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.tempParserRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class test {
    public static List<String> listFiles(String dir) throws IOException {
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            return stream.filter(file -> !Files.isDirectory(file)).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
        }
    }

    // public static List<String> getExpectedLines(String fileName, String dir) throws IOException {
    //     String expectedName = fileName.substring(0, fileName.indexOf(".")) + ".out";
    //     return Files.readAllLines(Paths.get(dir + expectedName));
    // }

    public static void runTestFiles(String dir) throws IOException {
        List<String> filePaths = test.listFiles(dir);
        for (String fileName : filePaths) {
            if (fileName.endsWith("0.ol")) {
                // System.out.println("testing file " + fileName); //for fast 'debugging'
                String inputFile = new String(Files.readAllBytes(Paths.get(dir + fileName)));

                tempParserRunner controller = new tempParserRunner();
                List<JolieToken> actualOutputLines = controller.execute(inputFile);

                for (JolieToken line : actualOutputLines) {
                    // for (String word : line) {
                    //     System.out.println(word);
                    // }
                    if (line.type != SPACE) {
                        System.out.println(line);
                    }

                }


                // List<String> sampleFileExpectedLines = TestUtil.getExpectedLines(fileName, dir);
                // if (sampleFileExpectedLines.size() != actualOutputLines.size())
                //     fail(fileName + ": the expected outcome " + sampleFileExpectedLines.size() + " and the actual outcome " + actualOutputLines.size() + " do not match");

                // String fileMsg = "examined file: " + fileName;  //for potential error messaged

                // for (int i = 0; i < sampleFileExpectedLines.size(); i++) {
                //     String actualLine = actualOutputLines.get(i);
                //     String expectedLine = sampleFileExpectedLines.get(i);
                //     String error = "\n Expected line " + i + " to start with: ''" + expectedLine + "'' and actual was: ''" + actualLine;
                //     assertTrue(actualLine.startsWith(expectedLine), fileMsg + error);
                // }
            }
        }
    }

    public static void main(String[] args) {
        try {
            runTestFiles("adapter/jolie/src/main/java/at/jku/isse/ecco/adapter/jolie/highLevelParser/tempTest/testFiles/");
        } catch (IOException e) {
            System.out.println("Error on runTestFiles");
            e.printStackTrace();
        }
    }
}


package at.jku.isse.ecco.adapter.jolie.highLevelParser.tempTest;

import static at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType.*;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.AstPrinter;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.tempParserRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// temp jolie parser imports
//import jolie.cli.CommandLineException;
//import jolie.cli.CommandLineParser;
//import jolie.Interpreter;
//import jolie.lang.CodeCheckException;
//import jolie.lang.parse.SemanticVerifier;
//import jolie.lang.parse.ast.Program;
//import jolie.lang.parse.util.ParsingUtils;
//import jolie.runtime.FaultException;
//import jolie.runtime.JavaService;
//import jolie.runtime.Value;
//import jolie.runtime.embedding.RequestResponse;

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
            if (fileName.endsWith("1.ol")) { // TODO: .iol files included?
                // System.out.println("testing file " + fileName); //for fast 'debugging'
                String inputFile = new String(Files.readAllBytes(Paths.get(dir + fileName)));

                tempParserRunner controller = new tempParserRunner();
                List<Node> output = controller.execute(inputFile);

//                List<JolieToken> parserInput = new ArrayList<>();
//                for (JolieToken token : output) {
//                    if (token.type != SPACE && token.type != COMMENT && token.type != MULTILINE_COMMENT) {
//                        parserInput.add(token);
//                    }
//                }

                AstPrinter printer = new AstPrinter();
                System.out.println(printer.print(output));
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

        // temp jolie parser test

//        try( CommandLineParser cmdLnParser =
//                     new CommandLineParser( newArgs.toArray(new String[0]), JolieSlicer.class.getClassLoader() ) ) {
//
//            Interpreter.Configuration intConf = cmdLnParser.getInterpreterConfiguration();
//
//            SemanticVerifier.Configuration semVerConfig =
//                    new SemanticVerifier.Configuration( intConf.executionTarget() );
//            semVerConfig.setCheckForMain( false );
//
//            Program program = ParsingUtils.parseProgram(
//                    intConf.inputStream(),
//                    intConf.programFilepath().toURI(),
//                    intConf.charset(),
//                    intConf.includePaths(),
//                    intConf.packagePaths(),
//                    intConf.jolieClassLoader(),
//                    intConf.constants(),
//                    semVerConfig,
//                    INCLUDE_DOCUMENTATION );
//
//            final Slicer slicer = Slicer.create( program, outputDirectory, services );
//            slicer.generateServiceDirectories();
//        } catch ( CommandLineException | InvalidConfigurationFileException | CodeCheckException | IOException e ) {
//            throw new FaultException( e.getClass().getSimpleName(), e );
//        }

    }
}


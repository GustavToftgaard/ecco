import at.jku.isse.ecco.EccoException;
import at.jku.isse.ecco.adapter.ArtifactPlugin;
import at.jku.isse.ecco.core.Association;
import at.jku.isse.ecco.core.Checkout;
import at.jku.isse.ecco.module.ModuleRevision;
import at.jku.isse.ecco.service.EccoService;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

public class JolieEccoServiceTest {

    private Path outputDir = null;
    private Path repositoryDir = null;
    private Path inputDir = null;

    @BeforeEach
    public void beforeTest() throws IOException {
        System.out.println("\nBEFORE");

        if (outputDir == null || repositoryDir == null || inputDir == null) {
            this.outputDir = Paths.get("src/integrationTest/resources/versionTestFolders/autoTestOutput");
            this.repositoryDir = outputDir.resolve("repository");
            this.inputDir = Paths.get("src/integrationTest/resources/versionTestFolders");
        }

        // delete files and directories
        Path pathToBeDeleted = repositoryDir.resolve(".ecco");
        if (Files.exists(pathToBeDeleted)) {
            Files.walk(pathToBeDeleted)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        Files.deleteIfExists(this.outputDir.resolve("parent_repo/.ecco/.ignores"));
        Files.deleteIfExists(this.outputDir.resolve("parent_repo/.ecco/ecco.db"));
        Files.deleteIfExists(this.outputDir.resolve("parent_repo/.ecco"));

        if (Files.exists(repositoryDir)) {
            Files.walk(repositoryDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        if (Files.exists(outputDir)) {
            Files.walk(outputDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        // create directories
        Files.createDirectories(this.repositoryDir);
        Files.createDirectories(this.outputDir.resolve("parent_repo"));
    }

    @AfterEach
    public void afterTest() throws IOException {
        // delete files and directories
//        Files.deleteIfExists(repositoryDir.resolve(".ecco/.ignores"));
//        Files.deleteIfExists(repositoryDir.resolve(".ecco/ecco.db"));
//        Files.deleteIfExists(repositoryDir.resolve(".ecco"));

        Files.deleteIfExists(outputDir.resolve("parent_repo/.ecco/.ignores"));
        Files.deleteIfExists(outputDir.resolve("parent_repo/.ecco/ecco.db"));
        Files.deleteIfExists(outputDir.resolve("parent_repo/.ecco"));

//        Files.deleteIfExists(this.repositoryDir);
        Files.deleteIfExists(this.outputDir.resolve("parent_repo"));
//        Files.deleteIfExists(this.outputDir);

        System.out.println("AFTER\n");
    }

    // testing out class ServiceLoader.load
    @Test
    public void Temp_Test() {
        System.out.println("\n----Test Temp----\n");

        final ServiceLoader<ArtifactPlugin> loader = ServiceLoader.load(ArtifactPlugin.class);

        for (final ArtifactPlugin plugin : loader) {
            System.out.println("-+-" + plugin.getPluginId() + "-+-");
        }
    }

    @Test
    public void Init_Test() throws EccoException, IOException {
        EccoService service = new EccoService(inputDir.resolve(Paths.get("V1_numbers")), repositoryDir.resolve(Paths.get(".ecco")));

        service.init();

        service.close();

        service.open();
    }

     @Test
    public void Commit_Test() throws EccoException, IOException {
        EccoService service = new EccoService(inputDir.resolve(Paths.get("V1_numbers")), repositoryDir.resolve(Paths.get(".ecco")));
        service.init();

        System.out.println("Commit 1:");
        service.commit();
        for (Association a : service.getRepository().getAssociations()) {
            System.out.println("A(" + a.getRootNode().countArtifacts() + "): " + a.computeCondition().toString());
        }

        System.out.println("Commit 2:");
        service.setBaseDir(inputDir.resolve(Paths.get("V2_numbers")));
        service.commit();
        for (Association a : service.getRepository().getAssociations()) {
            System.out.println("A(" + a.getRootNode().countArtifacts() + "): " + a.computeCondition().toString());
        }

        System.out.println("Commit 3:");
        service.setBaseDir(inputDir.resolve(Paths.get("V3_numbers")));
        service.commit();
        for (Association a : service.getRepository().getAssociations()) {
            System.out.println("A(" + a.getRootNode().countArtifacts() + "): " + a.computeCondition().toString());
        }

        service.close();
    }

//    @Test
//    public void Checkout_Test() throws EccoException, IOException {
//        EccoService service = new EccoService(inputDir.resolve(Paths.get("V1_purpleshirt")), repositoryDir.resolve(Paths.get(".ecco")));
//        service.init();
//
//        System.out.println("Commit 1:");
//        service.setBaseDir(inputDir.resolve(Paths.get("V1_purpleshirt")));
//        service.commit();
//        for (Association a : service.getRepository().getAssociations()) {
//            System.out.println("A(" + a.getRootNode().countArtifacts() + "): " + a.computeCondition().toString());
//        }
//
//        System.out.println("Commit 2:");
//        service.setBaseDir(inputDir.resolve(Paths.get("V2_stripedshirt")));
//        service.commit();
//        for (Association a : service.getRepository().getAssociations()) {
//            System.out.println("A(" + a.getRootNode().countArtifacts() + "): " + a.computeCondition().toString());
//        }
//
//        System.out.println("Checkout:");
//        service.setBaseDir(outputDir.resolve(Paths.get("checkout")));
//        Checkout checkout = service.checkout("person.1, purpleshirt.1, stripedshirt.1, new.1");
//        for (ModuleRevision m : checkout.getMissing()) {
//            System.out.println("MISSING: " + m);
//        }
//
//        service.close();
//    }
}

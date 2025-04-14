package jolieReaderIntergrationTests.interfacesAndAbstractClasses;

import at.jku.isse.ecco.tree.Node;

public interface IJolieReaderIntegrationTestCase {
    public void test(Node.Op node, String fileName);
}

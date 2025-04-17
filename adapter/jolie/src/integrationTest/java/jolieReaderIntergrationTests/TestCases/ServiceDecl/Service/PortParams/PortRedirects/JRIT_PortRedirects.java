package jolieReaderIntergrationTests.TestCases.ServiceDecl.Service.PortParams.PortRedirects;

import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_PortRedirects extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put(" ", 1);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                test1(node);
        }
    }

    private void test1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(6, pluginNodeChildren.size());

        Node.Op node;
    }
}

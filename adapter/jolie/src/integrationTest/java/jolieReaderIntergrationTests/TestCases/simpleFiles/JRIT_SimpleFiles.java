package jolieReaderIntergrationTests.TestCases.simpleFiles;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieTokenType;
import at.jku.isse.ecco.tree.Node;
import jolieReaderIntergrationTests.interfacesAndAbstractClasses.JolieReaderIntegrationTestCase;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JRIT_SimpleFiles extends JolieReaderIntegrationTestCase {
    static {
        fileNames.put("simpleFile1.ol", 1);
    }

    @Override
    public void test(Node.Op node, String fileName) {
        int key = fileNames.get(fileName);

        switch (key) {
            case 1:
                testSimpleFile1(node);
        }
    }

    private void testSimpleFile1(Node.Op pluginNode) {
        List<Node.Op> pluginNodeChildren = (List<Node.Op>) pluginNode.getChildren();

        assertEquals(6, pluginNodeChildren.size());

        Node.Op node;

        // 1: ImportDecl
        node = pluginNodeChildren.get(0);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 1.1: Import
        node = pluginNodeChildren.get(0).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 1.1.1: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "console", 1);

        // 1.1.2: ID
        node = pluginNodeChildren.get(0).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "Console", 1);

        // ------

        // 2: ImportDecl
        node = pluginNodeChildren.get(1);
        checkContextNode(node, NodeTypes.IMPORTDECL, 1);

        // 2.1: Import
        node = pluginNodeChildren.get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.IMPORT, 2);

        // 2.1.1: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "string_utils", 2);

        // 2.1.2: ID
        node = pluginNodeChildren.get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "StringUtils", 2);

        // ------

        // 3: TypeDecl
        node = pluginNodeChildren.get(2);
        checkContextNode(node, NodeTypes.TYPEDECL, 2);

        // 3.1: ID
        node = pluginNodeChildren.get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 4);

        // 3.2: Block
        node = pluginNodeChildren.get(2).getChildren().get(1);
        checkContextNode(node, NodeTypes.BLOCK, 4);

        // 3.2.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "{\n  ", 4);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "m:int\n  ", 5);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "n:int\n", 6);

        checkContextNode(node.getChildren().get(3), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(3).getChildren().get(0), "}", 7);

        // ------

        // 4: InterfaceDecl
        node = pluginNodeChildren.get(3);
        checkContextNode(node, NodeTypes.INTERFACEDECL, 2);

        // 4.1: ID
        node = pluginNodeChildren.get(3).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "NumbersAPI", 9);

        // 4.2: Request_Response_Decl
        node = pluginNodeChildren.get(3).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_DECL, 2);

        // 4.2.1: Request_Response_Element
        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 4.2.1.*: IDs
        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumUpTo", 11);

        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(0).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "int", 11);

        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(0).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 11);

        // 4.2.2: REQUEST_RESPONSE_ELEMENT
        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(1);
        checkContextNode(node, NodeTypes.REQUEST_RESPONSE_ELEMENT, 3);

        // 4.2.2.*: IDs
        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sumBetween", 12);

        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(1).getChildren().get(1);
        checkTokenNode(node, JolieTokenType.ID, "twoArguments", 12);

        node = pluginNodeChildren.get(3).getChildren().get(1).getChildren().get(1).getChildren().get(2);
        checkTokenNode(node, JolieTokenType.ID, "int", 12);

        // ------

        // 5: ServiceDecl
        node = pluginNodeChildren.get(4);
        checkContextNode(node, NodeTypes.SERVICEDECL, 4);

        // 5.1: ID
        node = pluginNodeChildren.get(4).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "Numbers", 15);

        // 5.2: Execution
        node = pluginNodeChildren.get(4).getChildren().get(1);
        checkContextNode(node, NodeTypes.EXECUTION, 1);

        // 5.2.1: ID
        node = pluginNodeChildren.get(4).getChildren().get(1).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "sequential", 16);

        // 5.3:InputPort
        node = pluginNodeChildren.get(4).getChildren().get(2);
        checkContextNode(node, NodeTypes.INPUTPORT, 3);

        // 5.3.1: ID
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID, "numbersInput", 18);

        // 5.3.2: PortLocation
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1);
        checkContextNode(node, NodeTypes.PORTLOCATION, 1);

        // 5.3.2.1: Line
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1).getChildren().get(0);
        checkContextNode(node, NodeTypes.LINE, 1);

        // 5.3.2.1.1: LineContents
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(1).getChildren().get(0).getChildren().get(0);
        checkLineNode(node, " {\n" + "    location: \"local\"\n" + "    ", 19);

        // 5.3.3: PortInterfaces
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(2);
        checkContextNode(node, NodeTypes.PORTINTERFACES, 1);

        // 5.3.3.1: Token
        node = pluginNodeChildren.get(4).getChildren().get(2).getChildren().get(2).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.ID,"NumbersAPI", 20);

        // 5.4: Main
        node = pluginNodeChildren.get(4).getChildren().get(3);
        checkContextNode(node, NodeTypes.MAIN, 1);

        // 5.4.1: Block
        node = pluginNodeChildren.get(4).getChildren().get(3).getChildren().get(0);
        checkContextNode(node, NodeTypes.BLOCK, 8);

        // 5.4.1.*: Lines
        checkContextNode(node.getChildren().get(0), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(0).getChildren().get(0), "{\n" + "\n" + "    ", 23);

        checkContextNode(node.getChildren().get(1), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(1).getChildren().get(0), "[ sumUpTo( n )( response ) {\n" + "      ", 25);

        checkContextNode(node.getChildren().get(2), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(2).getChildren().get(0), "...\n" + "    ", 26);

        checkContextNode(node.getChildren().get(3), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(3).getChildren().get(0), "} ]\n" + "\n" + "    ", 27);

        checkContextNode(node.getChildren().get(4), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(4).getChildren().get(0), "[ sumBetween( request )( response ) {\n" + "      ", 29);

        checkContextNode(node.getChildren().get(5), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(5).getChildren().get(0), "...\n" + "    ", 30);

        checkContextNode(node.getChildren().get(6), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(6).getChildren().get(0), "} ]\n" + "\n" + "  ", 31);

        checkContextNode(node.getChildren().get(7), NodeTypes.LINE, 1);
        checkLineNode(node.getChildren().get(7).getChildren().get(0), "}", 33);

        // 6 EndOfFile
        node = pluginNodeChildren.get(5);
        checkContextNode(node, NodeTypes.EOF, 1);

        // 6.1 EOF
        node = pluginNodeChildren.get(5).getChildren().get(0);
        checkTokenNode(node, JolieTokenType.EOF, "", 35);
    }
}

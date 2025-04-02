package at.jku.isse.ecco.adapter.jolie;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.NodeTypes;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieContextArtifactData;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.visitors.Data.JolieTokenArtifactData;
import at.jku.isse.ecco.tree.Node;

import java.util.Objects;

// TODO: check when new line

public class ECCOToString {
    private final Node.Op rootNode;

    public ECCOToString(Node.Op rootNode) {
        this.rootNode = rootNode;
    }

    public String convert() {
        StringBuilder res = new StringBuilder();

        for (Node.Op node : rootNode.getChildren()) {
            res.append(nodeTypeCase(node));
        }

        return res.toString();
    }

    private String nodeTypeCase(Node.Op node) {
        if (isContextNode(node)) {
            return contextNodeCase(node);
        } else {
            return " ?errorNodeTypeCase ";
        }
    }

    private String contextNodeCase(Node.Op node) {
        NodeTypes nodeType = Objects.requireNonNull(toContextNode(node)).getType();
        String res;
        switch (nodeType) {
            case IMPORTDECL:
                res = convertImportDecl(node);
                break;
            case IMPORT:
                res = convertImport(node);
                break;
            case INCLUDE:
                res = convertInclude(node);
                break;

            case INTERFACEDECL:
                res = convertInterfaceDecl(node);
                break;

            case TYPEDECL:
                res = convertTypeDecl(node);
                break;

            case SERVICEDECL:
                res = convertServiceDecl(node);
                break;
            case SERVICE:
                res = convertService(node);
                break;
            case EXECUTION:
                res = convertExecution(node);
                break;
            case EMBED:
                res = convertEmbed(node);
                break;
            case EMBEDDED:
                res = convertEmbedded(node);
                break;
            case INPUTPORT:
                res = convertInputPort(node);
                break;
            case OUTPUTPORT:
                res = convertOutputPort(node);
                break;
            case PORTLOCATION:
                res = convertPortLocation(node);
                break;
            case PORTPROTOCOL:
                res = convertPortProtocol(node);
                break;
            case PORTINTERFACES:
                res = convertPortInterfaces(node);
                break;
            case PORTAGGREGATES:
                res = convertPortAggregates(node);
                break;
            case PORTREDIRECTS:
                res = convertPortRedirects(node);
                break;
            case INIT:
                res = convertInit(node);
                break;
            case MAIN:
                res = convertMain(node);
                break;
            case COURIER:
                res = convertCourier(node);
                break;
            case DEFINEPROCEDURE:
                res = convertDefineProcedure(node);
                break;

            case BLOCK:
                res = convertBlock(node);
                break;
            case LINE:
                res = convertLine(node);
                break;


            default: {
                res = " ?error? ";
            }
        }

        return res;
    }

    //    IMPORTDECL
    private String convertImportDecl(Node.Op node) {
        StringBuilder res = new StringBuilder();

        for (Node.Op childNode : node.getChildren()) {
            res.append(nodeTypeCase(childNode));
        }

        return res.toString();
    }

    //    IMPORT
    private String convertImport(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 2) {
            res.append("from").append(" ");
            res.append(toTokenNode(node.getChildren().get(0)).lexeme);
            res.append(" ").append("import").append(" ");
            res.append(toTokenNode(node.getChildren().get(1)).lexeme);
            res.append("\n");
        } else if (node.getChildren().size() == 3) {
            res.append("from").append(" ");
            res.append(toTokenNode(node.getChildren().get(0)).lexeme);
            res.append(" ").append("import").append(" ");
            res.append(toTokenNode(node.getChildren().get(1)).lexeme);
            res.append(contextNodeCase(node.getChildren().get(2)));
            res.append("\n");
        } else {
            return " ?error? ";
        }

        return res.toString();
    }

    //    INCLUDE
    private String convertInclude(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 1) {
            res.append("include").append(" ");
            res.append(toTokenNode(node.getChildren().get(0)).lexeme);
            res.append("\n");
        } else {
            return " ?error? ";
        }

        return res.toString();
    }

    //    INTERFACEDECL
    private String convertInterfaceDecl(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 2) {
            res.append("interface").append(" ");
            res.append(toTokenNode(node.getChildren().get(0)).lexeme);
            res.append(" ");
            res.append(contextNodeCase(node.getChildren().get(1)));
            res.append("\n");
        } else if (node.getChildren().size() == 3) {
            res.append("interface").append(" ");
            res.append("extender").append(" ");
            res.append(toTokenNode(node.getChildren().get(0)).lexeme);
            res.append(" ");
            res.append(contextNodeCase(node.getChildren().get(2)));
            res.append("\n");
        } else {
            return " ?error? ";
        }

        return res.toString();
    }

    //    TYPEDECL
    private String convertTypeDecl(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("type").append(" ");
        res.append(toTokenNode(node.getChildren().get(0)).lexeme);
        res.append(":").append(" ");

        if (node.getChildren().size() == 2) {
            if (isContextNode(node.getChildren().get(1))) { // case block
                res.append(contextNodeCase(node.getChildren().get(1)));
            } else {
                res.append(toTokenNode(node.getChildren().get(1)).lexeme);
            }
        } else if (node.getChildren().size() == 3) {
            res.append(toTokenNode(node.getChildren().get(1)).lexeme);
            res.append(" ");
            res.append(contextNodeCase(node.getChildren().get(2)));
        } else {
            return " ?error? ";
        }

        res.append("\n");

        return res.toString();
    }

    //    SERVICEDECL
    private String convertServiceDecl(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("service").append(" ");
        res.append(toTokenNode(node.getChildren().get(0)).lexeme);

        int i = 1;
        if (isTokenNode(node.getChildren().get(i))) {
            res.append("(");
            while (i < node.getChildren().size() && isTokenNode(node.getChildren().get(i))) {
                res.append(toTokenNode(node.getChildren().get(i)));
                res.append(" "); // TODO: remove extra end space
                i++;
            }
            res.append(")").append(" ");
        }

        res.append("{").append("\n");
        while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
            res.append(contextNodeCase(node.getChildren().get(i)));
            i++;
        }
        res.append("}");

        if (i != (node.getChildren().size())) {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    SERVICE
    private String convertService(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 1) {
            contextNodeCase(node.getChildren().get(0));
        } else {
            res.append(" ?error? ");
        }

        return res.toString();
    }

    //    EXECUTION
    private String convertExecution(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("execution");

        if (node.getChildren().size() == 2) {
            res.append(":");
            res.append(toTokenNode(node.getChildren().get(1)).lexeme);
        } else if (node.getChildren().size() == 1) {
            res.append("{");
            res.append(toTokenNode(node.getChildren().get(1)).lexeme);
            res.append("}");
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    EMBED
    private String convertEmbed(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 2) {
            res.append("embed").append(" ");
            res.append(toTokenNode(node.getChildren().get(0)).getLexeme()).append(" ");
            res.append("as").append(" ");
            res.append(toTokenNode(node.getChildren().get(1)).getLexeme());
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    EMBEDDED
    private String convertEmbedded(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 1) {
            res.append("embedded").append(" ");
            res.append(contextNodeCase(node.getChildren().get(0)));
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    INPUTPORT
    private String convertInputPort(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("inputPort").append(" ");
        res.append(toTokenNode(node.getChildren().get(0)).getLexeme());
        res.append(" ").append("{").append("\n");

        int i = 1;
        while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
            res.append(contextNodeCase(node.getChildren().get(i)));
            i++;
        }

        res.append("}");

        res.append("\n");

        return res.toString();
    }

    //    OUTPUTPORT
    private String convertOutputPort(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("outputPort").append(" ");
        res.append(toTokenNode(node.getChildren().get(0)).getLexeme());
        res.append(" ").append("{").append("\n");

        int i = 1;
        while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
            res.append(contextNodeCase(node.getChildren().get(i)));
            i++;
        }

        res.append("}");

        res.append("\n");

        return res.toString();
    }

    //    PORTLOCATION
    private String convertPortLocation(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 2) {
            res.append("Location").append(":").append(" ");
            res.append(contextNodeCase(node.getChildren().get(1)));
        } else if (node.getChildren().size() == 1) {
            res.append("location").append(":").append(" ");
            res.append(contextNodeCase(node.getChildren().get(0)));
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    PORTPROTOCOL
    private String convertPortProtocol(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (node.getChildren().size() == 2) {
            res.append("Protocol").append(":").append(" ");
            res.append(contextNodeCase(node.getChildren().get(1)));
        } else if (node.getChildren().size() == 1) {
            res.append("protocol").append(":").append(" ");
            res.append(contextNodeCase(node.getChildren().get(0)));
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    PORTINTERFACES
    private String convertPortInterfaces(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (isTokenNode(node.getChildren().get(0))) {
            res.append("Interfaces").append(":").append(" ");
            int i = 1;
            while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
                res.append(contextNodeCase(node.getChildren().get(i)));
                i++;
            }
        } else if (isContextNode(node.getChildren().get(0))) {
            res.append("interfaces").append(":").append(" ");
            int i = 0;
            while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
                res.append(contextNodeCase(node.getChildren().get(i)));
                i++;
            }
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    PORTAGGREGATES
    private String convertPortAggregates(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (isTokenNode(node.getChildren().get(0))) {
            res.append("Aggregates").append(":").append(" ");
            int i = 1;
            while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
                res.append(contextNodeCase(node.getChildren().get(i)));
                i++;
            }
            res.append(toTokenNode(node.getChildren().get(1)).getLexeme());
        } else if (isContextNode(node.getChildren().get(0))) {
            res.append("aggregates").append(":").append(" ");
            int i = 0;
            while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
                res.append(contextNodeCase(node.getChildren().get(i)));
                i++;
            }
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    PORTREDIRECTS
    private String convertPortRedirects(Node.Op node) {
        StringBuilder res = new StringBuilder();

        if (isTokenNode(node.getChildren().get(0))) {
            res.append("Redirects").append(":").append(" ");
            int i = 1;
            while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
                res.append(contextNodeCase(node.getChildren().get(i)));
                i++;
            }
            res.append(toTokenNode(node.getChildren().get(1)).getLexeme());
        } else if (isContextNode(node.getChildren().get(0))) {
            res.append("redirects").append(":").append(" ");
            int i = 0;
            while (i < node.getChildren().size() && isContextNode(node.getChildren().get(i))) {
                res.append(contextNodeCase(node.getChildren().get(i)));
                i++;
            }
        } else {
            res.append(" ?error? ");
        }

        res.append("\n");

        return res.toString();
    }

    //    INIT
    private String convertInit(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("init").append(" ");
        res.append(contextNodeCase(node.getChildren().get(0)));
        res.append("\n");

        return res.toString();
    }

    //    MAIN
    private String convertMain(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("main").append(" ");
        res.append(contextNodeCase(node.getChildren().get(0)));
        res.append("\n");

        return res.toString();
    }

    //    COURIER
    private String convertCourier(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("courier").append(" ");
        res.append("<");
        res.append(toTokenNode(node.getChildren().get(0)).getLexeme());
        res.append(">");
        res.append(contextNodeCase(node.getChildren().get(1)));
        res.append("\n");

        return res.toString();
    }

    //    DEFINEPROCEDURE
    private String convertDefineProcedure(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("define").append(" ");
        res.append(toTokenNode(node.getChildren().get(0)).getLexeme());
        res.append(" ");
        res.append(contextNodeCase(node.getChildren().get(1)));
        res.append("\n");

        return res.toString();
    }

    //    BLOCK
    private String convertBlock(Node.Op node) {
        StringBuilder res = new StringBuilder();

        res.append("{").append(" ");
        for (Node.Op childNode : node.getChildren()) {
            res.append(toTokenNode(childNode).getLexeme());
             res.append(" ");
        }
        res.append("}").append("\n");

        return res.toString();
    }

    //    LINE
    private String convertLine(Node.Op node) {
        StringBuilder res = new StringBuilder();

        for (Node.Op childNode : node.getChildren()) {
            res.append(toTokenNode(childNode).getLexeme());
            res.append(" ");
        }

        return res.toString();
    }

    // ----

    private boolean isContextNode(Node.Op node) {
        return (node.getArtifact().getData() instanceof JolieContextArtifactData);
    }

    private boolean isTokenNode(Node.Op node) {
        return (node.getArtifact().getData() instanceof JolieTokenArtifactData);
    }

    private JolieContextArtifactData toContextNode(Node.Op node) {
        return (JolieContextArtifactData) node.getArtifact().getData();
    }

    private JolieTokenArtifactData toTokenNode(Node.Op node) {
        return (JolieTokenArtifactData) node.getArtifact().getData();
    }
}

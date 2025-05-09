package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class Import implements Node {
    private String postLexeme = "";
    private final JolieToken fromID;
    private final JolieToken importID;
    private final Node line; //?

    public Import(JolieToken fromID, JolieToken importID, Node line) {
        this.fromID = fromID;
        this.importID = importID;
        this.line = line;
    }

    public Import(JolieToken fromID, JolieToken importID) {
        this.fromID = fromID;
        this.importID = importID;
        this.line = null;
    }

    public JolieToken getFromID() {
        return fromID;
    }

    public JolieToken getImportID() {
        return importID;
    }

    public Node getLine() {
        return line;
    }

    @Override
    public String getPostLexeme() {
        return postLexeme;
    }

    @Override
    public void setPostLexeme(String postLexeme) {
        this.postLexeme = postLexeme;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitImport(this);
    }
}
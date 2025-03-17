package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.Line;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class Import implements Node {
    private final JolieToken fromID;
    private final JolieToken importID;
    private Line line = null; //?

    public Import(JolieToken fromID, JolieToken importID, Line line) {
        this.fromID = fromID;
        this.importID = importID;
        this.line = line;
    }
    public Import(JolieToken fromID, JolieToken importID) {
        this.fromID = fromID;
        this.importID = importID;
    }

    public JolieToken getFromID() {
        return fromID;
    }

    public JolieToken getImportID() {
        return importID;
    }

    public Line getLine() {
        return line;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitImport(this);
    }
}

package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class Line implements Node {
    private final ArrayList<JolieToken> contents;

    public Line(ArrayList<JolieToken> contents) {
        this.contents = contents;
    }

    public ArrayList<JolieToken> getContents() {
        return contents;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitLine(this);
    }
}
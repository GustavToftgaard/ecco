package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

import java.util.ArrayList;

public class Line implements Node {
    private final String lineContents;
    private final int line;

    public Line(String lineContents, int line) {
        this.lineContents = lineContents;
        this.line = line;
    }

    public String getLineContents() {
        return lineContents;
    }

    public int getLine() {
        return line;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitLine(this);
    }
}
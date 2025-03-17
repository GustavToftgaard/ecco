package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

public interface Node {
    public <T> T accept(NodeVisitor<T> visitor);
}

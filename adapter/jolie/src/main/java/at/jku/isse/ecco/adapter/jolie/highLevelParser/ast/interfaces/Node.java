package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces;

public interface Node {
    public String getPostLexeme();
    public void setPostLexeme(String postLexeme);
    public <T> T accept(NodeVisitor<T> visitor);
}
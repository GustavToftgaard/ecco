package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.interfaceDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;

import java.util.ArrayList;

public class OneWayDecl  implements Node {
    private String postLexeme = "";
    private final ArrayList<OneWayElement> oneWayElements;

    public OneWayDecl(ArrayList<OneWayElement> oneWayElements) {
        this.oneWayElements = oneWayElements;
    }

    public ArrayList<OneWayElement> getOneWayElements() {
        return oneWayElements;
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
        return visitor.visitOneWayDecl(this);
    }
}
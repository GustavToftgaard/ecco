package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes.importDecl;

import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.Node;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.interfaces.NodeVisitor;
import at.jku.isse.ecco.adapter.jolie.highLevelParser.scanner.token.JolieToken;

public class Include implements Node {
    private final JolieToken includeID;

    public Include(JolieToken includeID) {
        this.includeID = includeID;
    }

    public JolieToken getIncludeID() {
        return includeID;
    }

    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visitInclude(this);
    }
}

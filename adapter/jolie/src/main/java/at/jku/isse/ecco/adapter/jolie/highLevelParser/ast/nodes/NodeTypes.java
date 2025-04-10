package at.jku.isse.ecco.adapter.jolie.highLevelParser.ast.nodes;

public enum NodeTypes {
    IMPORTDECL,
        IMPORT, INCLUDE,

    INTERFACEDECL,

    TYPEDECL,

    SERVICEDECL,
        SERVICE, EXECUTION, EMBED,
        EMBEDDED, INPUTPORT, OUTPUTPORT,
        PORTLOCATION, PORTPROTOCOL, PORTINTERFACES,
        PORTAGGREGATES, PORTREDIRECTS, INIT,
        MAIN, COURIER, DEFINEPROCEDURE,

    BLOCK,

    LINE,

    EOF
}

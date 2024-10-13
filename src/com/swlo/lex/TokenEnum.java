package com.swlo.lex;

public enum TokenEnum {
    NUMBER("Número"),
    IDENTIFIER("Identificador"),
    DRONE("Palavra reservada DRONE", "drone"),
    ROUTE("Palavra reservada ROUTE", "route"),
    NEW("Palavra reservada NEW", "new"),
    ON("Palavra reservada ON", "on"),
    LPAREN("Abre parênteses", "("),
    RPAREN("Fecha parênteses", ")"),
    COMMA("Vírgula", ","),
    COLON("Dois pontos", ":"),
    END("Ponto e vírgula", ";"),
    ASSIGN("Operador de atribuição", "="),
    STRING("String"),
    UNIT("Unidade", "m", "m/s", "minutes", "kg"),
    COORDINATE("Coordenada");

    private String description;
    private String[] values;

    TokenEnum(String description, String... values) {
        this.description = description;
        this.values = values;
    }

    public String getDescription() {
        return description;
    }

    public String[] getValues() {
        return values;
    }

    public static TokenEnum getEnumByValue(String value) {
        for (TokenEnum lexEnum : values()) {
            for (String val : lexEnum.values) {
                if (val.equals(value)) {
                    return lexEnum;
                }
            }
        }
        if (value.matches("\\d+(\\.\\d+)?")) {
            return NUMBER;
        } else if (value.matches("[a-zA-Z_][a-zA-Z0-9_]*(:[a-zA-Z_][a-zA-Z0-9_]*)?")) {
            return IDENTIFIER;
        } else if (value.matches("\\(\\d+,\\s*\\d+\\)")) {
            return COORDINATE;
        } else if (value.matches("\"[^\"]*\"")) {
            return STRING;
        }
        return null;
    }
}


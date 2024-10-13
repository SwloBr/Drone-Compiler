package com.swlo.token;

import com.swlo.lex.TokenEnum;

public class Token {
    public TokenEnum lex;
    public String value;

    public Token(TokenEnum lex, String value) {
        this.lex = lex;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "name=" + lex.name() +
                ", value='" + value + '\'' +
                '}';
    }
}

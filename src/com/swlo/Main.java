package com.swlo;

import com.swlo.lex.Analyser;
import com.swlo.token.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Exemplo de uso do Analyser com arquivo de entrada
        String filePath = "/home/swlo/src/Compiladores/Test.drone";
        Analyser lexer = new Analyser(filePath);
        List<Token> tokens = lexer.tokenize();
        tokens.forEach(token -> System.out.println(token.toString()));

    }
}


package com.swlo.lex;

import com.swlo.token.EntryAnalyser;
import com.swlo.token.Token;

import java.util.ArrayList;
import java.util.List;

public class Analyser {
    private EntryAnalyser entryAnalyser;
    private int currentChar;

    public Analyser(String filePath) {
        this.entryAnalyser = new EntryAnalyser(filePath);
        this.currentChar = -1;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        try {
            while ((currentChar = entryAnalyser.readNextChar()) != -1) {
                boolean matched = false;

                // Ignora espaços em branco
                while (currentChar != -1 && Character.isWhitespace(currentChar)) {
                    currentChar = entryAnalyser.readNextChar();
                }

                if (currentChar == -1) {
                    break;
                }

                currentToken.setLength(0);
                char current = (char) currentChar;

                // Constrói o token atual
                while (currentChar != -1 && !Character.isWhitespace(current) && current != ';' && current != '=') {
                    currentToken.append(current);
                    currentChar = entryAnalyser.readNextChar();
                    current = (char) currentChar;
                }

                String tokenValue = currentToken.toString();

                // Verifica tokens baseados no enum TokenEnum
                TokenEnum tokenEnum = TokenEnum.getEnumByValue(tokenValue);
                if (tokenEnum != null) {
                    tokens.add(new Token(tokenEnum, tokenValue));
                    matched = true;
                }

                if (!matched && !tokenValue.isEmpty()) {
                    throw new RuntimeException("Unexpected token: " + tokenValue);
                }

                // Adiciona tokens específicos como ponto e vírgula e igual
                if (current == ';') {
                    tokens.add(new Token(TokenEnum.END, String.valueOf(current)));
                } else if (current == '=') {
                    tokens.add(new Token(TokenEnum.ASSIGN, String.valueOf(current)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
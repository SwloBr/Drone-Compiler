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
                if (current == '(') {
                    currentToken.append(current);
                    currentChar = entryAnalyser.readNextChar();
                    current = (char) currentChar;
                    while (currentChar != -1 && current != ')') {
                        currentToken.append(current);
                        currentChar = entryAnalyser.readNextChar();
                        current = (char) currentChar;
                    }
                    currentToken.append(current);
                    currentChar = entryAnalyser.readNextChar();
                    String tokenValue = currentToken.toString();
                    TokenEnum tokenEnum = TokenEnum.getEnumByValue(tokenValue);
                    if (tokenEnum != null) {
                        tokens.add(new Token(tokenEnum, tokenValue));
                        matched = true;
                    }
                } else if (current == '"') {
                    currentToken.append(current);
                    currentChar = entryAnalyser.readNextChar();
                    current = (char) currentChar;
                    while (currentChar != -1 && current != '"') {
                        currentToken.append(current);
                        currentChar = entryAnalyser.readNextChar();
                        current = (char) currentChar;
                    }
                    currentToken.append(current);
                    currentChar = entryAnalyser.readNextChar();
                    String tokenValue = currentToken.toString();
                    TokenEnum tokenEnum = TokenEnum.getEnumByValue(tokenValue);
                    if (tokenEnum != null) {
                        tokens.add(new Token(tokenEnum, tokenValue));
                        matched = true;
                    }
                } else if (current == '{' || current == '}') {
                    currentToken.append(current);
                    String tokenValue = currentToken.toString();
                    TokenEnum tokenEnum = TokenEnum.getEnumByValue(tokenValue);
                    if (tokenEnum != null) {
                        tokens.add(new Token(tokenEnum, tokenValue));
                        matched = true;
                    }
                    currentChar = entryAnalyser.readNextChar();
                } else {
                    while (currentChar != -1 && !Character.isWhitespace(current) && current != ';' && current != '=' && current != '{' && current != '}' && current != ':' && current != '"') {
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

                    // Se o próximo caractere for dois pontos, adiciona como token separado
                    if (current == ':') {
                        tokens.add(new Token(TokenEnum.COLON, String.valueOf(current)));
                        currentChar = entryAnalyser.readNextChar();
                    }
                }

                if (!matched && !currentToken.toString().isEmpty()) {
                    throw new RuntimeException("Unexpected token: " + currentToken);
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
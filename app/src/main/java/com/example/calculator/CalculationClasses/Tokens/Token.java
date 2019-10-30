package com.example.calculator.CalculationClasses.Tokens;

import com.example.calculator.Enums.TokenType;

public class Token {
    private TokenType type;
    public TokenType getType()
    {
        return type;
    }
    private String value;
    public String getValue()
    {
        return value;
    }

    public Token(TokenType type, String value)
    {
        this.type = type;
        this.value = value;
    }
}

package com.example.calculator.CalculationClasses;

import com.example.calculator.CalculationClasses.Tokens.Token;
import com.example.calculator.Enums.TokenType;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Parser {
    private String result;
    private String lastError;
    private ArrayList<Token> fullData;
    private ArrayList<Token> data;
    public Parser()
    {
        result = "";
        lastError = "";
        fullData = new ArrayList<Token>();
        data = new ArrayList<Token>();
    }

    public Token NextParentheses()
    {
        if(data.size() > 0)
        {
            int amountOfLeft = 0;
            int amountOfRight = 0;
            for(int i = 0; i < data.size(); i++)
            {
                if(data.get(i).getType() == TokenType.LeftParenthes)
                    amountOfLeft++;
                else if(data.get(i).getType() == TokenType.RightParenthes)
                    amountOfRight++;
            }
            if(amountOfLeft > amountOfRight)
            {
                if(data.get(data.size() - 1).getType() == TokenType.Integer ||
                        data.get(data.size() - 1).getType() == TokenType.Float ||
                        data.get(data.size() - 1).getType() == TokenType.Digit ||
                        data.get(data.size() - 1).getType() == TokenType.RightParenthes)
                {
                    return new Token(TokenType.RightParenthes, ")");
                }
            }
            return new Token(TokenType.LeftParenthes, "(");
        }
        else
            return new Token(TokenType.LeftParenthes, "(");
    }

    public String Clear()
    {
        fullData.clear();
        return "";
    }

    public String Backspace()
    {
        fullData.remove(fullData.size() - 1);
        UpdateData();
        String output = "";
        for(int i = 0; i < fullData.size(); i++)
            output = output.concat(fullData.get(i).getValue());
        return output;
    }

    private void UpdateData()
    {
        data.clear();
        for(int i = 0; i < fullData.size(); i++)
        {
            if(fullData.get(i).getType() == TokenType.WhiteSpace)
                continue;
            data.add(fullData.get(i));
            if(data.size() >= 2)
            {
                if(data.get(data.size() - 1).getType() == TokenType.Digit &&
                        (data.get(data.size() - 2).getType() == TokenType.Digit ||
                                data.get(data.size() - 2).getType() == TokenType.Integer))
                {
                    String newValue = "";
                    newValue = newValue.concat(data.get(data.size() - 2).getValue());
                    newValue = newValue.concat(data.get(data.size() - 1).getValue());
                    Token newToken = new Token(TokenType.Integer, newValue);
                    data.remove(data.size() - 1);
                    data.remove(data.size() - 1);
                    data.add(newToken);
                }
                else if(data.get(data.size() - 1).getType() == TokenType.Comma &&
                        (data.get(data.size() - 2).getType() == TokenType.Digit ||
                                data.get(data.size() - 2).getType() == TokenType.Integer))
                {
                    String newValue = "";
                    newValue = newValue.concat(data.get(data.size() - 2).getValue());
                    newValue = newValue.concat(data.get(data.size() - 1).getValue());
                    Token newToken = new Token(TokenType.Float, newValue);
                    data.remove(data.size() - 1);
                    data.remove(data.size() - 1);
                    data.add(newToken);
                }
                else if(data.get(data.size() - 1).getType() == TokenType.Digit
                        && data.get(data.size() - 2).getType() == TokenType.Float)
                {
                    String newValue = "";
                    newValue = newValue.concat(data.get(data.size() - 2).getValue());
                    newValue = newValue.concat(data.get(data.size() - 1).getValue());
                    Token newToken = new Token(TokenType.Float, newValue);
                    data.remove(data.size() - 1);
                    data.remove(data.size() - 1);
                    data.add(newToken);
                }
            }
        }
    }

    public boolean IsCanAddComma()
    {
        if(data.size() > 0)
        {
            return data.get(data.size() - 1).getType() == TokenType.Digit ||
                    data.get(data.size() - 1).getType() == TokenType.Integer;
        }
        return false;
    }

    public String Add(TokenType type, String value)
    {
        fullData.add(new Token(type, value));
        UpdateData();
        String output = "";
        for(int i = 0; i < fullData.size(); i++)
            output = output.concat(fullData.get(i).getValue());
        return output;
    }

    public String getLastError()
    {
        return lastError;
    }

    public String getResult()
    {
        return result;
    }

    public boolean Calculate()
    {
        ArrayList<Token> OPZ_string = new ArrayList<Token>();
        Stack<Token> stack = new Stack<Token>();
        for(int i = 0; i < data.size(); i++)
        {
            Token current = data.get(i);
            if(current.getType() == TokenType.Digit ||
                    current.getType() == TokenType.Integer ||
                    current.getType() == TokenType.Float)
            {
                OPZ_string.add(current);
            }
            else if(current.getType() == TokenType.Sin ||
                    current.getType() == TokenType.Cos ||
                    current.getType() == TokenType.Tan ||
                    current.getType() == TokenType.Log)
            {
                stack.push(current);
            }
            else if(current.getType() == TokenType.LeftParenthes)
            {
                stack.push(current);
            }
            else if(current.getType() == TokenType.RightParenthes)
            {
                Token peekStack;
                while (true)
                {
                    try {
                        peekStack = stack.peek();
                    }catch (EmptyStackException e)
                    {
                        lastError = "Parentheses not matching";
                        return false;
                    }
                    if(peekStack.getType() != TokenType.LeftParenthes)
                    {
                        Token temp = stack.pop();
                        OPZ_string.add(temp);
                    }
                    else
                    {
                        stack.pop();
                        break;
                    }
                }
            }
            else if(current.getType() == TokenType.Addition ||
                    current.getType() == TokenType.Subtraction ||
                    current.getType() == TokenType.Multiplication ||
                    current.getType() == TokenType.Division)
            {
                Token peekStack;
                while (true)
                {
                    try {
                        peekStack = stack.peek();
                    }catch (EmptyStackException e)
                    {
                        break;
                    }
                    if(peekStack.getType() == TokenType.Sin ||
                            peekStack.getType() == TokenType.Cos ||
                            peekStack.getType() == TokenType.Tan ||
                            peekStack.getType() == TokenType.Log)
                    {
                        Token temp = stack.pop();
                        OPZ_string.add(temp);
                    }
                    else if(IsHigherPriority(peekStack.getType(), current.getType()))
                    {
                        Token temp = stack.pop();
                        OPZ_string.add(temp);
                    }
                    else if(IsSamePriority(peekStack.getType(), current.getType()))
                    {
                        Token temp = stack.pop();
                        OPZ_string.add(temp);
                    }
                    else
                    {
                        break;
                    }
                }
                stack.push(current);
            }
            else
            {
                lastError = "Incorrect expression";
                return false;
            }
        }
        while (true)
        {
            Token peekStack;
            try {
                peekStack = stack.peek();
            } catch (EmptyStackException e)
            {
                break;
            }
            if(peekStack.getType() != TokenType.Addition &&
                    peekStack.getType() != TokenType.Subtraction &&
                    peekStack.getType() != TokenType.Multiplication &&
                    peekStack.getType() != TokenType.Division &&
                    peekStack.getType() != TokenType.Sin &&
                    peekStack.getType() != TokenType.Cos &&
                    peekStack.getType() != TokenType.Tan &&
                    peekStack.getType() != TokenType.Log)
            {
                lastError = "Parentheses not matching";
                return false;
            }
            Token temp = stack.pop();
            OPZ_string.add(temp);
        }
        Stack<Token> calcStack = new Stack<Token>();
        for(int i = 0; i < OPZ_string.size(); i++)
        {
            if(OPZ_string.get(i).getType() == TokenType.Integer ||
                    OPZ_string.get(i).getType() == TokenType.Float ||
                    OPZ_string.get(i).getType() == TokenType.Digit)
            {
                calcStack.push(OPZ_string.get(i));
            }
            else if(OPZ_string.get(i).getType() == TokenType.Sin)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(Math.sin(value)));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Cos)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(Math.cos(value)));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Tan)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(Math.tan(value)));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Log)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(Math.log10(value)));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Addition)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value2 = Double.parseDouble(calcStack.pop().getValue());
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value1 = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(value1 + value2));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Subtraction)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value2 = Double.parseDouble(calcStack.pop().getValue());
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value1 = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(value1 - value2));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Multiplication)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value2 = Double.parseDouble(calcStack.pop().getValue());
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value1 = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(value1 * value2));
                calcStack.push(newToken);
            }
            else if(OPZ_string.get(i).getType() == TokenType.Division)
            {
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value2 = Double.parseDouble(calcStack.pop().getValue());
                try {
                    calcStack.peek();
                } catch (EmptyStackException e)
                {
                    lastError = "Incorrect expression";
                    return false;
                }
                double value1 = Double.parseDouble(calcStack.pop().getValue());
                Token newToken = new Token(TokenType.Float, String.valueOf(value1 / value2));
                calcStack.push(newToken);
            }
        }
        if(calcStack.size() != 1)
        {
            lastError = "Incorrect expression";
            return false;
        }
        result = calcStack.peek().getValue();
        data.clear();
        fullData.clear();
        if(result.equals("Infinity"))
        {
            data.add(new Token(TokenType.Infinity, result));
            fullData.add(new Token(TokenType.Infinity, result));
        }
        else if(result.equals("-Infinity"))
        {
            data.add(new Token(TokenType.MinusInfinity, result));
            fullData.add(new Token(TokenType.MinusInfinity, result));
        }
        else if(result.equals("NaN"))
        {
            data.add(new Token(TokenType.NaN, result));
            fullData.add(new Token(TokenType.NaN, result));
        }
        else
        {
            Token resultToken = new Token(TokenType.Float, result);
            for(int i = 0; i < result.length(); i++)
                if(result.charAt(i) == '.')
                {
                    fullData.add(new Token(TokenType.Comma, result.substring(i, i + 1)));
                }
                else
                {
                    fullData.add(new Token(TokenType.Digit, result.substring(i, i + 1)));
                }
            data.add(resultToken);
        }
        return true;
    }

    public boolean IsHigherPriority(TokenType stack, TokenType current)
    {
        if(stack == TokenType.Multiplication || stack == TokenType.Division)
            if(current == TokenType.Addition || current == TokenType.Subtraction)
                return true;
        return false;
    }

    public boolean IsSamePriority(TokenType stack, TokenType current)
    {
        if(stack == TokenType.Addition && current == TokenType.Subtraction)
            return true;
        if(current == TokenType.Addition && stack == TokenType.Subtraction)
            return true;
        if(stack == TokenType.Multiplication && current == TokenType.Division)
            return true;
        if(current == TokenType.Multiplication && stack == TokenType.Division)
            return true;
        return false;
    }
}

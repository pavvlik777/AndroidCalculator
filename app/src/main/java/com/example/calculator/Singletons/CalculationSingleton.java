package com.example.calculator.Singletons;

import com.example.calculator.BuildConfig;
import com.example.calculator.Enums.CalculatorMode;
import com.example.calculator.Enums.TokenType;

import java.util.Observable;
import java.util.Observer;

public class CalculationSingleton extends Observable {
    private static CalculationSingleton instance = null;
    public static CalculationSingleton getInstance()
    {
        if(instance == null)
            instance = new CalculationSingleton();
        return instance;
    }

    public void AddObserver(Observer o){
        this.addObserver(o);
    }

    public void DeleteObserver(Observer o){
        this.deleteObserver(o);
    }

    private String lastError;
    public String getLastError()
    {
        return lastError;
    }

    private String currentData;
    private boolean isDemo;
    private CalculatorMode calculatorMode;
    public CalculatorMode getCalculatorMode()
    {
        return calculatorMode;
    }
    public void setCalculationMode(CalculatorMode value)
    {
        calculatorMode = value;
    }
    public void changeCalculationMode()
    {
        if(calculatorMode == CalculatorMode.BASIC)
            calculatorMode = CalculatorMode.SCIENCE;
        else
            calculatorMode = CalculatorMode.BASIC;
    }

    private CalculationSingleton()
    {
        lastError = "";
        currentData = "";
        isDemo = BuildConfig.FLAVOR.equals("demo");
        calculatorMode = CalculatorMode.BASIC;
    }

    public boolean AddData(TokenType value, String data)
    {
        if(isDemo)
        {
            if(value == TokenType.Sin || value == TokenType.Cos ||
                value == TokenType.Tan || value == TokenType.Log)
            {
                lastError = "Scientific tools not allowed in demo version. Please buy full one.";
                return false;
            }
        }
        if(currentData.length() == 255)
            return true;
        SetCurrentData(currentData.concat(data));
        //TODO
        return true;
    }

    public String GetNextParentheses()
    {
        //TODO
        return "(";
    }

    public void SetCurrentData(String value)
    {
        currentData = value;
        this.setChanged();
        this.notifyObservers();
        this.clearChanged();
    }

    public String GetCurrentData()
    {
        return currentData;
    }

    public void ClearData()
    {
        SetCurrentData("");
    }

    public boolean CalculateResult()
    {
        //TODO
        SetCurrentData("Hello");
        return true;
    }
}

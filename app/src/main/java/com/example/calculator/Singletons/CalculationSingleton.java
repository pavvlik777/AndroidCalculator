package com.example.calculator.Singletons;

import com.example.calculator.BuildConfig;
import com.example.calculator.Enums.CalculatorMode;
import com.example.calculator.Interfaces.IOperation;

public class CalculationSingleton {
    private static CalculationSingleton instance = null;
    public static CalculationSingleton getInstance()
    {
        if(instance == null)
            instance = new CalculationSingleton();
        return instance;
    }

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
        isDemo = BuildConfig.FLAVOR.equals("demo");
        calculatorMode = CalculatorMode.BASIC;
    }

    public void AddOperation(IOperation value)
    {

    }
}

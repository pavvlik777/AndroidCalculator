package com.example.calculator.Models;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.example.calculator.Enums.TokenType;
import com.example.calculator.R;
import com.example.calculator.Singletons.CalculationSingleton;

public class BasicOperationsModel {
    public void Initialize(View view) {
        Button addition_btn = view.findViewById(R.id.addition);
        if(addition_btn != null)
        {
            addition_btn.setOnClickListener(
                    OperationButton(TokenType.Addition, "+")
            );
        }
        Button subtraction_btn = view.findViewById(R.id.subtraction);
        if(subtraction_btn != null)
        {
            subtraction_btn.setOnClickListener(
                    OperationButton(TokenType.Subtraction, "-")
            );
        }
        Button multiplication_btn = view.findViewById(R.id.multiplication);
        if(multiplication_btn != null)
        {
            multiplication_btn.setOnClickListener(
                    OperationButton(TokenType.Multiplication, "X")
            );
        }
        Button division_btn = view.findViewById(R.id.division);
        if(division_btn != null)
        {
            division_btn.setOnClickListener(
                    OperationButton(TokenType.Division, "÷")
            );
        }
    }

    private View.OnClickListener OperationButton(final TokenType type, final String data)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = CalculationSingleton.getInstance().AddData(TokenType.WhiteSpace, " ");
                if(!status) {
                    ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
                }
                else
                {
                    status = CalculationSingleton.getInstance().AddData(type, data);
                    if(!status) {
                        ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
                    }
                    else
                    {
                        status = CalculationSingleton.getInstance().AddData(TokenType.WhiteSpace, " ");
                        if(!status) {
                            ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
                        }
                    }
                }
            }
        };
    }

    private void ShowAlertDialog(View v, String error)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle("Error");
        alertBuilder.setMessage(error);
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}

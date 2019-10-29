package com.example.calculator.Models;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.example.calculator.Enums.TokenType;
import com.example.calculator.R;
import com.example.calculator.Singletons.CalculationSingleton;

public class ScientificOperationsModel {
    public void Initialize(View view) {
        Button sin_btn = view.findViewById(R.id.sin);
        if(sin_btn != null)
        {
            sin_btn.setOnClickListener(
                    OperationButton(TokenType.Sin, "Sin")
            );
        }
        Button cos_btn = view.findViewById(R.id.cos);
        if(cos_btn != null)
        {
            cos_btn.setOnClickListener(
                    OperationButton(TokenType.Cos, "Cos")
            );
        }
        Button tan_btn = view.findViewById(R.id.tan);
        if(tan_btn != null)
        {
            tan_btn.setOnClickListener(
                    OperationButton(TokenType.Tan, "Tan")
            );
        }
        Button log_btn = view.findViewById(R.id.log);
        if(log_btn != null)
        {
            log_btn.setOnClickListener(
                    OperationButton(TokenType.Log, "Log")
            );
        }
    }

    private View.OnClickListener OperationButton(final TokenType type, final String data)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = CalculationSingleton.getInstance().AddData(type, data);
                if(!status) {
                    ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
                }
                else
                {
                    status = CalculationSingleton.getInstance().AddData(TokenType.LeftParenthes, "(");
                    if(!status) {
                        ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
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

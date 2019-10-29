package com.example.calculator.Models;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import com.example.calculator.Enums.TokenType;
import com.example.calculator.R;
import com.example.calculator.Singletons.CalculationSingleton;

import java.util.HashMap;

public class NumbersFragmentModel {
    private HashMap<String, Integer> buttonsMap = new HashMap<>();

    public void Initialize(View view) {
        buttonsMap.put("0", R.id.number_0_btn);
        buttonsMap.put("1", R.id.number_1_btn);
        buttonsMap.put("2", R.id.number_2_btn);
        buttonsMap.put("3", R.id.number_3_btn);
        buttonsMap.put("4", R.id.number_4_btn);
        buttonsMap.put("5", R.id.number_5_btn);
        buttonsMap.put("6", R.id.number_6_btn);
        buttonsMap.put("7", R.id.number_7_btn);
        buttonsMap.put("8", R.id.number_8_btn);
        buttonsMap.put("9", R.id.number_9_btn);
        for(int i = 0; i < buttonsMap.size(); i++)
        {
            Button temp = view.findViewById(buttonsMap.get(Integer.toString(i)));
            if(temp != null)
            {
                temp.setOnClickListener(
                        NumberButton(TokenType.Digit, Integer.toString(i))
                );
            }
        }
        Button comma_btn = view.findViewById(R.id.comma_btn);
        if(comma_btn != null)
        {
            comma_btn.setOnClickListener(
                    NumberButton(TokenType.Comma, ".")
            );
        }
        Button parentheses_btn = view.findViewById(R.id.parentheses_btn);
        if(parentheses_btn != null)
        {
            parentheses_btn.setOnClickListener(  new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = CalculationSingleton.getInstance().GetNextParentheses();
                    TokenType type = TokenType.LeftParenthes;
                    if(data.equals(")"))
                        type = TokenType.RightParenthes;

                    boolean status = CalculationSingleton.getInstance().AddData(type, data);
                    if(!status) {
                        ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
                    }
                }
            });
        }
    }

    private View.OnClickListener NumberButton(final TokenType type, final String data)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = CalculationSingleton.getInstance().AddData(type, data);
                if(!status) {
                    ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
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

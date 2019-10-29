package com.example.calculator.Models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.calculator.Enums.CalculatorMode;
import com.example.calculator.Fragments.BasicOperations;
import com.example.calculator.Fragments.ScientificOperations;
import com.example.calculator.R;
import com.example.calculator.Singletons.CalculationSingleton;

import java.util.Observable;
import java.util.Observer;

public class MainActivityModel implements Observer {
    private TextView resultView;

    public void Initialize(Context context) {
        final FragmentActivity activity = (FragmentActivity) context;
        Button toggleButton = activity.findViewById(R.id.ModeToggle);
        if(toggleButton != null) {
            if (CalculationSingleton.getInstance().getCalculatorMode() != CalculatorMode.BASIC)
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.operations, new ScientificOperations())
                        .commit();
            else
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.operations, new BasicOperations())
                        .commit();

            toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(CalculationSingleton.getInstance().getCalculatorMode() == CalculatorMode.BASIC)
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.operations, new ScientificOperations())
                                .commit();
                    else
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.operations, new BasicOperations())
                                .commit();
                    CalculationSingleton.getInstance().changeCalculationMode();
                }
            });
        }
        //TODO

        Button resultBtn = activity.findViewById(R.id.result_btn);
        resultView = activity.findViewById(R.id.display);
        if(resultView != null)
        {
            resultView.setText(CalculationSingleton.getInstance().GetCurrentData());
        }
        CalculationSingleton.getInstance().AddObserver(this);
        if(resultBtn != null && resultView != null)
        {
            resultBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    boolean status = CalculationSingleton.getInstance().CalculateResult();
                    if(!status)
                    {
                        ShowAlertDialog(v, CalculationSingleton.getInstance().getLastError());
                    }
                }
            });
        }
        Button clearBtn = activity.findViewById(R.id.clear_btn);
        if(clearBtn != null)
        {
            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    CalculationSingleton.getInstance().ClearData();
                }
            });
        }
    }

    public void Destroy(Context context) {
        CalculationSingleton.getInstance().DeleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg){
        resultView.setText(CalculationSingleton.getInstance().GetCurrentData());
    }

    public MainActivityModel() {

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

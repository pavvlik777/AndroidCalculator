package com.example.calculator.Models;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.example.calculator.Enums.CalculatorMode;
import com.example.calculator.Fragments.BasicOperations;
import com.example.calculator.Fragments.ScientificOperations;
import com.example.calculator.R;
import com.example.calculator.Singletons.CalculationSingleton;

public class MainActivityModel {
    public void Initialize(Context context) {
        final FragmentActivity activity = (FragmentActivity) context;
        Button button = activity.findViewById(R.id.ModeToggle);
        if(button != null) {
            if (CalculationSingleton.getInstance().getCalculatorMode() != CalculatorMode.BASIC)
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.operations, new ScientificOperations())
                        .commit();
            else
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.operations, new BasicOperations())
                        .commit();

            button.setOnClickListener(new View.OnClickListener() {
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
    }

    public MainActivityModel() {

    }
}

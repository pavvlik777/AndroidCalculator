package com.example.calculator.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.calculator.Models.MainActivityModel;
import com.example.calculator.R;

public class MainActivity extends FragmentActivity {
    private MainActivityModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new MainActivityModel();
        model.Initialize(this);
    }
}

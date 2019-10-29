package com.example.calculator.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculator.Models.BasicOperationsModel;
import com.example.calculator.R;

public class BasicOperations extends Fragment {
    public BasicOperations() {
        // Required empty public constructor
    }

    private BasicOperationsModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_operations, container, false);
        model = new BasicOperationsModel();
        model.Initialize(view);
        return view;
    }
}

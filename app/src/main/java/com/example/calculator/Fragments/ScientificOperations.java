package com.example.calculator.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculator.Models.ScientificOperationsModel;
import com.example.calculator.R;


public class ScientificOperations extends Fragment {

    public ScientificOperations() {

    }

    private ScientificOperationsModel model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scientific_operations, container, false);
        model = new ScientificOperationsModel();
        model.Initialize(view);
        return view;
    }
}

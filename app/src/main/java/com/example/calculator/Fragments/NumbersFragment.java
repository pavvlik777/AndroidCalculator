package com.example.calculator.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculator.Models.NumbersFragmentModel;
import com.example.calculator.R;

public class NumbersFragment extends Fragment {
    public NumbersFragment() {

    }

    private NumbersFragmentModel model;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_numbers, container, false);
        model = new NumbersFragmentModel();
        model.Initialize(view);
        return view;
    }
}

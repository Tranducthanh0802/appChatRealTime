package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appchatrealtime.databinding.RegisterFragmentBinding;

public class Register_fragment extends Fragment {
    public static Register_fragment newInstance() {

        Bundle args = new Bundle();

        Register_fragment fragment = new Register_fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        RegisterFragmentBinding registerFragmentBinding= RegisterFragmentBinding.inflate(inflater,container,false);

        return registerFragmentBinding.getRoot();
    }
}

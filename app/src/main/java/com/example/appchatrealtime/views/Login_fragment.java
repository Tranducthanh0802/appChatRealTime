package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appchatrealtime.databinding.LoginFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class Login_fragment extends Fragment {
    public static Login_fragment newInstance() {

        Bundle args = new Bundle();

        Login_fragment fragment = new Login_fragment();
        fragment.setArguments(args);
        return fragment;
    }
    private LoginFragmentBinding dataBiding;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        LoginFragmentBinding binding = LoginFragmentBinding.inflate(inflater, container, false);
        //set binding variables here
        return binding.getRoot();

    }
}

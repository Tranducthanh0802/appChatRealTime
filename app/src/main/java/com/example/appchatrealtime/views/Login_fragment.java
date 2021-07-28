package com.example.appchatrealtime.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.LoginFragmentBinding;
import com.example.appchatrealtime.viewmodels.LoginViewModel;


import org.jetbrains.annotations.NotNull;

public class Login_fragment extends Fragment {

    public static Login_fragment newInstance() {

        Bundle args = new Bundle();

        Login_fragment fragment = new Login_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //set binding variables here
        LoginFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.login_fragment, container, false);
        View view = binding.getRoot();
        LoginViewModel loginViewModel=new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(loginViewModel);
        return view;

    }



}

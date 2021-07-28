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

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.LoginFragmentBinding;
import com.example.appchatrealtime.databinding.RegisterFragmentBinding;
import com.example.appchatrealtime.viewmodels.LoginViewModel;
import com.example.appchatrealtime.viewmodels.RegisterViewModel;

public class Register_fragment extends Fragment {
    public static Register_fragment newInstance() {

        Bundle args = new Bundle();

        Register_fragment fragment = new Register_fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        RegisterFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.register_fragment, container, false);
        View view = binding.getRoot();
        RegisterViewModel registerFragmentBinding=new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setRegister(registerFragmentBinding);
        return view;
    }
}

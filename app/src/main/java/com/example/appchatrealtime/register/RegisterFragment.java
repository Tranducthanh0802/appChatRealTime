package com.example.appchatrealtime.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.appchatrealtime.R;
import com.example.appchatrealtime.databinding.RegisterFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {
    RegisterFragmentBinding binding;
    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                inflater, R.layout.register_fragment, container, false);
        View view = binding.getRoot();
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
        binding.txtNotifical1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RegisterViewModel registerFragmentBinding=new ViewModelProvider(getActivity()).get(RegisterViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setRegister(registerFragmentBinding);

        registerFragmentBinding.message.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("Register success")){

                    binding.txtNotifical1.setTextColor(getResources().getColor(R.color.green));
                }else if(s.equals("Email already exist")){
                    binding.txtNotifical1.setTextColor(getResources().getColor(R.color.red));

                }
            }
        });
    }
}

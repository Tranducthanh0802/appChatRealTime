package com.example.appchatrealtime.views;

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
import com.example.appchatrealtime.databinding.LoginFragmentBinding;
import com.example.appchatrealtime.viewmodels.LoginViewModel;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends Fragment {
    private  LoginFragmentBinding binding;
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //set binding variables here
        binding = DataBindingUtil.inflate(
                inflater, R.layout.login_fragment, container, false);
        View view = binding.getRoot();

        return view;

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginViewModel loginViewModel=new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(loginViewModel);
        loginViewModel.isShowMessage.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,TopicFragment.newInstance()).commit();

                }
            }
        });

        loginViewModel.isShowNotifica.observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean) {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("register").add(R.id.frame, RegisterFragment.newInstance()).commit();

                }
            }
        });
    }
}

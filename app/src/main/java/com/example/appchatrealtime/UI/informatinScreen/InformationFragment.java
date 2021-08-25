package com.example.appchatrealtime.UI.informatinScreen;

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
import com.example.appchatrealtime.databinding.InformationFragmentBinding;
import com.example.appchatrealtime.service.model.User;

import org.jetbrains.annotations.NotNull;

public class InformationFragment extends Fragment {
    public static InformationFragment newInstance() {

        Bundle args = new Bundle();

        InformationFragment fragment = new InformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        InformationFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.information_fragment, container, false);
        View view = binding.getRoot();
        InformationViewmodel informationViewmodel = new ViewModelProvider(getActivity()).get(InformationViewmodel.class);
        binding.setViewmodel(informationViewmodel);
        informationViewmodel.getMutableLiveData(getActivity()).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}

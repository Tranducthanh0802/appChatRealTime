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
import com.example.appchatrealtime.databinding.EditFragmentBinding;
import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.viewmodels.InformationViewmodel;

import org.jetbrains.annotations.NotNull;

public class EditFragment extends Fragment {
    public static EditFragment newInstance() {

        Bundle args = new Bundle();

        EditFragment fragment = new EditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        EditFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.edit_fragment, container, false);
        View view = binding.getRoot();
        InformationViewmodel informationViewmodel=new ViewModelProvider(getActivity()).get(InformationViewmodel.class);
        binding.setViewmodel(informationViewmodel);
        informationViewmodel.getMutableLiveData(getActivity()).observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.setUser(user);
            }
        });
        return view;
    }
}

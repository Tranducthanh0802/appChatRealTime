package com.example.appchatrealtime.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.adapter.ChatAdapter;
import com.example.appchatrealtime.adapter.bottomgalleryAdapter;
import com.example.appchatrealtime.databinding.MessageFragmentBinding;
import com.example.appchatrealtime.model.Chat;
import com.example.appchatrealtime.model.firebase;
import com.example.appchatrealtime.viewmodels.ChatViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private static final int STORAGE_PERMISSION_CODE = 1;
    ChatAdapter chatAdapter;
    Chat chat;
    firebase fb =new firebase();
    DatabaseReference databaseReference=fb.getDatabaseReference();
    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        MessageFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.message_fragment, container, false);
        View view = binding.getRoot();
        ChatViewModel loginViewModel=new ViewModelProvider(getActivity()).get(ChatViewModel.class);

        chat=new Chat();
       // binding.setLifecycleOwner(getActivity());
        binding.setViewmodel(loginViewModel);

        loginViewModel.getArrayListLiveData().observe(getActivity(), new Observer<ArrayList<ChatViewModel>>() {
            @Override
            public void onChanged(ArrayList<ChatViewModel> chatViewModels) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
               chatAdapter=new ChatAdapter(chatViewModels,getActivity());
                binding.recyclerviewmessage.setAdapter(chatAdapter);
                binding.recyclerviewmessage.setLayoutManager(mLayoutManager);
                binding.recyclerviewmessage.scrollToPosition(chatViewModels.size()-1);

           }
        });
        binding.storageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.recBot.getVisibility()==View.GONE) {
                    binding.storageImg.setImageResource(R.drawable.photoblue);
                    binding.recBot.setVisibility(View.VISIBLE);
                    ChatViewModel chatViewModel = new ChatViewModel(getActivity());
                    chatViewModel.getArrayListGalleryLiveData().observe(getActivity(), new Observer<ArrayList<String>>() {
                        @Override
                        public void onChanged(ArrayList<String> strings) {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                            bottomgalleryAdapter galleryAdapter = new bottomgalleryAdapter(strings, getActivity());
                            binding.recBot.setAdapter(galleryAdapter);
                            binding.recBot.setLayoutManager(gridLayoutManager);


                        }
                    });
                }else{
                    binding.storageImg.setImageResource(R.drawable.photo);
                    binding.recBot.setVisibility(View.GONE);
                }


            }
        });
        loginViewModel.getLinkPhotoLiveData().observe(getActivity(), new Observer<Chat>() {
            @Override
            public void onChanged(Chat s) {

                Glide.with(getActivity()).load(s.getLinkphoto()).placeholder(R.drawable.personal1).into(binding.imgAvataTop);
                binding.namefull.setText(s.getStatus()+"");
            }
        });
        checkpermission();
        return view;
    }

    public void loadSavedImages() {
        StorageReference root = FirebaseStorage.getInstance().getReference();
        StorageReference fileref=root.child("Image/");
        Uri file = Uri.fromFile(new File("/storage/emulated/0/DCIM/Camera/IMG_20210806_084216.jpg"));

        fileref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("abc", "onSuccess: "+uri.toString());
                    }
                });
                Log.d("abc", "onSuccess: ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("abc", "onFailure: "+e.toString());
            }
        });


    }
    void checkpermission() {
        if (ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(getActivity(), "you have already granted this permission", Toast.LENGTH_SHORT).show();
        }else requestStoragePermission();
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Permission need")
                    .setTitle("that permission need because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(),new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },STORAGE_PERMISSION_CODE);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(STORAGE_PERMISSION_CODE==requestCode) {
            if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Permission Grant", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(getActivity(), "Permission denned", Toast.LENGTH_SHORT).show();
        }
    }
}

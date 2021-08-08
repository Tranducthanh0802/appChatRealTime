package com.example.appchatrealtime.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;

public class Chat extends ViewModel {

    private String linkphoto;
    private String nameFullf;
    private MutableLiveData<String> message=new MutableLiveData<>();

    public Chat(String linkphoto, String nameFullf) {
        this.linkphoto = linkphoto;
        this.nameFullf = nameFullf;
    }

    public Chat() {
    }

    public Chat(String linkphoto) {
        this.linkphoto = linkphoto;
    }

    public String getLinkphoto() {

        return linkphoto;
    }
    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }

    public void setLinkphoto(String linkphoto) {
        this.linkphoto = linkphoto;
    }

    public String getStatus() {
        return nameFullf;
    }

    public void setStatus(String status) {
        this.nameFullf = status;
    }



}

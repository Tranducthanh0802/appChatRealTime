package com.example.appchatrealtime.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;

public class Chat extends BaseObservable {

    private String linkphoto;
    private String nameFullf;


    public Chat() {
    }

    public Chat(String linkphoto) {
        this.linkphoto = linkphoto;
    }
    @Bindable
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

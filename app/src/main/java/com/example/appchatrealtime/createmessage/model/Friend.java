package com.example.appchatrealtime.createmessage.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;

public class Friend  {
    private String link;
    private String id;

    public Friend(String link, String id) {
        this.link = link;
        this.id = id;

    }

    public Friend() {
    }

    public String getLink() {
        return link;
    }
    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

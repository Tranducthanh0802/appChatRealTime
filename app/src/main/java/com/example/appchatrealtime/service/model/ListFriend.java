package com.example.appchatrealtime.service.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;

public class ListFriend implements Comparable<ListFriend>{
    private String linkPhoto;
    private String nameFull;
    private Boolean isFriend;
    private String stickHeader;
    private String id;

    public ListFriend() {
    }
    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }
    public ListFriend(String linkPhoto, String nameFull, Boolean isFriend, String stickHeader) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.isFriend = isFriend;
        this.stickHeader = stickHeader;
    }

    public ListFriend(String linkPhoto, String nameFull) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.stickHeader= getSticky(nameFull.toUpperCase());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }

    public String getStickHeader() {
        return stickHeader.toUpperCase().substring(0,1);
        //charAt(0)
    }

    public void setStickHeader(String stickHeader) {
        this.stickHeader = stickHeader;
    }

    @Override
    public int compareTo(ListFriend o) {
        String[] arrA=nameFull.split(" ");
        String[] arrB= o.getNameFull().split(" ");
        return arrA[arrA.length-1].toUpperCase().compareTo(arrB[arrB.length-1].toUpperCase());
    }
    private String getSticky(String s){
        String[] a=s.split(" ");
        return a[a.length-1];

    }
}

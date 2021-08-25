package com.example.appchatrealtime.service.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TopicItem implements Comparable<TopicItem> {
    private String linkPhoto;
    private String nameSend;
    private Message messages;
    private Boolean isBold;
    private String diem="0";
    private String paragraph;
    private String idGuest;
    private Boolean isShow;
    private String notifical;


    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }
    public TopicItem() {
    }

    public TopicItem(String linkPhoto, String nameSend, Message messages) {
        this.linkPhoto = linkPhoto;
        this.nameSend = nameSend;
        this.messages = messages;
        this.isShow=true;
    }

    public String getNotifical() {
        return notifical;
    }

    public void setNotifical(String notifical) {
        this.notifical = notifical;
    }

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public TopicItem(String linkPhoto, String nameSend) {
        this.linkPhoto = linkPhoto;
        this.nameSend = nameSend;
    }

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
    }

    public String getDiem() {
        return diem;
    }

    public void setDiem(String diem) {
        this.diem = diem;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public Boolean getBold() {
        return isBold;
    }

    public void setBold(Boolean bold) {
        isBold = bold;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getNameSend() {
        if(nameSend.length()>10){
            char[] arr = nameSend.toCharArray();
            char[] arr1=new char[13];
            for(int i=0;i<10;i++){
                arr1[i]=arr[i];
            }
            arr1[10]='.';
            arr1[11]='.';
            arr1[12]='.';

            return String.valueOf(arr1);
        }
        return nameSend;

    }

    public void setNameSend(String nameSend) {

        this.nameSend = nameSend;
    }

    public Message getMessages() {
        return messages;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }

    @Override
    public int compareTo(TopicItem o) {
        String[] arrA=getMessages().getTime().split(" ");
        String[] arrB=o.getMessages().getTime().split(" ");

        if (getMessages().getTime() == null || o.getMessages().getTime() == null)
            return 0;
        String stringDate = "22/01/2016";
        Date b = new Date();
        Date a = new Date();
        Date c = new Date();
        Date d = new Date();

        try {
            a = new SimpleDateFormat("dd/MM/yyyy").parse(arrA[0]);
            b = new SimpleDateFormat("dd/MM/yyyy").parse(arrB[0]);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (a.compareTo(b)==0){

            try {
                c=new SimpleDateFormat("HH:mm").parse(arrA[1]);
                d=new SimpleDateFormat("HH:mm").parse(arrB[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d.compareTo(c);

        }
        return b.compareTo(a);
    }
}

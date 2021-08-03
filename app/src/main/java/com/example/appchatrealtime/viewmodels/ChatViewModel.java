package com.example.appchatrealtime.viewmodels;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel
{
    private String linkPhoto;
    private Boolean status;
    private String nameFull;
    private String messag;
    private String time;
    private String id_Sender;
    private String id_receive;
    private ArrayList<ChatViewModel> arrayList=new ArrayList<>();
    private MutableLiveData<ArrayList<ChatViewModel>> arrayListLiveData= new MutableLiveData<>();

    public ChatViewModel() {
    }

    public ChatViewModel( String time,String linkPhoto,String namefull,String message,Boolean status) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.messag = message;
        this.time = time;
        id_receive="1";
        id_Sender="0";
        this.status=status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }
    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public String getMessag() {
        return messag;
    }

    public void setMessag(String messag) {
        this.messag = messag;
    }

    public ArrayList<ChatViewModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ChatViewModel> arrayList) {
        this.arrayList = arrayList;
    }

    public MutableLiveData<ArrayList<ChatViewModel>> getArrayListLiveData() {
        firebase fb =new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                linkPhoto= (String) snapshot.child("User").child("0").child("linkPhoto").getValue();
                nameFull= (String) snapshot.child("User").child("0").child("fullName").getValue();

                for (int i=0;i<snapshot.child("ListMessage").getChildrenCount();i++
                     ) {
                    if(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue()).equals("1") && String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue()).equals("0")){

                            String getmessage= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                            String[] arrtime =getTime(getmessage);
                            String[] arr=getMessage(getmessage);
                            String[] status=getStatus(getmessage);
                            ChatViewModel chatViewModel;
                            for(int j=0;j<arr.length;j++) {
                                if(status[j].equals('r')){
                                   chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],true);

                                }else {
                                     chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull, arr[j], false);
                                }
                                arrayList.add(chatViewModel);
                                arrayListLiveData.setValue(arrayList);

                            }
                            break;
                    }
                }
                arrayListLiveData.setValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return arrayListLiveData;
    }

    public void setArrayListLiveData(MutableLiveData<ArrayList<ChatViewModel>> arrayListLiveData) {
        this.arrayListLiveData = arrayListLiveData;
    }
    private  String[] getTime(String message){
        String[] arrSection=new String[message.split("--").length+1];
        String[] arrTime=new String[3];
        arrSection=message.split("--");
        for(int i=0;i<message.split("--").length;i++){
            arrTime=arrSection[i].split("-");
            arrSection[i]=arrTime[1];
        }
        return arrSection;
    }
    private  String[] getMessage(String message){
        String[] arrSection=new String[message.split("--").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];
        arrSection=message.split("--");

        for(int i=0;i<message.split("--").length;i++){
            arrTime=arrSection[i].split("-");
            arrCategory=arrTime[0].split("@@");
            arrSection[i]=arrCategory[0];
        }
        return arrSection;
    }
    private String[] getStatus(String message){
        String[] arrSection=new String[message.split("--").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];
        arrSection=message.split("--");

        for(int i=0;i<message.split("--").length;i++){
            arrTime=arrSection[i].split("-");
            arrCategory=arrTime[0].split("@@");
            arrSection[i]=arrCategory[1];
        }
        return arrSection;
    }

//    private boolean processMessage(String tinnhan) {
//        String[] arrSection=new String[tinnhan.split("--").length+1];
//        String[] arrTime=new String[3];
//        String[] arrCategory=new String[3];
//
//        arrSection=tinnhan.split("--");
//        HashMap<String,String> a=new HashMap<>();
//        arrTime=arrSection[arrSection.length-1].split("-");
//        arrCategory=arrTime[0].split("@@");
//        message.setTime(arrTime[1]);
//        message.setMessage(arrCategory[0]);
//
//        if(arrCategory[1].equals("s")){
//            return false;
//        }else {
//            return true;
//        }
//
//    }
}

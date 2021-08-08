package com.example.appchatrealtime.viewmodels;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.Chat;
import com.example.appchatrealtime.model.firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatViewModel extends ViewModel
{
    private String linkPhoto;
    private Boolean status;
    private String nameFull;
    private String messag;
    private String time;
    private String id_Sender;
    private String id_receive;
    private Boolean isshowavatar;
    private Boolean isshowtime;
    private String linkPhotoGallery;
    private Boolean isImage;
    private ArrayList<ChatViewModel> arrayList=new ArrayList<>();
    private MutableLiveData<ArrayList<ChatViewModel>> arrayListLiveData= new MutableLiveData<>();
    private MutableLiveData<Chat> linkPhotoLiveData= new MutableLiveData<>();
    private ArrayList<String> listGallery=new ArrayList<>();
    private MutableLiveData<ArrayList<String>> arrayListGalleryLiveData= new MutableLiveData<>();
    private Context context;

    public Boolean getImage() {
        return isImage;
    }

    public void setImage(Boolean image) {
        isImage = image;
    }

    public ChatViewModel(Context context) {
        this.context = context;
    }

    public Boolean getIsshowavatar() {
        return isshowavatar;
    }

    public void setIsshowavatar(Boolean isshowavatar) {
        this.isshowavatar = isshowavatar;
    }

    public Boolean getIsshowtime() {
        return isshowtime;
    }

    public void setIsshowtime(Boolean isshowtime) {
        this.isshowtime = isshowtime;
    }

    public String getLinkPhotoGallery() {
        return linkPhotoGallery;
    }

    public void setLinkPhotoGallery(String linkPhotoGallery) {
        this.linkPhotoGallery = linkPhotoGallery;
    }

    public ChatViewModel() {
    }

    public ChatViewModel( String time,String linkPhoto,String namefull,String message,Boolean status,Boolean isshowavatar, Boolean isshowtime ,Boolean isImage) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.messag = message;
        this.time = time;
        id_receive="1";
        id_Sender="0";
        this.status=status;
        this.isshowavatar=isshowavatar;
        this.isshowtime=isshowtime;
        this.isImage=isImage;
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

    public MutableLiveData<Chat> getLinkPhotoLiveData() {
        firebase fb =new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                linkPhoto= (String) snapshot.child("User").child("0").child("linkPhoto").getValue();
                nameFull= (String) snapshot.child("User").child("0").child("fullName").getValue();
                Chat chat=new Chat(linkPhoto,nameFull);
                linkPhotoLiveData.setValue(chat);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return linkPhotoLiveData;
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
                            ChatViewModel chatViewModel = new ChatViewModel();
                            for(int j=0;j<arr.length;j++) {
                                isImage=getImage(arr[j]);
                                if(status[j].trim().equals("r")){
                                    if((j+1)==status.length || !status[j+1].trim().equals("r")){
                                        chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],true,true,true,isImage);

                                    }else {
                                        chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],true,true,false,isImage);

                                    }
                                }else {
                                    if(j==0){
                                        if((j+1)==status.length || status[j+1].trim().equals("r")){
                                            chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],false,true,true,isImage);

                                        }else {
                                            chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],false,true,false,isImage);

                                        }
                                    }else {
                                        if(status[j-1].trim().equals("r")){
                                            if((j+1)==status.length ||status[j+1].trim().equals("r")){
                                                chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],false,true,true,isImage);

                                            }else{
                                                chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull,arr[j],false,true,false,isImage);

                                            }
                                        }else {
                                            if((j+1)==status.length|| status[j+1].trim().equals("r")) {
                                                chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull, arr[j], false, false, true,isImage);
                                            }else  chatViewModel = new ChatViewModel(arrtime[j], linkPhoto, nameFull, arr[j], false, false, false,isImage);
                                        }
                                    }
                                     
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
        String[] arrSection=new String[message.split("@@@@@").length+1];
        String[] arrTime=new String[3];
        arrSection=message.split("@@@@@");
        for(int i=0;i<message.split("@@@@@").length;i++){
            arrTime=arrSection[i].split("@@@@");
            arrSection[i]=arrTime[1];
        }
        return arrSection;
    }
    private  String[] getMessage(String message){
        String[] arrSection=new String[message.split("@@@@@").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];
        arrSection=message.split("@@@@@");

        for(int i=0;i<message.split("@@@@@").length;i++){
            arrTime=arrSection[i].split("@@@@");
            arrCategory=arrTime[0].split("@@@");
            arrSection[i]=arrCategory[0];
        }
        return arrSection;
    }
    private Boolean getImage(String message){
        String[] arrImage=new String[3];
        arrImage=message.split("@@");
        if(arrImage.length<2){
            return false;
        }else {
            return true;
        }
    }
    private String[] getStatus(String message){
        String[] arrSection=new String[message.split("@@@@@").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];
        arrSection=message.split("@@@@@");

        for(int i=0;i<message.split("@@@@@").length;i++){
            arrTime=arrSection[i].split("@@@@");
            arrCategory=arrTime[0].split("@@@");
            arrSection[i]=arrCategory[1];
        }
        return arrSection;
    }

    public MutableLiveData<ArrayList<String>> getArrayListGalleryLiveData() {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,null, null);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            listGallery.add(absolutePathOfImage);

        }
        cursor.close();
        arrayListGalleryLiveData.setValue(listGallery);
        return arrayListGalleryLiveData;
    }
    StringBuilder sb;
    public void onClick(){
        StorageReference root = FirebaseStorage.getInstance().getReference();
        StorageReference fileref=root.child("Image/"+System.currentTimeMillis());
        Uri file = Uri.fromFile(new File(linkPhotoGallery));
        firebase fb=new firebase();

        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               sb=new StringBuilder((String) snapshot.child("ListMessage").child(String.valueOf(0)).child("message").getValue());
                fileref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                sb.append(uri+"@@image@@@r@@@@"+gettime()+"@@@@@");
                            }
                        });

                      }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d("abc", "onFailure: "+e.toString());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        if(sb!=null){
            databaseReference.child("ListMessage").child("0").child("message").setValue(sb+"");
        }
        databaseReference.addValueEventListener(postMessage);


    }
    String gettime(){
        DateFormat df = new SimpleDateFormat("HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
    public final MutableLiveData<String> textEdit=new MutableLiveData<>();
    public void onclickSend(){
        firebase fb =new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        if(!TextUtils.isEmpty(textEdit.getValue())){
            ValueEventListener postMessage=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    sb=new StringBuilder((String) snapshot.child("ListMessage").child(String.valueOf(0)).child("message").getValue());
                    sb.append(textEdit.getValue()+"@@@r@@@@"+gettime()+"@@@@@");
//                    databaseReference.child("ListMessage").child("0").child("message").setValue(sb+"");
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            };
            if(sb!=null){
                databaseReference.child("ListMessage").child("0").child("message").setValue(sb+"");
                textEdit.setValue("");
            }
            databaseReference.addValueEventListener(postMessage);

        }
    }


}

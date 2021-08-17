package com.example.appchatrealtime.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.Chat;
import com.example.appchatrealtime.model.ListChat;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.User;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
    private FragmentActivity context;
    private List<ListChat> listChats;
    private List<User> listUser;

    public Boolean getImage() {
        return isImage;
    }

    public void setImage(Boolean image) {
        isImage = image;
    }

    public void setContext(FragmentActivity context) {
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


    public MutableLiveData<Chat> getLinkPhotoLiveData(FragmentActivity context) {
        firebase fb =new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        String id_Guest=sharedPreferencesModel.getString("id_guest","");
        String idHost=sharedPreferencesModel.getString("idHost","");


        listUser=new ArrayList<>();
        DatabaseReference databaseReference=fb.getDatabaseReference().child("User");
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listUser.clear();
                for (int i = 0; i < snapshot.getChildrenCount(); i++
                ) {
                    listUser.add(snapshot.child(String.valueOf(i)).getValue(User.class));

                }
                linkPhoto= getLinkPhoto(id_Guest,idHost);
                nameFull= getName(id_Guest,idHost);
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

    public MutableLiveData<ArrayList<ChatViewModel>> getArrayListLiveData(FragmentActivity context) {
        firebase fb =new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        String idHost=sharedPreferencesModel.getString("idHost","");
        String id_Guest=sharedPreferencesModel.getString("id_guest","");
        String[] arr =TransitionArr(id_Guest);


        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList.clear();
                listUser=new ArrayList<>();
                listChats=new ArrayList<>();
                for (int i = 0; i < snapshot.child("User").getChildrenCount(); i++
                ) {
                    listUser.add(snapshot.child("User").child(String.valueOf(i)).getValue(User.class));

                }
                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++
                ) {
                    //    arr.add(new Invite_User(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue()),String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue())));
                    int count =snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue(Integer.class);
                    String idGuest= AddId((String) snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue().toString());
                    String[] arridHost=  snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue().toString().split(",");
                    String idHost=arridHost[0];
                    String message= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                    Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    listChats.add(new ListChat(count,idHost,idGuest,message,status));
                }
                for (int i=0;i<listChats.size();i++
                     ) {
                    if(listChats.get(i).getAddId().equals(id_Guest))
                    {
                            String getmessage=listChats.get(i).getMessage();
                            if(!getmessage.equals("")){
                            String[] arrtime =getTime(getmessage);
                            String[] arr=getMessage(getmessage);
                            String[] status=getStatus(getmessage);
                            ChatViewModel chatViewModel = new ChatViewModel();
                            for(int j=0;j<arr.length;j++) {

                                isImage = getImage(arr[j]);
                                if (status[j].trim().equals(idHost)) {
                                    if ((j + 1) == status.length || !status[j + 1].trim().equals(idHost)) {

                                        chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(idHost)).getLinkPhoto(), listUser.get(Integer.parseInt(idHost)).getFullName(), arr[j], true, true, true, isImage);

                                    } else {
                                        chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(idHost)).getLinkPhoto(), listUser.get(Integer.parseInt(idHost)).getFullName(), arr[j], true, true, false, isImage);

                                    }
                                } else {
                                    if (j == 0) {
                                        if ((j + 1) == status.length || status[j + 1].trim().equals(idHost)) {

                                            chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(status[i])).getLinkPhoto(), listUser.get(Integer.parseInt(status[i])).getFullName(), arr[j], false, true, true, isImage);

                                        } else {
                                            chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(status[i])).getLinkPhoto(), listUser.get(Integer.parseInt(status[i])).getFullName(), arr[j], false, true, false, isImage);

                                        }
                                    } else {
                                        if (status[j - 1].trim().equals(idHost)) {
                                            if ((j + 1) == status.length || status[j + 1].trim().equals(idHost)) {
                                                chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(status[i])).getLinkPhoto(), listUser.get(Integer.parseInt(status[i])).getFullName(), arr[j], false, true, true, isImage);

                                            } else {
                                                chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(status[i])).getLinkPhoto(), listUser.get(Integer.parseInt(status[i])).getFullName(), arr[j], false, true, false, isImage);

                                            }
                                        } else {
                                            if ((j + 1) == status.length || status[j + 1].trim().equals(idHost)) {
                                                chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(status[i])).getLinkPhoto(), listUser.get(Integer.parseInt(status[i])).getFullName(), arr[j], false, false, true, isImage);

                                            } else

                                                chatViewModel = new ChatViewModel(arrtime[j].split(" ")[1], listUser.get(Integer.parseInt(status[i])).getLinkPhoto(), listUser.get(Integer.parseInt(status[i])).getFullName(), arr[j], false, false, false, isImage);
                                        }
                                    }

                                }
                                arrayList.add(chatViewModel);
                            }
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
    int vitri;
    public void onClick(View view){
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel((FragmentActivity) view.getContext());
        String id_Guest=sharedPreferencesModel.getString("id_guest","");
        StorageReference root = FirebaseStorage.getInstance().getReference();
        StorageReference fileref=root.child("Image/"+System.currentTimeMillis());
        Uri file = Uri.fromFile(new File(linkPhotoGallery));
        firebase fb=new firebase();
        String idHost=sharedPreferencesModel.getString("idHost","");
        Log.d("abc", "onDataChange12: "+ idHost);

        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listChats=new ArrayList<>();
                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++
                ) {
                    //    arr.add(new Invite_User(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue()),String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue())));
                    int count =snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue(Integer.class);
                    String idGuest= AddId((String) snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue().toString());
                    String[] arridHost=  snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue().toString().split(",");
                    String idHost=arridHost[0];
                    String message= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                    Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    listChats.add(new ListChat(count,idHost,idGuest,message,status));
                }
                for(int i=0;i<listChats.size();i++){
                    if(listChats.get(i).getAddId().equals(id_Guest)){
                        sb=new StringBuilder(listChats.get(i).getMessage());
                        vitri=i;
                        break;
                    }
                }
                fileref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                sb.append(uri+"@@image@@@"+idHost+"@@@@"+gettime()+"@@@@@");
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
            databaseReference.child("ListMessage").child(String.valueOf(vitri)).child("message").setValue(sb+"");
        }
        databaseReference.addValueEventListener(postMessage);


    }
    public Boolean CheckPermission(){
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
//        Toast.makeText(getActivity(), "you have already granted this permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions((Activity) context,new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },101);
        return true;
        }else return false;
    }
    String gettime(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }
    private final MutableLiveData<String> textEdit=new MutableLiveData<>();
    private final MutableLiveData<Boolean> isShow=new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsShow() {
        return isShow;
    }

    public MutableLiveData<String> getTextEdit() {
        return textEdit;
    }

    public void onclickSend(View view){
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel((FragmentActivity) view.getContext());
        String id_Guest=sharedPreferencesModel.getString("id_guest","");
        String[] arr =TransitionArr(id_Guest);
        String idHost=sharedPreferencesModel.getString("idHost","");
        firebase fb =new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        if(!TextUtils.isEmpty(textEdit.getValue())){
            ValueEventListener postMessage=new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    listChats=new ArrayList<>();
                    for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++
                    ) {
                        //    arr.add(new Invite_User(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue()),String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue())));
                        int count =snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue(Integer.class);
                        String idGuest= AddId((String) snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue().toString());
                        String[] arridHost=  snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue().toString().split(",");
                        String idHost=arridHost[0];
                        String message= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                        Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                        listChats.add(new ListChat(count,idHost,idGuest,message,status));
                    }
                    for(int i=0;i<listChats.size();i++){
                        if(listChats.get(i).getAddId().equals(id_Guest)){
                            sb=new StringBuilder(listChats.get(i).getMessage());
                            vitri=i;
                            break;
                        }
                    }
                     sb.append(textEdit.getValue()+"@@@"+idHost+"@@@@"+gettime()+"@@@@@");
//                    databaseReference.child("ListMessage").child("0").child("message").setValue(sb+"");
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            };
            if(sb!=null){
                databaseReference.child("ListMessage").child(String.valueOf(vitri)).child("message").setValue(sb+"");
               textEdit.setValue("");
            }
            databaseReference.addValueEventListener(postMessage);

        }
    }
    private void CreateArrListMessage(){
        firebase fb =new firebase();
        listChats=new ArrayList<>();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              //  arr.clear();
                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++
                ) {
                //    arr.add(new Invite_User(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue()),String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue())));
                    int count =snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue(Integer.class);
                    String idGuest= AddId((String) snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue().toString());

                    String[] arridHost=  snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue().toString().split(",");
                    String idHost=arridHost[0];
                    String message= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                    Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    listChats.add(new ListChat(count,idHost,idGuest,message,status));
                }

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        };
        databaseReference.addValueEventListener(postMessage);

    }
    private void CreateArrUser(){
        firebase fb =new firebase();
        listUser=new ArrayList<>();
        DatabaseReference databaseReference=fb.getDatabaseReference().child("User");
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listUser.clear();
                for (int i = 0; i < snapshot.getChildrenCount(); i++
                ) {
                        listUser.add(snapshot.child(String.valueOf(i)).getValue(User.class));

                }

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);


    }
    String AddId(String s){
        List<String> list= Arrays.asList(s.split(","));
        Collections.sort(list);
        String chuoi="";
        for (int i=0;i<list.size();i++){
            chuoi+=list.get(i)+",";
        }
        return chuoi;

    }
    private String[] TransitionArr(String s){
        String[] arr=s.split(",");
        return arr;
    }
    private String getLinkPhoto(String s,String idHost){

        String[] arr=s.split(",");
        if(arr.length == 2){
            for(int i=0;i<arr.length;i++){
                if(!arr[i].equals(idHost)){
                    return listUser.get(Integer.parseInt(arr[i])).getLinkPhoto();
                }
            }
        }
         return "";
    }
    private String getName(String s,String idHost){

        String[] arr=s.split(",");
        if(arr.length == 2){
            for(int i=0;i<arr.length;i++){
                if(!arr[i].equals(idHost)){
                    return listUser.get(Integer.parseInt(arr[i])).getFullName();
                }
            }
        }else {
            String chuoi="";
            for(int i=0;i<arr.length;i++){
                if(!arr[i].equals(idHost)){
                    String[] arr1=listUser.get(Integer.parseInt(arr[i])).getFullName().toString().split(" ");
                    chuoi+= arr1[arr1.length-1];
                    if(i<arr.length-1) chuoi+=",";
                }
            }
            return chuoi;
        }
         return "";
    }
//    private String getLinkPhoto(String s,String idHost){
//
//        String[] arr=s.split(",");
//        if(arr.length==1)
//            for(int i=0;i<arr.length;i++){
//                if(arr[i]!=idHost){
//                    return listUser.get(Integer.parseInt(arr[i])).getLinkPhoto();
//                }
//            }
//        return "";
//    }
//    private String getName(String s,String idHost){
//        String[] arr=s.split(",");
//        String chuoi="";
//        for(int i=0;i<arr.length;i++){
//
//            if(arr.length>1){
//                String[] arr1=listUser.get(Integer.parseInt(arr[i])).getFullName().toString().split(" ");
//                chuoi+= arr1[arr1.length-1];
//                if(i<arr.length-1) chuoi+=",";
//            }else {
//                chuoi=listUser.get(Integer.parseInt(arr[i])).getFullName().toString();
//            }
//        }
//        return chuoi;
//
//    }


}

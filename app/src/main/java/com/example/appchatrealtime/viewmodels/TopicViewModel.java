package com.example.appchatrealtime.viewmodels;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.model.Message;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.TopicItem;
import com.example.appchatrealtime.model.User;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TopicViewModel extends ViewModel {
//    public String linkPhoto="";
//    public String nameSend="";
//    public String tn="";
//    public String thoigian="";
//    public Message message;
    private String id_sender;

    private TopicItem topicItem;
    private ArrayList<User> listUser;
    private MutableLiveData<String> transitionData=new MutableLiveData<>();
    MutableLiveData<ArrayList<TopicItem>> arrayListMutableLiveData=new MutableLiveData<>();
    private ArrayList<TopicItem> arrayList;
    private MutableLiveData<Boolean> isChoose=new MutableLiveData<>();

    public MutableLiveData<String> getTransitionData() {
        return transitionData;
    }

    public void setTransitionData(String s) {
        transitionData.setValue(s);
    }

    public TopicViewModel() {
    }



    @BindingAdapter("android:typeface")
    public static void setTypeface(TextView v, String style) {
        switch (style) {
            case "bold":
                v.setTypeface(null, Typeface.BOLD);
                break;
            default:
                v.setTypeface(null, Typeface.NORMAL);
                break;
        }
    }



    public TopicViewModel(TopicItem topicItem) {
        this.topicItem =   topicItem;

    }

    public MutableLiveData<ArrayList<TopicItem>> getArrayListMutableLiveData(FragmentActivity context) {
        arrayList = new ArrayList<>();
        topicItem=new TopicItem();
         initdata(context);
        topicItem.setBold(false);
        //arrayListMutableLiveData.setValue(arrayList);
        return arrayListMutableLiveData;
    }
    private MutableLiveData<ArrayList<TopicItem>> initdata(FragmentActivity context) {
        firebase fb =new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        String idHost=sharedPreferencesModel.getString("idHost","");
        DatabaseReference databaseReference =fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList.clear();
                listUser=new ArrayList<>();
                for (int i = 0; i < snapshot.child("User").getChildrenCount(); i++
                ) {
                    listUser.add(snapshot.child("User").child(String.valueOf(i)).getValue(User.class));

                }
                TopicItem item=new TopicItem();
                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++){
                    Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    String id_re= AddId(String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue()+String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue())));
                    if(Check(id_re,idHost) && !String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue()).equals("")) {

                        String tinnhan= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                        if(!processMessage(tinnhan)&& !status){
                            item.setBold(true);
                        }else{
                            item.setBold(false);
                        };
                        item.setDiem( String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue()));
//                        String id= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue());
                        item = new TopicItem(
                               getLinkPhoto(id_re,idHost),
                                getName(id_re,idHost),
                                topicItem.getMessages()
                        );
                        item.setIdGuest(id_re);
                        arrayList.add(item);

                    }
                }
                if(arrayList.size()!=0) {
                    Collections.sort(arrayList);
                }
               arrayListMutableLiveData.setValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        setNotificaion();
        return arrayListMutableLiveData;
    }

    private void setNotificaion() {

    }

    private boolean processMessage(String tinnhan) {
        if(tinnhan.equals("")){
            return false;
        }
        String[] arrSection=new String[tinnhan.split("--").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];
        String[] arrImage=new String[3];

        Message message=new Message();
        arrSection=tinnhan.split("@@@@@");
         arrTime=arrSection[arrSection.length-1].split("@@@@");
         arrCategory=arrTime[0].split("@@@");
         arrImage=arrCategory[0].split("@@");
         if(arrImage.length>=2){
             message.setMessage("[image]");
         }else  message.setMessage(arrCategory[0]);

        message.setTime(arrTime[1]);
        topicItem.setMessages(message);

        if(arrCategory[1].equals("s")){
            return false;
        }else {
            return true;
        }

    }
    void ngay(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

    }

    public MutableLiveData<Boolean> getIsChoose() {
        return isChoose;
    }

    public void transtionMessage(){
       // if(isChoose.getValue()==Boolean.FALSE)
        isChoose.setValue(Boolean.TRUE);
        //else isChoose.setValue(Boolean.FALSE);
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
    String AddId(String s){
        List<String> list= Arrays.asList(s.split(","));
        Collections.sort(list);
        String chuoi="";
        for (int i=0;i<list.size();i++){
            chuoi+=list.get(i)+",";
        }
        return chuoi;

    }
    Boolean Check(String s,String idHost) {
        String[] arr = s.split(",");
        Log.d("abc", "ngay: "+s+idHost);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].trim().equals(idHost)) {
                return true;
            }
        }
        return false;
    }



}

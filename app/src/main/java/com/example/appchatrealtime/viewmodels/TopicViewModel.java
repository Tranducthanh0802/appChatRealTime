package com.example.appchatrealtime.viewmodels;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.model.Message;
import com.example.appchatrealtime.model.TopicItem;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class TopicViewModel extends ViewModel {
//    public String linkPhoto="";
//    public String nameSend="";
//    public String tn="";
//    public String thoigian="";
//    public Message message;
    private String id_sender;
    private String id_receive="1";
    private TopicItem topicItem;
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

    public MutableLiveData<ArrayList<TopicItem>> getArrayListMutableLiveData() {
        arrayList = new ArrayList<>();
        topicItem=new TopicItem();
         initdata();
        topicItem.setBold(false);
        //arrayListMutableLiveData.setValue(arrayList);
        return arrayListMutableLiveData;
    }
    private MutableLiveData<ArrayList<TopicItem>> initdata() {
        firebase fb =new firebase();
        DatabaseReference databaseReference =fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList.clear();
                TopicItem item=new TopicItem();
                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++){
                    Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    String id_re= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue());
                    if(id_re.equals(id_receive) && !!String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue()).equals("")) {
                        String tinnhan= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                        if(!processMessage(tinnhan)&& !status){
                            item.setBold(true);
                        }else{
                            item.setBold(false);
                        };
                        item.setDiem( String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue()));
                        String id= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue(Integer.class));
                        item = new TopicItem(
                                (String) snapshot.child("User").child(id).child("linkPhoto").getValue(),
                                (String) snapshot.child("User").child(id).child("fullName").getValue(),
                                topicItem.getMessages()
                        );
                        item.setIdGuest(String.valueOf(i));
                    }
                    arrayList.add(item);


                }
                Collections.sort(arrayList);
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
        Log.d("abc", "ngay: "+date);
    }

    public MutableLiveData<Boolean> getIsChoose() {
        return isChoose;
    }

    public void transtionMessage(){
       // if(isChoose.getValue()==Boolean.FALSE)
        isChoose.setValue(Boolean.TRUE);
        //else isChoose.setValue(Boolean.FALSE);
    }



}

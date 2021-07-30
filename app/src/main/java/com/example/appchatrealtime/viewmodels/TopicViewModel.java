package com.example.appchatrealtime.viewmodels;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.Message;
import com.example.appchatrealtime.model.TopicItem;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class TopicViewModel extends ViewModel {
    public String linkPhoto="";
    public String nameSend="";
    public Message message;
    private String id_sender;
    private String id_receive="1";
    public Boolean isBold ;
    MutableLiveData<ArrayList<TopicViewModel>> arrayListMutableLiveData=new MutableLiveData<>();
    private ArrayList<TopicViewModel> arrayList;

    public TopicViewModel() {
    }

    public String getImageUrl(){
        return linkPhoto;
    }

    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView,String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
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
        this.linkPhoto =   topicItem.getLinkPhoto();
        this.nameSend =     topicItem.getNameSend();
        this.message =      topicItem.getMessages();
    }

    public MutableLiveData<ArrayList<TopicViewModel>> getArrayListMutableLiveData() {
        arrayList = new ArrayList<>();
        TopicItem topicItem = new TopicItem("https://i.imgur.com/4mqc17e.jpg","Tran Duc Thanh",new Message("17:02","Xin chao cac ban! Minh ten la tran duc thanh nam nay 22 tuoi"));
        TopicViewModel topicViewModel=new TopicViewModel(topicItem);

         initdata();
        isBold=false;
        //arrayListMutableLiveData.setValue(arrayList);
        return arrayListMutableLiveData;
    }
    private MutableLiveData<ArrayList<TopicViewModel>> initdata() {

        firebase fb =new firebase();
        DatabaseReference databaseReference =fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList.clear();
                Message message1 =new Message();
                TopicViewModel topicViewModel;

                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++){
                    String tinnhan= (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                    Boolean status=snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    if(!processMessage(tinnhan,message1 )&& !status){ isBold=true;
                    }else isBold=false;
                    
                    String id_re= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue(Integer.class));
                    TopicItem topicItem1 = null;
                    if(id_re.equals(id_receive)) {
                        String id= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue(Integer.class));
                        topicItem1 = new TopicItem(
                                (String) snapshot.child("User").child(id).child("linkPhoto").getValue(),
                                (String) snapshot.child("User").child(id).child("fullName").getValue(),
                                message1
                        );
                    }
                    topicViewModel=new TopicViewModel(topicItem1);
                    arrayList.add(topicViewModel);

                }
               arrayListMutableLiveData.setValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);

        return arrayListMutableLiveData;
    }

    private boolean processMessage(String tinnhan, Message message1) {
        String[] arrSection=new String[tinnhan.split("--").length+1];
        String[] arrTime=new String[3];
        String[] arrCategory=new String[3];

        arrSection=tinnhan.split("--");
        HashMap<String,String> a=new HashMap<>();
         arrTime=arrSection[arrSection.length-1].split("-");
         arrCategory=arrTime[0].split("@@");
        message1.setTime(arrTime[1]);
        message1.setMessage(arrCategory[0]);

        if(arrCategory[1].equals("s")){
            return false;
        }else {
            return true;
        }

    }

    public void addListItem(TopicViewModel TopicViewModel){
        arrayList.add(TopicViewModel);
        arrayListMutableLiveData.setValue(arrayList);

    }



}

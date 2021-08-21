package com.example.appchatrealtime.viewmodels;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.appchatrealtime.R;
import com.example.appchatrealtime.model.ListFriend;
import com.example.appchatrealtime.model.SharedPreferencesModel;
import com.example.appchatrealtime.model.firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ListFriendViewModel  extends ViewModel {


    private int id_sender;
    ListFriend friend ;
    private MutableLiveData<ArrayList<ListFriend>> listMutableLiveData=new MutableLiveData<>();

    private MutableLiveData<ArrayList<ListFriend>> listMutableLiveData1=new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListFriend>> listMutableLiveData2=new MutableLiveData<>();
    private MutableLiveData<ArrayList<ListFriend>> listMutableLiveData3=new MutableLiveData<>();


    public ListFriendViewModel() {
    }

    public ListFriendViewModel(ListFriend listFriend) {
        this.friend = listFriend;
    }

    public MutableLiveData<ArrayList<ListFriend>> getListMutableLiveData(FragmentActivity context) {
        firebase fb=new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        String idHost=sharedPreferencesModel.getString("idHost","");
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(context==null || snapshot == null||idHost.equals("")){
                    return;
                }
                ArrayList<ListFriend> arrayList=new ArrayList<>();
                String listfriend= (String) snapshot.child("Friend_User").child(String.valueOf(idHost)).getValue();
                String[] arr=getlistFriend(listfriend);
                for(int i=0;i<arr.length;i++){
                    if(arr[i]!="") {

                        ListFriend fr = new ListFriend(
                                (String) snapshot.child("User").child(arr[i]).child("linkPhoto").getValue(),
                                (String) snapshot.child("User").child(arr[i]).child("fullName").getValue()
                        );

                        arrayList.add(fr);
                    }
                }

                Collections.sort(arrayList);

                listMutableLiveData.setValue(arrayList);


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return listMutableLiveData;
    }
    String idHost;
    public MutableLiveData<ArrayList<ListFriend>> getListMutableLiveData1(FragmentActivity context) {
        firebase fb=new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
         idHost=sharedPreferencesModel.getString("idHost","");
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<ListFriend> arrayList=new ArrayList<>();
                String listfriend= (String) snapshot.child("Friend_User").child(String.valueOf(idHost)).getValue();
                String listRequest= (String) snapshot.child("Invite").child(String.valueOf(idHost)).child("invite_send").getValue();
                String listInvite= (String) snapshot.child("Invite").child(String.valueOf(idHost)).child("invite_receive").getValue();

                String[] arr=getlistFriend(listfriend);
                String[] arr1=getlistFriend(listRequest);
                String[] arr2=getlistFriend(listInvite);
                int size= (int) snapshot.child("User").getChildrenCount();
                ArrayList<String> list = new ArrayList<String>(Arrays.asList(arr));
                ArrayList<String> list1 = new ArrayList<String>(Arrays.asList(arr1));
                ArrayList<String> list2 = new ArrayList<String>(Arrays.asList(arr2));

                for(int i=0,j=0;i<size;i++){
                    if(i!= Integer.valueOf(idHost)) {
                        ListFriend fr =new ListFriend(
                                (String) snapshot.child("User").child(String.valueOf(i)).child("linkPhoto").getValue(),
                                (String) snapshot.child("User").child(String.valueOf(i)).child("fullName").getValue());
                        if (list.contains(String.valueOf(i)) || list1.contains(String.valueOf(i)) || list2.contains(String.valueOf(i))) {
                            fr.setFriend( false);
                        } else fr.setFriend(true);
                        fr.setId(String.valueOf(i));
                        arrayList.add(fr);

                    }

                }
                Collections.sort(arrayList);

                listMutableLiveData1.setValue(arrayList);
             }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return listMutableLiveData1;
    }

    public MutableLiveData<ArrayList<ListFriend>> getListMutableLiveData2(FragmentActivity context) {
        firebase fb=new firebase();
        SharedPreferencesModel sharedPreferencesModel=new SharedPreferencesModel(context);
        idHost=sharedPreferencesModel.getString("idHost","");

        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<ListFriend> arrayList=new ArrayList<>();
                String invite_recive= (String) snapshot.child("Invite").child(String.valueOf(idHost)).child("invite_receive").getValue();
                String invite_send= (String) snapshot.child("Invite").child(String.valueOf(idHost)).child("invite_send").getValue();
                String[] arrReceive=getlistFriend(invite_recive);
                String[] arrSend=getlistFriend(invite_send);
                for(int i=0;i<arrReceive.length;i++){
                    if (arrReceive[i] != "") {
                        ListFriend fr =new ListFriend(
                                (String) snapshot.child("User").child(arrReceive[i]).child("linkPhoto").getValue(),
                                (String) snapshot.child("User").child(arrReceive[i]).child("fullName").getValue());
                        fr.setFriend(true);
                        fr.setId(arrReceive[i]);
                        arrayList.add(fr);
                    }



                   // arrlistName.add(friend.getNameFull());

                }
                Collections.sort(arrayList);

                listMutableLiveData2.setValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return listMutableLiveData2;
    }
    public MutableLiveData<ArrayList<ListFriend>> getListMutableLiveData3() {
        firebase fb=new firebase();
        DatabaseReference databaseReference=fb.getDatabaseReference();
        ValueEventListener postMessage=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<ListFriend> arrayList=new ArrayList<>();
                String invite_recive= (String) snapshot.child("Invite").child(String.valueOf(idHost)).child("invite_receive").getValue();
                String invite_send= (String) snapshot.child("Invite").child(String.valueOf(idHost)).child("invite_send").getValue();
                String[] arrReceive=getlistFriend(invite_recive);
                String[] arrSend=getlistFriend(invite_send);
                for(int i=0;i<arrSend.length;i++){
                    if(arrSend[i]!=""){
                        ListFriend fr =new ListFriend(
                                (String) snapshot.child("User").child(arrSend[i]).child("linkPhoto").getValue(),
                                (String) snapshot.child("User").child(arrSend[i]).child("fullName").getValue());
                        fr.setFriend(false);
                        fr.setId(arrSend[i]);
                        arrayList.add(fr);
                    }

                }
                Collections.sort(arrayList);
                //Collections.sort(arrlistName);

                listMutableLiveData3.setValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        return listMutableLiveData3;
    }

    @BindingAdapter({"bind:imageUri"})
    public static void loadImage(ImageView imageView, String imgaeUrl){
        Glide.with(imageView.getContext()).load(imgaeUrl).placeholder(R.drawable.personal1).into(imageView);
    }

    private String[] getlistFriend(String message){
        String[] arrlist = message.split(",");
        return arrlist;
    }
    private String getSticky(String s){
        String[] a=s.split(" ");
        return a[a.length-1];

    }

}

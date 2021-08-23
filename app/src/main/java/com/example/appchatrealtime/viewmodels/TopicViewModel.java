package com.example.appchatrealtime.viewmodels;

import android.graphics.Typeface;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appchatrealtime.ConnectionReceiver;
import com.example.appchatrealtime.model.Database.CreateDatabase;
import com.example.appchatrealtime.model.Database.ListMessageDB;
import com.example.appchatrealtime.model.Database.UserDB;
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
    private MutableLiveData<String> transitionData = new MutableLiveData<>();
    MutableLiveData<ArrayList<TopicItem>> arrayListMutableLiveData = new MutableLiveData<>();

    private ArrayList<TopicItem> arrayList;
    private MutableLiveData<Boolean> isChoose = new MutableLiveData<>();

    public MutableLiveData<String> getTransitionData() {
        return transitionData;
    }

    public void setTransitionData(String s) {
        transitionData.postValue(s);
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
        this.topicItem = topicItem;

    }

    public MutableLiveData<ArrayList<TopicItem>> getArrayListMutableLiveData(FragmentActivity context) {
        arrayList = new ArrayList<>();
        topicItem = new TopicItem();
        boolean ret = ConnectionReceiver.isConnected();
        String msg;
        if (ret == true) {
            initdata(context);
        } else {
            initdata1(context);
        }

        //arrayListMutableLiveData.setValue(arrayList);
        return arrayListMutableLiveData;
    }

    private MutableLiveData<ArrayList<TopicItem>> initdata(FragmentActivity context) {
        firebase fb = new firebase();
        SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel(context);
        String idHost = sharedPreferencesModel.getString("idHost", "");
        DatabaseReference databaseReference = fb.getDatabaseReference();
        ValueEventListener postMessage = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (context == null) {
                    return;
                }
                arrayList.clear();
                CreateDatabase.getInstance(context).userDAO().deleteAllUser();
                CreateDatabase.getInstance(context).listMessageDAO().deleteAllUser();

                listUser = new ArrayList<>();
                for (int i = 0; i < snapshot.child("User").getChildrenCount(); i++
                ) {
                    listUser.add(snapshot.child("User").child(String.valueOf(i)).getValue(User.class));
                    UserDB userDB = new UserDB(i, listUser.get(i).getEmail(), listUser.get(i).getPassword(), listUser.get(i).getFullName(), listUser.get(i).getPhoneNumber(), listUser.get(i).getDate(), listUser.get(i).getLinkPhoto());
                    CreateDatabase.getInstance(context).userDAO().InsertUser(userDB);

                }

                for (int i = 0; i < snapshot.child("ListMessage").getChildrenCount(); i++) {
                    TopicItem item = new TopicItem();
                    Boolean status = snapshot.child("ListMessage").child(String.valueOf(i)).child("status").getValue(Boolean.class);
                    String id_re = AddId(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue() + String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue())));
                    if (Check(id_re, idHost) && !String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue()).equals("")) {

                        String tinnhan = (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("message").getValue();
                        String idBold=(String) snapshot.child("ListMessage").child(String.valueOf(i)).child("addId").getValue();
                        item.setDiem(String.valueOf(snapshot.child("ListMessage").child(String.valueOf(i)).child("count").getValue()));
//                        String id= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue());
                        Boolean bold = processMessage(tinnhan, idHost,idBold);

                        item = new TopicItem(
                                getLinkPhoto(id_re, idHost),
                                getName(id_re, idHost),
                                topicItem.getMessages()
                        );
                        item.setParagraph(tinnhan);
                        item.setIdGuest(id_re);
                        if (!bold ) {
                            item.setBold(true);

                        } else {
                            item.setBold(false);
                        }
                        ;
                        item.setDiem(String.valueOf(Count(tinnhan, idHost)));
                        ListMessageDB listMessageDB = new ListMessageDB(i, item.getDiem(), (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("id_receiver").getValue(), (String) snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue(), tinnhan, status,idBold);
                        CreateDatabase.getInstance(context).listMessageDAO().InsertUser(listMessageDB);
                        arrayList.add(item);

                    }
                }
                if (arrayList.size() != 0) {
                    Collections.sort(arrayList);
                }
                arrayListMutableLiveData.postValue(arrayList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(postMessage);
        setNotificaion();
        return arrayListMutableLiveData;
    }

    private MutableLiveData<ArrayList<TopicItem>> initdata1(FragmentActivity context) {

        SharedPreferencesModel sharedPreferencesModel = new SharedPreferencesModel(context);
        String idHost = sharedPreferencesModel.getString("idHost", "");
        arrayList.clear();
        List<UserDB> listUser = new ArrayList<>();
        listUser = CreateDatabase.getInstance(context).userDAO().getListUser();
        List<ListMessageDB> listMessageDB = new ArrayList<>();
        listMessageDB = CreateDatabase.getInstance(context).listMessageDAO().getListUser();
        for (int i = 0; i < listMessageDB.size(); i++) {
            TopicItem item = new TopicItem();
            Boolean status = listMessageDB.get(i).getStatus();
            String id_re = AddId(listMessageDB.get(i).getId_sender() + listMessageDB.get(i).getId_receiver());
            String idBold=listMessageDB.get(i).getIdbold();
            if (Check(id_re, idHost) && !String.valueOf(listMessageDB.get(i).getMessage().toString()).equals("")) {
                String tinnhan = (String) listMessageDB.get(i).getMessage();
                item.setParagraph(tinnhan);
                item.setDiem(listMessageDB.get(i).getCount());
//                        String id= String.valueOf( snapshot.child("ListMessage").child(String.valueOf(i)).child("id_sender").getValue());
                Boolean bold = processMessage(tinnhan, idHost,idBold);

                item = new TopicItem(
                        getLinkPhoto1(id_re, idHost, listUser),
                        getName1(id_re, idHost, listUser),
                        topicItem.getMessages()
                );
                item.setIdGuest(id_re);
                if (!bold ) {
                    item.setBold(true);

                } else {
                    item.setBold(false);
                }
                ;
                item.setDiem(String.valueOf(Count(tinnhan, idHost)));
                arrayList.add(item);

            }
        }
        if (arrayList.size() != 0) {
            Collections.sort(arrayList);
        }
        arrayListMutableLiveData.postValue(arrayList);


        return arrayListMutableLiveData;
    }

    private void setNotificaion() {

    }

    private boolean processMessage(String tinnhan, String idHost,String addid) {
        if (tinnhan.equals("")) {
            return false;
        }
        String[] arrSection = new String[tinnhan.split("--").length + 1];
        String[] arrTime = new String[3];
        String[] arrCategory = new String[3];
        String[] arrImage = new String[3];

        Message message = new Message();
        arrSection = tinnhan.split("@@@@@");
        arrTime = arrSection[arrSection.length - 1].split("@@@@");
        arrCategory = arrTime[0].split("@@@");
        arrImage = arrCategory[0].split("@@");
        if (arrImage.length >= 2) {
            message.setMessage("[image]");
        } else message.setMessage(arrCategory[0]);
        message.setTime(arrTime[1]);
        topicItem.setMessages(message);
        String[] arradId=addid.split(",");
        for(int i=0;i<arradId.length;i++){
            if(arradId[i].equals(idHost)&&!arrCategory[1].equals(idHost)){
                return false;
            }
        }
        return true;
    }

    void ngay() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

    }

    public MutableLiveData<Boolean> getIsChoose() {
        return isChoose;
    }

    public void transtionMessage() {
        // if(isChoose.getValue()==Boolean.FALSE)
        isChoose.setValue(Boolean.TRUE);
        //else isChoose.setValue(Boolean.FALSE);
    }

    private String getLinkPhoto(String s, String idHost) {

        String[] arr = s.split(",");
        if (arr.length == 2) {
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(idHost)) {

                    return listUser.get(Integer.parseInt(arr[i])).getLinkPhoto();
                }
            }
        }
        return "";
    }

    private String getLinkPhoto1(String s, String idHost, List<UserDB> list) {

        String[] arr = s.split(",");
        if (arr.length == 2) {
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(idHost)) {

                    return list.get(Integer.parseInt(arr[i])).getLinkPhoto();
                }
            }
        }
        return "";
    }

    private String getName(String s, String idHost) {

        String[] arr = s.split(",");
        if (arr.length == 2) {
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(idHost)) {

                    return listUser.get(Integer.parseInt(arr[i])).getFullName();
                }
            }
        } else {
            String chuoi = "";
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(idHost)) {
                    String[] arr1 = listUser.get(Integer.parseInt(arr[i])).getFullName().toString().split(" ");
                    chuoi += arr1[arr1.length - 1];
                    if (i < arr.length - 1) chuoi += ",";
                }
            }
            return chuoi;
        }
        return "";
    }

    private String getName1(String s, String idHost, List<UserDB> list) {

        String[] arr = s.split(",");
        if (arr.length == 2) {
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(idHost)) {

                    return list.get(Integer.parseInt(arr[i])).getFullName();
                }
            }
        } else {
            String chuoi = "";
            for (int i = 0; i < arr.length; i++) {
                if (!arr[i].equals(idHost)) {
                    String[] arr1 = list.get(Integer.parseInt(arr[i])).getFullName().toString().split(" ");
                    chuoi += arr1[arr1.length - 1];
                    if (i < arr.length - 1) chuoi += ",";
                }
            }
            return chuoi;
        }
        return "";
    }

    String AddId(String s) {
        List<String> list = Arrays.asList(s.split(","));
        Collections.sort(list);
        String chuoi = "";
        for (int i = 0; i < list.size(); i++) {
            chuoi += list.get(i) + ",";
        }
        return chuoi;

    }

    Boolean Check(String s, String idHost) {
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].trim().equals(idHost)) {
                return true;
            }
        }
        return false;
    }

    int Count(String s, String idHost) {
        String[] arrSection = new String[s.split("--").length + 1];
        String[] arrTime = new String[3];
        String[] arrCategory = new String[3];
        String[] arrImage = new String[3];
        arrSection = s.split("@@@@@");
        arrTime = arrSection[arrSection.length - 1].split("@@@@");
        arrCategory = arrTime[0].split("@@@");
        int count = 0;
        for (int i = 0; i < arrSection.length; i++) {
            arrTime = arrSection[i].split("@@@@");
            arrCategory = arrTime[0].split("@@@");
            count++;
            if (arrCategory[1].equals(idHost)) {
                count = 0;
            }
        }
        return count;
    }


}

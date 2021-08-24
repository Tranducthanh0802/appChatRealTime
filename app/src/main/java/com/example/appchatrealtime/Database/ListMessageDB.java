package com.example.appchatrealtime.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "ListMessage")
public class ListMessageDB {
    @PrimaryKey
    private int id;
    private String count;
    private String id_receiver;
    private String id_sender;
    private String message;
    private Boolean status;
    private String addId;
    private String idbold;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public ListMessageDB(int id, String count, String id_receiver, String id_sender, String message, Boolean status,String idbold) {
        this.id=id;
        this.count = count;
        this.id_receiver = id_receiver;
        this.id_sender = id_sender;
        this.message = message;
        this.status = status;
        this.addId = AddId(id_receiver+","+ id_sender);
        this.idbold=idbold;
    }

    public String getIdbold() {
        return idbold;
    }

    public void setIdbold(String idbold) {
        this.idbold = idbold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(String id_receiver) {
        this.id_receiver = id_receiver;
    }

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id_sender) {
        this.id_sender = id_sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
}

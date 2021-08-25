package com.example.appchatrealtime.service.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListChat {
    private  int count;
    private  String idHost;
    private String idGuest;
    private String message;
    private Boolean status;
    private String addId;

    public ListChat(int count, String idHost, String idGuest, String message, Boolean status) {
        this.count = count;
        this.idHost = idHost;
        this.idGuest = idGuest;
        this.message = message;
        this.status = status;
        this.addId = AddId(idHost+","+ idGuest);
    }

    public ListChat() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getIdHost() {
        return idHost;
    }

    public void setIdHost(String idHost) {
        this.idHost = idHost;
    }

    public String getIdGuest() {
        return idGuest;
    }

    public void setIdGuest(String idGuest) {
        this.idGuest = idGuest;
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

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
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

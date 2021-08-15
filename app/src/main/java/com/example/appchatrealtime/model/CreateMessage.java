package com.example.appchatrealtime.model;

public class CreateMessage {
    private int count;
    private String id_receiver;
    private String id_sender;
    private String message;
    private Boolean status;

    public CreateMessage(String id_receiver, String id_sender) {
        this.id_receiver = id_receiver;
        this.id_sender = id_sender;
        this.count=0;
        this.message="";
        this.status=false;
    }

    public CreateMessage() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
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
}
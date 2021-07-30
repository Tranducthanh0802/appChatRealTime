package com.example.appchatrealtime.model;

public class MessageReceiver {
    private String id_Receiver;
    private Message message;

    public MessageReceiver(String id_Receiver, Message message) {
        this.id_Receiver = id_Receiver;
        this.message = message;
    }

    public String getId_Receiver() {
        return id_Receiver;
    }

    public void setId_Receiver(String id_Receiver) {
        this.id_Receiver = id_Receiver;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}

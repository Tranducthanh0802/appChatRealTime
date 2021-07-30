package com.example.appchatrealtime.model;

public class MessageSender {
    private String id_sender;
    private Message message;

    public MessageSender(String id_sender, Message message) {
        this.id_sender = id_sender;
        this.message = message;
    }

    public String getId_sender() {
        return id_sender;
    }

    public void setId_sender(String id_sender) {
        this.id_sender = id_sender;
    }
}

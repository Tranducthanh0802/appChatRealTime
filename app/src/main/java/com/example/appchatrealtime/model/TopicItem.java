package com.example.appchatrealtime.model;

public class TopicItem {
    private String linkPhoto;
    private String nameSend;
    private Message messages;

    public TopicItem() {
    }

    public TopicItem(String linkPhoto, String nameSend, Message messages) {
        this.linkPhoto = linkPhoto;
        this.nameSend = nameSend;
        this.messages = messages;
    }

    public TopicItem(String linkPhoto, String nameSend) {
        this.linkPhoto = linkPhoto;
        this.nameSend = nameSend;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getNameSend() {
        return nameSend;
    }

    public void setNameSend(String nameSend) {
        this.nameSend = nameSend;
    }

    public Message getMessages() {
        return messages;
    }

    public void setMessages(Message messages) {
        this.messages = messages;
    }
}

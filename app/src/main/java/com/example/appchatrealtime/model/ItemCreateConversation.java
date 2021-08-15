package com.example.appchatrealtime.model;

public class ItemCreateConversation {
    private String nameFull;
    private String linkPhoto;
    private Boolean isCheck;
    private int idFriend;

    public ItemCreateConversation(String nameFull, String linkPhoto, Boolean isCheck,Integer idFriend) {
        this.nameFull = nameFull;
        this.linkPhoto = linkPhoto;
        this.isCheck = isCheck;
        this.idFriend= idFriend;
    }

    public int getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(int idFriend) {
        this.idFriend = idFriend;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}

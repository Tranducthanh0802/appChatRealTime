package com.example.appchatrealtime.service.model;

public class Invite {
    private String linkPhoto;
    private String nameFull;
    private Boolean isFriend;
    private String stickHeader;

    public String getStickHeader() {
        return stickHeader;
    }

    public void setStickHeader(String stickHeader) {
        this.stickHeader = stickHeader;
    }

    public Invite(String linkPhoto, String nameFull, Boolean isFriend) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.isFriend = isFriend;
    }

    public Invite(String linkPhoto, String nameFull) {
        this.linkPhoto = linkPhoto;
        this.nameFull = nameFull;
        this.stickHeader= getSticky(nameFull);
    }

    public Invite() {
    }

    public String getLinkPhoto() {
        return linkPhoto;
    }

    public void setLinkPhoto(String linkPhoto) {
        this.linkPhoto = linkPhoto;
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }
    private String getSticky(String s){
        String[] a=s.split(" ");
        return a[a.length-1];

    }
}

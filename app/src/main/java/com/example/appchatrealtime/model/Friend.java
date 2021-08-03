package com.example.appchatrealtime.model;

public class Friend  {
    public String link;
    private String id;

    public Friend() {
    }

    public Friend(String link, String id) {
        this.link = link;
        this.id = id;

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

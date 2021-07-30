package com.example.appchatrealtime.model;

import java.util.List;

public class ListMessage {
    private List<Message> receive;
    private List<Message> send;
    private String id_send;
    private String id_receive;
    private Boolean status;

    public ListMessage(List<Message> receive, List<Message> send, String id_send, String id_receive, Boolean status) {
        this.receive = receive;
        this.send = send;
        this.id_send = id_send;
        this.id_receive = id_receive;
        this.status = status;
    }

    public List<Message> getReceive() {
        return receive;
    }

    public void setReceive(List<Message> receive) {
        this.receive = receive;
    }

    public List<Message> getSend() {
        return send;
    }

    public void setSend(List<Message> send) {
        this.send = send;
    }

    public String getId_send() {
        return id_send;
    }

    public void setId_send(String id_send) {
        this.id_send = id_send;
    }

    public String getId_receive() {
        return id_receive;
    }

    public void setId_receive(String id_receive) {
        this.id_receive = id_receive;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

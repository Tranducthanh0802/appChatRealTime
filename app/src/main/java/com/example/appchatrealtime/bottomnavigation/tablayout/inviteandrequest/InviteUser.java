package com.example.appchatrealtime.bottomnavigation.tablayout.inviteandrequest;

public class InviteUser {
    private String invite_receive;
    private String invite_send;

    public InviteUser(String invite_receive, String invite_send) {
        this.invite_receive = invite_receive;
        this.invite_send = invite_send;
    }

    public InviteUser() {
        this.invite_receive = "";
        this.invite_send = "";
    }

    public String getInvite_receive() {
        return invite_receive;
    }

    public void setInvite_receive(String invite_receive) {
        this.invite_receive = invite_receive;
    }

    public String getInvite_send() {
        return invite_send;
    }

    public void setInvite_send(String invite_send) {
        this.invite_send = invite_send;
    }
}

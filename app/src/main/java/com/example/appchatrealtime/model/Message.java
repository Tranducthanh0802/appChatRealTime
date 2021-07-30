package com.example.appchatrealtime.model;

public class Message {
    private String time;
    private String message;


    public Message() {
    }

    public Message(String time, String message) {
        this.time = time;
        this.message = message;
    }




    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        if(message.length()>31){
            char[] arr = message.toCharArray();
            char[] arr1=new char[34];
            for(int i=0;i<31;i++){
                arr1[i]=arr[i];
            }
            arr1[31]='.';
            arr1[32]='.';
            arr1[33]='.';

            return String.valueOf(arr1);
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

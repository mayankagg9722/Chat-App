package com.example.mayank.firebasedatatest;

/**
 * Created by mayank on 06-08-2016.
 */
public class ChatMessage {

    String name;
    String message;

    public ChatMessage(){

    }
    public ChatMessage(String name,String message) {
        this.message = message;
        this.name = name;
    }


    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}

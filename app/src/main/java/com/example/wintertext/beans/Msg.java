package com.example.wintertext.beans;

public class Msg {
    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    private String content;
    private int type;
    public Msg(String content,int type){
        this.content = content;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}

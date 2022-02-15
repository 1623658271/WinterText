package com.example.wintertext.beans;

/**
 * description ： TODO:Msg消息类
 * author : lfy
 * email : 1623658271@qq.com
 * date : 2022/2/1 19:40
 */
public class Msg {
    public static final int TYPE_A = 0;
    public static final int TYPE_B = 1;
    public static final int TYPE_C = 2;
    public static final int TYPE_D = 3;
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

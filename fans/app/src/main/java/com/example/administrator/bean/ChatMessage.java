package com.example.administrator.bean;

import java.util.Date;

/**
 * Created by Administrator on 2016/4/22.
 */
public class ChatMessage {
    private Date date;
    private String name;
    private String msg;
    private Type type;
    public enum Type{
        Incoming,
        Outcoming,
    }
   public ChatMessage(Date date,Type type,String msg){
       this.date=date;
       this.type=type;
       this.msg=msg;
   }
    public ChatMessage(){

    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}

package com.example.administrator.bean;

/**
 * Created by Administrator on 2016/4/21.
 */
public class Result {
    private int Code;
    private String text;

    public void setCode(int code) {
        Code = code;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return Code;
    }

    public String getText() {
        return text;
    }
}

package com.example.administrator.dt.bean;


public class TopItem {
   private int imageResource;
   private String time;
   private String title;
    public TopItem(int imageResource,String time,String title){
        this.imageResource=imageResource;
        this.time=time;
        this.title=title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.example.horry.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "GREEN_TOP".
 */
public class GreenTop {

    private Long id;
    private String topentity;
    private String typekind;

    public GreenTop() {
    }

    public GreenTop(Long id) {
        this.id = id;
    }

    public GreenTop(Long id, String topentity, String typekind) {
        this.id = id;
        this.topentity = topentity;
        this.typekind = typekind;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopentity() {
        return topentity;
    }

    public void setTopentity(String topentity) {
        this.topentity = topentity;
    }

    public String getTypekind() {
        return typekind;
    }

    public void setTypekind(String typekind) {
        this.typekind = typekind;
    }

}
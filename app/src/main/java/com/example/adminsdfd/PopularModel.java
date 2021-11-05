package com.example.adminsdfd;

import java.io.Serializable;

public class PopularModel implements Serializable {
    String name;
    String description;
    String calo;
    String img_url;
    String time;

    public PopularModel() {
    }

    public PopularModel(String name, String description, String calo, String img_url, String time) {
        this.name = name;
        this.description = description;
        this.calo = calo;
        this.img_url = img_url;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalo() {
        return calo;
    }

    public void setCalo(String calo) {
        this.calo = calo;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

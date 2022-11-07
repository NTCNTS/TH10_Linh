package com.example.th10_linh;

public class StudenModel {
    String image,name,msv;

    StudenModel(){

    }

    public StudenModel(String image, String name, String msv) {
        this.image = image;
        this.name = name;
        this.msv = msv;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsv() {
        return msv;
    }

    public void setMsv(String msv) {
        this.msv = msv;
    }
}

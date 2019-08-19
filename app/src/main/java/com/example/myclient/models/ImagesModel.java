package com.example.myclient.models;

public class ImagesModel {
    private String url;
    private String desc;
    private String webUrl;

    public ImagesModel(){

    }

    public ImagesModel(String url, String desc,String webUrl) {
        this.url = url;
        this.desc = desc;
        this.webUrl = webUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }

    public String getWebUrl() {
        return webUrl;
    }
}

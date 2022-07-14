package com.shahindemunav.admin.ui.slideshow;

public class SliderData {
    String imageUrl,
            title,pub,key;

    public SliderData(String imageUrl, String title, String pub, String key) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.pub = pub;
        this.key = key;
    }

    public SliderData() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

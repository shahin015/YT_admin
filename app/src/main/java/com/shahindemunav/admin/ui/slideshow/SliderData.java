package com.shahindemunav.admin.ui.slideshow;

public class SliderData {
    String imageUrl,
            title,pub;

    public SliderData(String imageUrl, String title, String pub) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.pub = pub;
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
}

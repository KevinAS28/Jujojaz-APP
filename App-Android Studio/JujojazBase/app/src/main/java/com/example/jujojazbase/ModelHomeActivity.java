package com.example.jujojazbase;

public class ModelHomeActivity {
    private String image, title, detail;

    public ModelHomeActivity(String image, String title, String detail) {
        this.image = image;
        this.title = title;
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

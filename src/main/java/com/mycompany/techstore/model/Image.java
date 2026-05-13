package com.mycompany.techstore.model;

public class Image {
    private String imageID;
    private String imageSource;

    public Image() {}

    public Image(String imageID, String imageSource) {
        this.imageID = imageID;
        this.imageSource = imageSource;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}

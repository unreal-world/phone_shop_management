package com.mycompany.phonestore.model;

public class Image {
    private String imageID;
    private String productID;
    private String imageSource;

    public Image() {}

    public Image(String imageID, String productID, String imageSource) {
        this.imageID = imageID;
        this.productID = productID;
        this.imageSource = imageSource;
    }

    public Image(String string, String string0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}

package com.mycompany.techstore.dao;

import com.mycompany.techstore.model.Image;
import java.util.List;

public interface ImageDao {
    List<Image> getAllImages();
    Image getImageById(String imageID);
    List<Image> getImagesByProductId(String productID);
    void addImage(Image image);
    void updateImage(Image image);
    void deleteImage(String imageID);
}

package com.mycompany.techstore.service;

import com.mycompany.techstore.model.Image;
import java.util.List;

public interface ImageService {
    List<Image> getAllImages();
    Image getImageById(String imageID);
    List<Image> getImagesByProductId(String productID);
    void saveImage(Image image);
    void updateImage(Image image);
    void deleteImage(String imageID);
}

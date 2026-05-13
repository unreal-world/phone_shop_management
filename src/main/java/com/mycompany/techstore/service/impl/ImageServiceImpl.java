package com.mycompany.techstore.service.impl;

import com.mycompany.techstore.dao.ImageDao;
import com.mycompany.techstore.model.Image;
import com.mycompany.techstore.service.ImageService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageDao imageDao;

    public ImageServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public List<Image> getAllImages() {
        return imageDao.getAllImages();
    }

    @Override
    public Image getImageById(String imageID) {
        return imageDao.getImageById(imageID);
    }

    @Override
    public List<Image> getImagesByProductId(String productID) {
        return imageDao.getImagesByProductId(productID);
    }

    @Override
    public void saveImage(Image image) {
        imageDao.addImage(image);
    }

    @Override
    public void updateImage(Image image) {
        imageDao.updateImage(image);
    }

    @Override
    public void deleteImage(String imageID) {
        imageDao.deleteImage(imageID);
    }
}

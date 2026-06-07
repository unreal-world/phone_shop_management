package com.mycompany.phonestore.dao.impl;

import com.mycompany.phonestore.dao.ImageDao;
import com.mycompany.phonestore.model.Image;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao {

    private final JdbcTemplate jdbcTemplate;

    public ImageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Image> getAllImages() {
        String sql = "SELECT * FROM Image";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Image(
                rs.getString("imageID"),
                rs.getString("imageSource")
        ));
    }

    @Override
    public Image getImageById(String imageID) {
        String sql = "SELECT * FROM Image WHERE imageID = ?";
        List<Image> images = jdbcTemplate.query(sql, new Object[]{imageID}, (rs, rowNum) -> new Image(
                rs.getString("imageID"),
                rs.getString("imageSource")
        ));
        return images.isEmpty() ? null : images.get(0);
    }

    @Override
    public List<Image> getImagesByProductId(String productID) {
        String sql = "SELECT * FROM Image WHERE productID = ?";
        return jdbcTemplate.query(sql, new Object[]{productID}, (rs, rowNum) -> new Image(
                rs.getString("imageID"),
                rs.getString("imageSource")
        ));
    }

    @Override
    public void addImage(Image image) {
        String sql = "INSERT INTO Image (imageID, productID, imageSource) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, image.getImageID(), image.getProductID(), image.getImageSource());
    }

    @Override
    public void updateImage(Image image) {
        String sql = "UPDATE Image SET imageSource=? WHERE imageID=?";
        jdbcTemplate.update(sql, image.getImageSource(), image.getImageID());
    }

    @Override
    public void deleteImage(String imageID) {
        String sql = "DELETE FROM Image WHERE imageID=?";
        jdbcTemplate.update(sql, imageID);
    }
}

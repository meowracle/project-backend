package com.horizonevent.project.security.services.picture;

import com.horizonevent.project.models.Picture;

import java.util.Optional;

public interface PictureService {
    Iterable<Picture> findAllPicture();
    Optional<Picture> findById(Long id);
    void save(Picture picture);
    void remove(Long id);
}

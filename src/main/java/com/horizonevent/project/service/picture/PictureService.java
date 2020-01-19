package com.horizonevent.project.service.picture;

import com.horizonevent.project.models.Picture;

import java.util.Optional;

public interface PictureService {
    Iterable<Picture> findAllPictures();

    Optional<Picture> findPictureById(Long id);

    void save(Picture picture);

    void remove(Long id);
}

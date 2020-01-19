package com.horizonevent.project.service.picture;

import com.horizonevent.project.models.Picture;
import com.horizonevent.project.repository.picture.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureRepository pictureRepository;


    @Override
    public Iterable<Picture> findAllPictures() {
        return pictureRepository.findAll();
    }

    @Override
    public Optional<Picture> findPictureById(Long id) {
        return pictureRepository.findById(id);
    }

    @Override
    public void save(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    public void remove(Long id) {
        pictureRepository.deleteById(id);
    }
}

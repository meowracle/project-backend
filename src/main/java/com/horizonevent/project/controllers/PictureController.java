package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Picture;
import com.horizonevent.project.service.picture.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/pictures")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @GetMapping("")
    public ResponseEntity<Iterable<Picture>> getListPictures() {
        Iterable<Picture> picturesList = pictureService.findAllPictures();
        return new ResponseEntity<Iterable<Picture>>(picturesList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewPicture(@RequestBody Picture picture) {
        System.out.println("Creating new picture");
        pictureService.save(picture);
        return new ResponseEntity<Long>(picture.getId(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Picture> getPictureById(@PathVariable Long id) {
        Optional<Picture> picture = pictureService.findPictureById(id);
        if (picture.isPresent()) {
            return new ResponseEntity<Picture>(picture.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Picture> updatePicture(@PathVariable Long id, @RequestBody Picture picture) {
        Optional<Picture> currentPicture = pictureService.findPictureById(id);
        if (currentPicture.isPresent()) {
            currentPicture.get().setId(id);
            currentPicture.get().setSrc(picture.getSrc());

            pictureService.save(currentPicture.get());
            return new ResponseEntity<Picture>(currentPicture.get(), HttpStatus.OK);
        } return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Picture> deletePicture(@PathVariable Long id) {
        Optional<Picture> picture = pictureService.findPictureById(id);
        if (picture.isPresent()) {
            pictureService.remove(id);
            return new ResponseEntity<Picture>(HttpStatus.OK);
        } return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
    }
}

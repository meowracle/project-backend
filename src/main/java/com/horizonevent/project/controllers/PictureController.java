package com.horizonevent.project.controllers;

import com.horizonevent.project.models.Picture;
import com.horizonevent.project.security.services.picture.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/picture")
public class PictureController {
    @Autowired
    PictureService pictureService;
    @GetMapping("")
    public ResponseEntity<Iterable<Picture>> showListPicture(){
        Iterable<Picture> pictures = pictureService.findAllPicture();
        return new ResponseEntity<Iterable<Picture>>(pictures, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewPicture(@RequestBody Picture picture) {
        System.out.println("Creating Picture ");
        pictureService.save(picture);
        return new ResponseEntity<Long>(picture.getId(),HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Picture> getPictureById(@PathVariable Long id,@RequestBody Picture picture){
        Optional<Picture> currentPicture = pictureService.findById(id);
        if(currentPicture.isPresent()){
            currentPicture.get().setId(id);
            currentPicture.get().setSrc(picture.getSrc());

            pictureService.save(currentPicture.get());
            return new ResponseEntity<Picture>(currentPicture.get(),HttpStatus.OK);
        }
        return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Picture> deletePicture(@PathVariable Long id){
        Optional<Picture> picture = pictureService.findById(id);
        if(picture.isPresent()){
            pictureService.remove(id);
            return new ResponseEntity<Picture>(HttpStatus.OK);
        }
        return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
    }
}

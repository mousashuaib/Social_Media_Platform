package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.MediaDto;
import com.example.social_media_platform.Service.MediaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MediaContoller {

    @Autowired
    private MediaServices mediaServices;



    @PostMapping("/AddMedia")
    public ResponseEntity<MediaDto> createMedia(@RequestBody MediaDto mediaDto) {
        MediaDto createdMedia = mediaServices.createMedia(mediaDto);
        return new ResponseEntity<>(createdMedia, HttpStatus.CREATED);
    }

    @GetMapping("getById/{mediaId}")
    public ResponseEntity<MediaDto> getMediaById(@PathVariable Long mediaId) {
        MediaDto mediaDto = mediaServices.getMediaById(mediaId);
        return mediaDto != null ? new ResponseEntity<>(mediaDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getAllMedia")
    public ResponseEntity<List<MediaDto>> getAllMedia() {
        List<MediaDto> mediaDtoList = mediaServices.getAllMedia();
        return new ResponseEntity<>(mediaDtoList, HttpStatus.OK);
    }
    @DeleteMapping("deleteMedia/{mediaId}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long mediaId) {
        boolean isDeleted = mediaServices.deleteMedia(mediaId);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("updateMedia/{mediaId}")
    public ResponseEntity<MediaDto> updateMedia(@PathVariable Long mediaId, @RequestBody MediaDto mediaDto) {
        MediaDto updatedMedia = mediaServices.updateMedia(mediaId, mediaDto);
        return updatedMedia != null ? new ResponseEntity<>(updatedMedia, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

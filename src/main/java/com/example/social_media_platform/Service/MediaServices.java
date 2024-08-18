package com.example.social_media_platform.Service;

import com.example.social_media_platform.Model.Dto.MediaDto;
import com.example.social_media_platform.Model.Entity.Media;
import com.example.social_media_platform.Model.Mapper.MediaMapper;
import com.example.social_media_platform.Repo.MediaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaServices {
    @Autowired
    private MediaRepo mediaRepo;
    @Autowired
    private MediaMapper mediaMapper;


    public MediaDto createMedia(MediaDto mediaDto) {
        Media media = mediaMapper.toEntity(mediaDto);
        Media savedMedia = mediaRepo.save(media);
        return mediaMapper.toDto(savedMedia);
    }




    public MediaDto getMediaById(Long mediaId) {
        Optional<Media> media = mediaRepo.findById(mediaId);
        return media.map(mediaMapper::toDto).orElse(null);
    }



    public List<MediaDto> getAllMedia() {
        List<Media> media = mediaRepo.findAll();
        return mediaMapper.toDto(media);
    }



    public MediaDto updateMedia(Long mediaId, MediaDto mediaDto) {
        if (mediaRepo.existsById(mediaId)) {
            Media media = mediaMapper.toEntity(mediaDto);
            media.setMediaId(mediaId);
            Media updatedMedia = mediaRepo.save(media);
            return mediaMapper.toDto(updatedMedia);
        }
        return null;
    }



    public boolean deleteMedia(Long mediaId) {
        if (mediaRepo.existsById(mediaId)) {
            mediaRepo.deleteById(mediaId);
            return true;
        }
        return false;
    }

}

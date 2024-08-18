package com.example.social_media_platform.Service;



import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.ProfileMapper;
import com.example.social_media_platform.Repo.ProfileRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ProfileService {


    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileMapper = profileMapper;
    }


    public ProfileDto createProfile(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }



        Profile profile = new Profile();
        profile.setUserEntity(userOptional.get());
        profile.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        profile.setLastUpdated(new Timestamp(System.currentTimeMillis()));



        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public ProfileDto getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
        return profileMapper.toDto(profile);
    }

    public ProfileDto updateProfile(Long userId, ProfileDto profileDto) {
        Profile existingProfile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        existingProfile.setBio(profileDto.getBio());
        existingProfile.setProfilePictureUrl(profileDto.getProfilePictureUrl());
        existingProfile.setMisc(profileDto.getMisc());
        // No need to manually set the lastUpdated field here.


        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toDto(updatedProfile);
    }



    //this one should be for the admin to delete a profile
    public void deleteProfile(Long userId) {
        Profile profile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
        profileRepository.delete(profile);
    }


    //find a user based on it username
    public ProfileDto getProfileByName(String username) {
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + username));
        Profile profile = profileRepository.findByUserEntity(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + username));
        return profileMapper.toDto(profile);
    }


    public ProfileDto updateProfilePicture(Long userId, ProfileDto profileDto) {
        Profile existingProfile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        existingProfile.setProfilePictureUrl(profileDto.getProfilePictureUrl());
        // No need to manually set the lastUpdated field here.

        profileRepository.save(existingProfile);
        return profileMapper.toDto(existingProfile);
    }

    public ProfileDto updateCoverPicture(Long userId, ProfileDto profileDto) {
        Profile existingProfile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        existingProfile.setCoverPictureUrl(profileDto.getCoverPictureUrl());
        // No need to manually set the lastUpdated field here.

        profileRepository.save(existingProfile);
        return profileMapper.toDto(existingProfile);
    }
}

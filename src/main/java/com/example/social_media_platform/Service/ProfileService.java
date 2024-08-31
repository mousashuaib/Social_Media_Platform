package com.example.social_media_platform.Service;

import com.example.social_media_platform.Config.FileUploadService;
import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Model.Entity.Profile;
import com.example.social_media_platform.Model.Entity.UserEntity;
import com.example.social_media_platform.Model.Mapper.ProfileMapper;
import com.example.social_media_platform.Repo.ProfileRepository;
import com.example.social_media_platform.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final ProfileSecurityService profileSecurityService;
    private final FileUploadService fileUploadService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, ProfileMapper profileMapper, ProfileSecurityService profileSecurityService, FileUploadService fileUploadService) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.profileMapper = profileMapper;
        this.profileSecurityService = profileSecurityService;
        this.fileUploadService = fileUploadService;
    }

    public ProfileDto createProfile(Long userId, String defaultImageUrl) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        Profile profile = new Profile();
        profile.setUserEntity(userOptional.get());
        profile.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        profile.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        profile.setProfilePictureUrl(defaultImageUrl);

        Profile savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }



    public ProfileDto getProfileByUserId(Long userId) {
        Profile profile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
        return profileMapper.toDto(profile);
    }

    public ProfileDto updateProfile(Long userId, ProfileDto profileDto) {
        if (!profileSecurityService.isProfileOwner(userId)) {
            throw new SecurityException("You are not authorized to update this profile.");
        }

        Profile existingProfile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        existingProfile.setBio(profileDto.getBio());
        existingProfile.setProfilePictureUrl(profileDto.getProfilePictureUrl());
        existingProfile.setMisc(profileDto.getMisc());

        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toDto(updatedProfile);
    }

    public void deleteProfile(Long userId) {
        Profile profile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
        profileRepository.delete(profile);
    }

    public ProfileDto getProfileByName(String username) {
        UserEntity user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + username));
        Profile profile = profileRepository.findByUserEntity(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user: " + username));
        return profileMapper.toDto(profile);
    }

    public ProfileDto updateProfilePicture(Long userId, MultipartFile profilePicture) throws IOException {
        if (!profileSecurityService.isProfileOwner(userId)) {
            throw new SecurityException("You are not authorized to update this profile picture.");
        }

        Profile existingProfile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        String profilePictureUrl = fileUploadService.saveFile(profilePicture);
        existingProfile.setProfilePictureUrl(profilePictureUrl);

        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toDto(updatedProfile);
    }

    public ProfileDto updateCoverPicture(Long userId, MultipartFile coverPicture) throws IOException {
        if (!profileSecurityService.isProfileOwner(userId)) {
            throw new SecurityException("You are not authorized to update this cover picture.");
        }

        Profile existingProfile = profileRepository.findByUserEntity_UserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        String coverPictureUrl = fileUploadService.saveFile(coverPicture);
        existingProfile.setCoverPictureUrl(coverPictureUrl);

        Profile updatedProfile = profileRepository.save(existingProfile);
        return profileMapper.toDto(updatedProfile);
    }
}

package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Service.ProfileService;

import jdk.jfr.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v0/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Description("Endpoint to get a profile for a user by userID.")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId) {
        ProfileDto profileDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDto);
    }

    @Description("Endpoint to search for a profile by username")
    @GetMapping("/search")
    public ResponseEntity<ProfileDto> searchProfile(@RequestParam String username) {
        ProfileDto profileDto = profileService.getProfileByName(username);
        return ResponseEntity.ok(profileDto);
    }

    @Description("Endpoint to create a profile for a user.")
    @PutMapping("/updateInfo/{userId}")
    @PreAuthorize("@profileSecurityService.isProfileOwner(#userId)")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateProfile(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @Description("Endpoint to update profile picture for a user.")
    @PutMapping("/updateProfilePicture/{userId}")
    @PreAuthorize("@profileSecurityService.isProfileOwner(#userId)")
    public ResponseEntity<ProfileDto> updateProfilePicture(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateProfilePicture(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @Description("Update cover picture, accessible only by the profile owner.")
    @PutMapping("/updateCoverPicture/{userId}")
    @PreAuthorize("@profileSecurityService.isProfileOwner(#userId)")
    public ResponseEntity<ProfileDto> updateCoverPicture(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateCoverPicture(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }
}

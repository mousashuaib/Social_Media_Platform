package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v0/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId) {
        ProfileDto profileDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDto);
    }

    @GetMapping("/search")
    public ResponseEntity<ProfileDto> searchProfile(@RequestParam String username) {
        ProfileDto profileDto = profileService.getProfileByName(username);
        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/updateInfo/{userId}")
    @PreAuthorize("@profileSecurityService.isProfileOwner(#userId)")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateProfile(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/updateProfilePicture/{userId}")
    @PreAuthorize("@profileSecurityService.isProfileOwner(#userId)")
    public ResponseEntity<ProfileDto> updateProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile profilePicture) throws IOException {
        ProfileDto updatedProfile = profileService.updateProfilePicture(userId, profilePicture);
        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/updateCoverPicture/{userId}")
    @PreAuthorize("@profileSecurityService.isProfileOwner(#userId)")
    public ResponseEntity<ProfileDto> updateCoverPicture(@PathVariable Long userId, @RequestParam("file") MultipartFile coverPicture) throws IOException {
        ProfileDto updatedProfile = profileService.updateCoverPicture(userId, coverPicture);
        return ResponseEntity.ok(updatedProfile);
    }

}

package com.example.social_media_platform.Controller;

import com.example.social_media_platform.Model.Dto.ProfileDto;
import com.example.social_media_platform.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v0/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    @Description("Get profile by user ID")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId) {
        ProfileDto profileDto = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profileDto);
    }



    @GetMapping("/search")
    @Description("Search profile by username")
    public ResponseEntity<ProfileDto> searchProfile(@RequestParam String username) {
        ProfileDto profileDto = profileService.getProfileByName(username);
        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/updateInfo/{userId}")
    @Description("Update profile information")
    public ResponseEntity<ProfileDto> updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateProfile(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }


    @PutMapping("/updateProfilePicture/{userId}")
    @Description("Update profile picture")
    public ResponseEntity<ProfileDto> updateProfilePicture(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateProfilePicture(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @PutMapping("/updateCoverPicture/{userId}")
    @Description("Update cover picture")
    public ResponseEntity<ProfileDto> updateCoverPicture(@PathVariable Long userId, @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.updateCoverPicture(userId, profileDto);
        return ResponseEntity.ok(updatedProfile);
    }


}

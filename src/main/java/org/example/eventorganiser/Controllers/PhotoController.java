package org.example.eventorganiser.Controllers;

import org.example.eventorganiser.DTOs.PhotoDTO;
import org.example.eventorganiser.Mapper.PhotoMapper;
import org.example.eventorganiser.Models.Photo;
import org.example.eventorganiser.Services.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/photos")
public class PhotoController {
    
    private final PhotoService photoService;
    
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }
    
    /**
     * Upload a photo for an event
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadPhoto(
            @PathVariable int eventId,
            @RequestParam int userId,
            @RequestParam("file") MultipartFile file) {
        try {
            Photo photo = photoService.uploadPhoto(eventId, userId, file);
            PhotoDTO photoDTO = PhotoMapper.toDto(photo);
            return ResponseEntity.ok(photoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Get all photos for an event
     */
    @GetMapping
    public ResponseEntity<?> getEventPhotos(@PathVariable int eventId) {
        try {
            List<Photo> photos = photoService.getEventPhotos(eventId);
            List<PhotoDTO> photoDTOs = photos.stream()
                    .map(PhotoMapper::toDto)
                    .toList();
            return ResponseEntity.ok(photoDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Get photos uploaded by a specific user for an event
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPhotosForEvent(
            @PathVariable int eventId,
            @PathVariable int userId) {
        try {
            List<Photo> photos = photoService.getUserPhotosForEvent(eventId, userId);
            List<PhotoDTO> photoDTOs = photos.stream()
                    .map(PhotoMapper::toDto)
                    .toList();
            return ResponseEntity.ok(photoDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Delete a photo (only by the uploader)
     */
    @DeleteMapping("/{photoId}")
    public ResponseEntity<?> deletePhoto(
            @PathVariable int eventId,
            @PathVariable Long photoId,
            @RequestParam int userId) {
        try {
            photoService.deletePhoto(photoId, userId);
            return ResponseEntity.ok("Photo deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get photo image as byte array
     */
    @GetMapping("/{photoId}/image")
    public ResponseEntity<byte[]> getPhotoImage(
            @PathVariable int eventId,
            @PathVariable Long photoId) {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            byte[] imageBytes = photoService.getPhotoBytes(photoId);

            // Determine content type from photo URL
            String photoUrl = photo.getPhotoUrl();
            MediaType mediaType = MediaType.IMAGE_JPEG; // default

            if (photoUrl.toLowerCase().endsWith(".png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (photoUrl.toLowerCase().endsWith(".gif")) {
                mediaType = MediaType.IMAGE_GIF;
            } else if (photoUrl.toLowerCase().endsWith(".webp")) {
                mediaType = MediaType.parseMediaType("image/webp");
            }

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imageBytes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


package org.example.eventorganiser.Services;

import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Models.Photo;
import org.example.eventorganiser.Models.User;
import org.example.eventorganiser.Repositories.EventRepository;
import org.example.eventorganiser.Repositories.EventGuestsRepository;
import org.example.eventorganiser.Repositories.PhotoRepository;
import org.example.eventorganiser.Repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventGuestsRepository eventGuestsRepository;

    // Directory where photos will be stored
    private final String uploadDir = "uploads/photos/";

    public PhotoService(PhotoRepository photoRepository,
                       EventRepository eventRepository,
                       UserRepository userRepository,
                       EventGuestsRepository eventGuestsRepository) {
        this.photoRepository = photoRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventGuestsRepository = eventGuestsRepository;

        // Create upload directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    /**
     * Upload a photo for an event
     */
    public Photo uploadPhoto(int eventId, int userId, MultipartFile file) {
        // Validate event exists
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user is organizer or guest
        boolean isOrganizer = event.getOrganizers().stream()
                .anyMatch(org -> org.getUserId() == userId);

        boolean isGuest = eventGuestsRepository.findById(new org.example.eventorganiser.Models.EventGuestsId(eventId, userId))
                .isPresent();

        if (!isOrganizer && !isGuest) {
            throw new RuntimeException("User is not authorized to upload photos for this event");
        }

        // Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String filename = UUID.randomUUID() + extension;

        try {
            // Save file to disk
            Path filePath = Paths.get(uploadDir + filename);
            Files.copy(file.getInputStream(), filePath);

            // Create photo record
            Photo photo = new Photo();
            photo.setEvent(event);
            photo.setUploadedBy(user);
            photo.setPhotoUrl("/uploads/photos/" + filename);
            photo.setUploadedAt(LocalDateTime.now());

            return photoRepository.save(photo);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    /**
     * Get all photos for an event
     */
    public List<Photo> getEventPhotos(int eventId) {
        // Validate event exists
        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Event not found");
        }

        return photoRepository.findByEventEventId(eventId);
    }

    /**
     * Get photos uploaded by a specific user for an event
     */
    public List<Photo> getUserPhotosForEvent(int eventId, int userId) {
        return photoRepository.findByEventEventIdAndUploadedByUserId(eventId, userId);
    }

    /**
     * Delete a photo
     */
    public void deletePhoto(Long photoId, int userId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        // Check if user is the uploader
        if (photo.getUploadedBy().getUserId() != userId) {
            throw new RuntimeException("You can only delete your own photos");
        }

        // Delete file from disk
        try {
            Path filePath = Paths.get(uploadDir + photo.getPhotoUrl().replace("/uploads/photos/", ""));
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error but continue with database deletion
            System.err.println("Failed to delete file: " + e.getMessage());
        }

        // Delete from database
        photoRepository.delete(photo);
    }

    /**
     * Get photo by ID
     */
    public Photo getPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));
    }

    /**
     * Get photo file bytes
     */
    public byte[] getPhotoBytes(Long photoId) {
        Photo photo = getPhotoById(photoId);
        String filename = photo.getPhotoUrl().replace("/uploads/photos/", "");
        Path filePath = Paths.get(uploadDir + filename);

        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read photo file", e);
        }
    }
}


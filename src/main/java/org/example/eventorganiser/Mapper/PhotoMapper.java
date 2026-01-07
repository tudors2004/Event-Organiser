package org.example.eventorganiser.Mapper;

import org.example.eventorganiser.DTOs.PhotoDTO;
import org.example.eventorganiser.Models.Photo;

public class PhotoMapper {

    public static PhotoDTO toDto(Photo photo) {
        if (photo == null) {
            return null;
        }

        return PhotoDTO.builder()
                .photoId(photo.getId())
                .eventId(photo.getEvent().getEventId())
                .uploaderId(photo.getUploadedBy().getUserId())
                .uploaderName(photo.getUploadedBy().getName())
                .photoUrl(photo.getPhotoUrl())
                .uploadedAt(photo.getUploadedAt())
                .build();
    }
}


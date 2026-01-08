package org.example.eventorganiser.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {
    private Long photoId;
    private int eventId;
    private int uploaderId;
    private String uploaderName;
    private String photoUrl;
    private LocalDateTime uploadedAt;
}


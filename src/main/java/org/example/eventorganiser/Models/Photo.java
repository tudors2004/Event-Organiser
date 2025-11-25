package org.example.eventorganiser.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    @ManyToOne
    private User uploadedBy;

    private String photoUrl;
    private LocalDateTime uploadedAt;

    public Photo() {}

    public Photo(Long id, Event event, User uploadedBy, String photoUrl, LocalDateTime uploadedAt) {
        this.id = id;
        this.event = event;
        this.uploadedBy = uploadedBy;
        this.photoUrl = photoUrl;
        this.uploadedAt = uploadedAt;
    }

}



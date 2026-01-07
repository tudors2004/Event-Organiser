package org.example.eventorganiser.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @Column(nullable = false)
    private String photoUrl;

    @Column(nullable = false)
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



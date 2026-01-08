package org.example.eventorganiser.Repositories;

import org.example.eventorganiser.Models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByEventEventId(int eventId);
    List<Photo> findByEventEventIdAndUploadedByUserId(int eventId, int userId);
}


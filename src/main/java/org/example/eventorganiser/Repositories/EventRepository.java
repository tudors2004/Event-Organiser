package org.example.eventorganiser.Repositories;

import org.example.eventorganiser.Models.Event;
import org.example.eventorganiser.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganiser(User organiser);
}
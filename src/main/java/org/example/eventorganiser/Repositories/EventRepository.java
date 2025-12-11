package org.example.eventorganiser.Repositories;

import org.example.eventorganiser.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT DISTINCT e FROM Event e LEFT JOIN e.organizers o LEFT JOIN e.eventGuests eg " +
           "WHERE o.userId = :userId OR eg.user.userId = :userId")
    List<Event> findAllEventsByUserId(@Param("userId") Integer userId);

    @Query("SELECT DISTINCT e FROM Event e JOIN e.organizers o WHERE o.userId = :userId")
    List<Event> findEventsByOrganizer(@Param("userId") Integer userId);

    Event findByEventName(String eventName);
}

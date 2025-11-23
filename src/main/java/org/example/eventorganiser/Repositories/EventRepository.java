package org.example.eventorganiser.Repositories;

import org.example.eventorganiser.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT DISTINCT e FROM Event e LEFT JOIN e.organizers o LEFT JOIN e.eventGuests eg " +
           "WHERE o.userId = :userId OR eg.user.userId = :userId")
    List<Event> findAllEventsByUserId(@Param("userId") Integer userId);
}

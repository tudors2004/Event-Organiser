package org.example.eventorganiser.DTOs;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Future;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateEventRequest {
    @NotBlank(message = "Event name is required")
    String eventName;

    @Future(message = "Event date must be in the future")
    LocalDate eventDate;

    @NotBlank(message = "Event location is required")
    String eventLocation;

    String description;

    String schedule;

    @NotEmpty(message = "At least one organizer is required")
    List<Integer> organizersId;
}

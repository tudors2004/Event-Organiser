package org.example.eventorganiser.DTOs;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDto {
    private int eventId;

    private String eventName;

    private LocalDate eventDate;

    private String eventLocation;

    private List<GuestsDto> eventGuests;

}

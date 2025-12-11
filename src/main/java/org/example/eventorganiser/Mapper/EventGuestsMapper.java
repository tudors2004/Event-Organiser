package org.example.eventorganiser.Mapper;

import org.example.eventorganiser.DTOs.GuestsDto;
import org.example.eventorganiser.Models.EventGuests;

public class EventGuestsMapper {

    public static GuestsDto toDto(EventGuests eventGuests) {
        return GuestsDto.builder()
                .user(eventGuests.getUser())
                .status(eventGuests.getStatus())
                .build();
    }
}

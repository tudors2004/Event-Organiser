package org.example.eventorganiser.Mapper;

import org.example.eventorganiser.DTOs.EventDto;
import org.example.eventorganiser.Models.Event;

public class EventMapper {

    public static EventDto toDto(Event event){
        String eventLocation = event.getLocation() != null
                ? event.getLocation().getAddress()
                : null;

        return EventDto.builder()
                .eventId(event.getEventId())
                .eventName(event.getEventName())
                .eventDate(event.getEventDate())
                .eventLocation(eventLocation)
                .eventGuests(event.getEventGuests().stream().map(EventGuestsMapper::toDto).toList())
                .build();
    }
}

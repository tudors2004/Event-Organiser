package org.example.eventorganiser.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.example.eventorganiser.Models.User;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateEventRequest {
    String eventName;
    LocalDate eventDate;

    String eventLocation;

    List<Integer> organizersId;
}

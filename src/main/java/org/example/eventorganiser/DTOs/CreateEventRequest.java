package org.example.eventorganiser.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.example.eventorganiser.Models.User;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class CreateEventRequest {
    String eventName;
    Date eventDate;

    String eventLocation;

    List<Integer> organizersId;
}

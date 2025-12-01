package org.example.eventorganiser.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailRequest {
    private List<String> recipients;
    private String name;
    private String eventName;
}


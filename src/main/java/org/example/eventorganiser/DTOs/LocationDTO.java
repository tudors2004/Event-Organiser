package org.example.eventorganiser.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LocationDTO {
    private Double latitude;
    private Double longitude;
    private String address;
    private String placeId;

}

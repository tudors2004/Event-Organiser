package org.example.eventorganiser.Models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Embeddable
public class Location {

    private Double latitude;
    private Double longitude;
    private String address;

    public Location() {}

    public Location(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

}

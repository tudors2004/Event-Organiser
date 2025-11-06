package org.example.eventorganiser.DTOs;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
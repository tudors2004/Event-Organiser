package org.example.eventorganiser.Models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int userId;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EventGuests> events;
    public User() {}

    public User(int userId, String name, String email, String password) {}
}

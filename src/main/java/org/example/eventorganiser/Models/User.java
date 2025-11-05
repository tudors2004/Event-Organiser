package org.example.eventorganiser.Models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    private int userId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EventGuests> events;

    @ManyToMany
    @JoinTable(
            name = "event_organisers",
            joinColumns = @JoinColumn(name = "organiser_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> organisedEvents;
    public User() {}

    public User(int userId, String name, String email, String password) {}
}

package org.example.eventorganiser.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

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

    public User(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

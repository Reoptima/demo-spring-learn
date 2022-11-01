package com.example.demo.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(name = "team_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    public Set<User> users;

    private String teamName;
}

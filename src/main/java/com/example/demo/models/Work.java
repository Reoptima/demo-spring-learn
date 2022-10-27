package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Work {

    public Work (String name, Cities city) {
        this.name = name;
        this.city = city;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Work() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    public Cities city;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<User> users = new ArrayList<>();
    private String name;

    public Cities getCity() {
        return city;
    }
    public void setCity(Cities city) {
        this.city = city;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

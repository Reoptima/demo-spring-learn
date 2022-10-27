package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cities")
public class Cities {
    public Cities(String name, Date birthdate, int citizens, double size, boolean isCapital) {
        this.name = name;
        this.birthdate = birthdate;
        this.citizens = citizens;
        this.size = size;
        this.isCapital = isCapital;
    }

    public Cities() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty()
    @NotBlank()
    @Size(min = 2, max = 50)
    private String name;
    @ManyToMany
    @JoinTable(name = "cities_user",
            joinColumns = @JoinColumn(name = "cities_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> user;

    public List<User> getusers() {
        return user;
    }

    public void setusers(List<User> users) {
        this.user = users;
    }


    @OneToOne(mappedBy = "city")
    private Work work;

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public void setCapital(boolean capital) {
        isCapital = capital;
    }

    @NotNull(message = "Please fill the input")
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthdate;
    @NotNull(message = "Please fill the input")
    @Max(value = 1000000000, message = "Please fill the input")
    @Min(100)
    private int citizens;
    @NotNull(message = "Please fill the input")
    @Max(value = 1000000000, message = "Please fill the input")
    @Min(100)

    private double size;
    private boolean isCapital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCitizens() {
        return citizens;
    }

    public void setCitizens(int citizens) {
        this.citizens = citizens;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean getIsCapital() {
        return isCapital;
    }

    public void setIsCapital(boolean capital) {
        isCapital = capital;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getBirthdateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.getBirthdate());
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}

package com.example.demo.models;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class User implements UserDetails {
    public User(String username, Date birthdate, int height, double salary, Boolean gender) {
        this.username = username;
        this.birthdate = birthdate;
        this.height = height;
        this.salary = salary;
        this.gender = gender;
    }

    public User() {
    }

    private String password;
    private boolean active;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty()
    @NotBlank()
    @Size(min = 2, max = 50)
    private String username;
    @ManyToMany
    @JoinTable(name = "cities_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "cities_id"))
    public List<Cities> cities;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.EAGER)
    public Work work;


//    @NotNull(message = "Please fill the input")
//    @Max(value = 1000000000, message = "Please fill the input")
//    @Min(100)
    private int height = 150;
//    @NotNull(message = "Please fill the input")
//    @Max(value = 1000000000, message = "Please fill the input")
//    @Min(100)
    private double salary = 50000.0;
//    @NotNull(message = "Please fill the input")
//    @PastOrPresent
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthdate;
    private Boolean gender;


    public String getPrivateString() {
        return gender ? "Мужской" : "Женский";
    }
    public String getBirthdateString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.getBirthdate());
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
}

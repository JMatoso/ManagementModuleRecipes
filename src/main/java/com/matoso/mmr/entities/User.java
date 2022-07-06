package com.matoso.mmr.entities;

import com.matoso.mmr.models.Genre;
import com.matoso.mmr.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Getter
@Setter

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Genre genre;
    private String address;
    private Role role;
    private LocalDateTime created;

    public User(int id, String name, String email, String password, Genre genre, String address, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.address = address;
        this.role = role;
        this.created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }

    public User(String name, String email, String password, Genre genre, String address, Role role) {
        this.id = new Random().nextInt(1000);
        this.name = name;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.address = address;
        this.role = role;
        this.created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}


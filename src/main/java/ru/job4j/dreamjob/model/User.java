package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private int id;
    private String email;
    private String name;
    private String password;
    private LocalDateTime created = LocalDateTime.now();

    public User(int id, String email, String name, String password, LocalDateTime created) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.created = created;
    }

    public User(int id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

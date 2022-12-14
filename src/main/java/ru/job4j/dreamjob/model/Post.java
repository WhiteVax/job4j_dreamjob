package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created;
    private boolean visible;
    private City city;

    public Post(int id, String name, String description, LocalDateTime created,
                City city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.city = city;
    }

    public Post(int id, String name, String description, LocalDateTime created,
                City city, boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.city = city;
        this.visible = visible;
    }

    public Post() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return getId() == post.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class PostStore {
    private static final AtomicInteger ID = new AtomicInteger(1);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    public PostStore() {
        posts.put(1, new Post(1, "Java Developer", "Spring, Java",
                LocalDateTime.now(), new City()));
        posts.put(2, new Post(2, "Java Developer", "Spring, Java",
                LocalDateTime.now(), new City()));
        posts.put(3, new Post(3, "Java Developer", "Spring, Java",
                LocalDateTime.now(), new City()));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(ID.getAndIncrement());
        post.setCreated(LocalDateTime.now());
        posts.put(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}

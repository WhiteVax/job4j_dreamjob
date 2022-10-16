package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Java Developer", "Spring, Java",
                LocalDate.of(2022, 5, 13)));
        posts.put(2, new Post(2, "Java Developer", "Spring, Java",
                LocalDate.of(2022, 10, 10)));
        posts.put(3, new Post(3, "Java Developer", "Spring, Java",
                LocalDate.of(2022, 12, 20)));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

}

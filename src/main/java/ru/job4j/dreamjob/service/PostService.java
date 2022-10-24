package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostDBStore;

import java.util.List;

@Service
@ThreadSafe
public class PostService {
    private final CityService cityService;
    private final PostDBStore store;

    public PostService(CityService cityService, PostDBStore store) {
        this.cityService = cityService;
        this.store = store;
    }

    public List<Post> findAll() {
        List<Post> posts = store.findAllPosts();
        posts.forEach(post -> post.setCity(
                cityService.findById(post.getCity().getId())
        ));
        return posts;
    }

    public void addPost(Post post) {
        store.addPost(post);
    }

    public Post findById(int id) {
        return store.findByIdPost(id);
    }

    public void update(Post post) {
        store.updatePost(post);
    }
}

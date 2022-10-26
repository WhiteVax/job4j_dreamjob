package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {

    private PostDBStore store;

    @BeforeEach
    void before() throws SQLException {
        new Main().loadPool().getConnection().prepareStatement("DELETE FROM post").execute();
        store = new PostDBStore(new Main().loadPool());
    }

    @Test
    public void whenAddPost() {
        var post = new Post(0, "Java Job", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addPost(post);
        var postInDb = store.findByIdPost(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    void whenFindAllPosts() {
        var first = new Post(1, "first", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        var second = new Post(2, "second", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addPost(first);
        store.addPost(second);
        assertThat(store.findAllPosts().get(0).getName()).isEqualTo(first.getName());
        assertThat(store.findAllPosts().get(1).getName()).isEqualTo(second.getName());
    }

    @Test
    public void whenUpdatePost() {
        var post = new Post(1, "first", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addPost(post);
        post.setName("second");
        store.updatePost(post);
        var postInDb = store.findByIdPost(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }
}
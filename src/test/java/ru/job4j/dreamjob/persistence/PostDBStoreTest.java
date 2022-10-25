package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class PostDBStoreTest {
    @Test
    public void whenCreatePost() {
        var store = new PostDBStore(new Main().loadPool());
        var post = new Post(0, "Java Job", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addPost(post);
        var postInDb = store.findByIdPost(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenCreateCandidate() {
        var store = new CandidateDBStore(new Main().loadPool());
        var candidate = new Candidate(0, "Ivan", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addCandidate(candidate);
        var candidateInDb = store.findByIdCandidate(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }
}
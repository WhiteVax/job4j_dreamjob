package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateDBStoreTest {

    private CandidateDBStore store;

    @BeforeEach
    void before() throws SQLException {
        new Main().loadPool().getConnection().prepareStatement("DELETE FROM candidate").execute();
        store = new CandidateDBStore(new Main().loadPool());
    }

    @Test
    public void whenAddCandidate() {
        var candidate = new Candidate(0, "Ivan", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addCandidate(candidate);
        var canInDb = store.findByIdCandidate(candidate.getId());
        assertThat(canInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    void whenFindAllCandidates() {
        var first = new Candidate(1, "Ivan", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        var second = new Candidate(2, "Dmitry", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addCandidate(first);
        store.addCandidate(second);
        assertThat(store.findAllCandidates().get(0).getName()).isEqualTo(first.getName());
        assertThat(store.findAllCandidates().get(1).getName()).isEqualTo(second.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        var candidate = new Candidate(1, "Petr", "Java, Spring, Hibernate",
                LocalDateTime.now(), new City());
        store.addCandidate(candidate);
        candidate.setName("Elena");
        store.updateCandidate(candidate);
        var postInDb = store.findByIdCandidate(candidate.getId());
        assertThat(postInDb.getName()).isEqualTo(candidate.getName());
    }
}
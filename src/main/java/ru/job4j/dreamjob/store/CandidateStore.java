package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> posts = new ConcurrentHashMap<>();

    private CandidateStore() {
        posts.put(1, new Candidate(1, "Ivan", "Junior",
                LocalDate.of(2021, 12, 13)));
        posts.put(2, new Candidate(2, "Dmitry", "Junior",
                LocalDate.of(2021, 11, 10)));
        posts.put(3, new Candidate(3, "Sergey", "Middle",
                LocalDate.of(2021, 12, 20)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return posts.values();
    }
}

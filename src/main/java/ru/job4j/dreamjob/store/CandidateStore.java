package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private static final AtomicInteger ID = new AtomicInteger(1);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "Junior",
                LocalDate.of(2021, 12, 13)));
        candidates.put(2, new Candidate(2, "Dmitry", "Junior",
                LocalDate.of(2021, 11, 10)));
        candidates.put(3, new Candidate(3, "Sergey", "Middle",
                LocalDate.of(2021, 12, 20)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(ID.getAndIncrement());
        candidate.setCreated(LocalDate.now());
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidate.setCreated(findById(candidate.getId()).getCreated());
        candidates.replace(candidate.getId(), candidate);
    }
}

package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.CandidateDBStore;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@ThreadSafe
public class CandidateService {
    private final CandidateDBStore store;
    private final CityService cityService;

    public CandidateService(CandidateDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = store.findAllCandidates();
        candidates.forEach(candidate -> candidate.setCity(
                cityService.findById(candidate.getCity().getId())
        ));
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        store.addCandidate(candidate);
    }

    public Candidate findById(int id) {
        return store.findByIdCandidate(id);
    }

    public void update(Candidate candidate) {
        store.updateCandidate(candidate);
    }
}

package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dreamjob.persistence.CandidateDBStore;

@Deprecated
@ThreadSafe
public class CandidateServiceDeprecated {
    private final CandidateDBStore store;
    private final SimpleCityService simpleCityService;

    public CandidateServiceDeprecated(CandidateDBStore store, SimpleCityService simpleCityService) {
        this.store = store;
        this.simpleCityService = simpleCityService;
    }

    /**
     * public List<Candidate> findAll() {
     * List<Candidate> candidates = store.findAllCandidates();
     * candidates.forEach(candidate -> candidate.setCity(
     * simpleCityService.findById(candidate.getCity().getId())
     * ));
     * return candidates;
     * }


    public void addCandidate(Candidate candidate) {
        store.addCandidate(candidate);
    }


     * public Candidate findById(int id) {
     * return store.findByIdCandidate(id);
     * }


    public void update(Candidate candidate) {
        store.updateCandidate(candidate);
   }
   */
}

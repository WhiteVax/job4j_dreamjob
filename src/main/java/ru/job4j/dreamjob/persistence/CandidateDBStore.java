package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Deprecated
@ThreadSafe
public class CandidateDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateStore.class);

    private static final String SELECT_ALL = "SELECT * FROM candidate ORDER BY id";
    private static final String INSERT_CANDIDATE =
            "INSERT INTO candidate (name, description, created, city_id, photo) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_CANDIDATE = "UPDATE candidate SET name = ?, description = ?, city_id = ?, photo = ? WHERE id = ?";
    private static final String SELECT_WHERE_ID = "SELECT * FROM candidate WHERE id = ? ";

    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * @deprecated
    public List<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_ALL)) {
            try (var it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findAllCandidates", e);
        }
        return candidates;
    }


    public Candidate addCandidate(Candidate candidate) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(INSERT_CANDIDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, candidate.getCity().getId());
            ps.setInt(5, candidate.getFileId());
            ps.execute();
            try (var id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in addCandidate.", e);
        }
        return candidate;
    }

    public void updateCandidate(Candidate candidate) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(UPDATE_CANDIDATE)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setInt(3, candidate.getCity().getId());
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Error in addCandidate.", e);
        }
    }

    public Candidate findByIdCandidate(int id) {
        var candidate = new Candidate();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_WHERE_ID)
        ) {
            ps.setInt(1, id);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    candidate = createCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findByIdCandidate.", e);
        }
        return candidate;
    }

     public Candidate createCandidate(ResultSet set) throws SQLException {
    return new Candidate(set.getInt("id"),
    set.getString("name"),
    set.getString("description"),
    set.getTimestamp("created").toLocalDateTime(),
    new City(set.getInt("city_id")),
    set.getBytes("photo"));
    }
     */
}

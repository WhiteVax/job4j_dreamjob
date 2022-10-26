package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class UserDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM users ORDER BY id";
    private static final String INSERT_USER =
            "INSERT INTO users(name, email, password, created) VALUES (?, ?, ?, ?)";
    private static final String EDIT_USER = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
    private static final String SELECT_WHERE_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_WHERE_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SELECT_WHERE_EMAIL_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_ALL)) {
            try (var it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(createUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findAllUsers.", e);
        }
        return users;
    }

    public User createUser(ResultSet it) throws SQLException {
        return new User(it.getInt("id"),
                it.getString("name"),
                it.getString("email"),
                it.getString("password"),
                it.getTimestamp("created").toLocalDateTime());
    }

    public Optional<User> addUser(User user) {
        Optional<User> optional = Optional.empty();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            try (var id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            optional = Optional.of(user);
        } catch (SQLException e) {
            LOG.error("Error in addUser", e);
        }
        return optional;
    }

    public void editUser(User user) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(EDIT_USER)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getEmail());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Error in editUser.", e);
        }
    }

    public User findByIdUser(int id) {
        var user = new User();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_WHERE_ID)
        ) {
            ps.setInt(1, id);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    user = createUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findByIdUser.", e);
        }
        return user;
    }

    public User findByIdEmail(String email) {
        var user = new User();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_WHERE_EMAIL)
        ) {
            ps.setString(1, email);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    user = createUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findByIdEmail.", e);
        }
        return user;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> user = Optional.empty();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_WHERE_EMAIL_PASSWORD)
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    user = Optional.of(createUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findByIdEmailAndPassword.", e);
        }
        return user;
    }
}
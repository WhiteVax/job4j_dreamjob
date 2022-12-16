package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
@ThreadSafe
public class PostDBStore {

    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());
    private static final String SELECT_ALL = "SELECT * FROM post ORDER BY id";
    private static final String INSERT_POST =
            "INSERT INTO post(name, description, created, city_id, visible) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_POST = "UPDATE post SET name = ?, description = ?, city_id = ?, visible = ? WHERE id = ?";
    private static final String SELECT_WHERE_ID = "SELECT * FROM post WHERE id = ?";

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_ALL)
        ) {
            try (var it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(createPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findAllPost.", e);
        }
        return posts;
    }

    public Post addPost(Post post) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(INSERT_POST, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, post.getCity().getId());
            ps.setBoolean(5, post.isVisible());
            ps.execute();
            try (var id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in addPost.", e);
        }
        return post;
    }

    public void updatePost(Post post) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(UPDATE_POST)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Error in updatePost.", e);
        }
    }

    public Post findByIdPost(int id) {
        var post = new Post();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement(SELECT_WHERE_ID)
        ) {
            ps.setInt(1, id);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    post = createPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findByIdPost.", e);
        }
        return post;
    }

    private Post createPost(ResultSet it) throws SQLException {
        return new Post(it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(),
                new City(it.getInt("city_id")),
                it.getBoolean("visible"));
    }
}

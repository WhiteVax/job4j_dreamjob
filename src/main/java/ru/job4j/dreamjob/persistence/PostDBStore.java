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

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT * FROM post ORDER BY id")
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
             var ps = cn.prepareStatement(
                     "INSERT INTO post(name, description, created, city_id, city_name) VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(4, post.getCity().getId());
            ps.setString(5, post.getCity().getName());
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
             var ps = cn.prepareStatement("UPDATE post SET name = ?, city_id = ?, city_name = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setInt(2, post.getCity().getId());
            ps.setString(3, post.getCity().getName());
            ps.setInt(4, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error("Error in updatePost.", e);
        }
    }

    public Post findByIdPost(int id) {
        try (var cn = pool.getConnection();
             var ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (var it = ps.executeQuery()) {
                if (it.next()) {
                    return createPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Error in findByIdPost.", e);
        }
        return null;
    }

    private Post createPost(ResultSet it) throws SQLException {
        return new Post(it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(),
                new City(it.getInt("city_id"),
                        it.getString("city_name")));
    }
}

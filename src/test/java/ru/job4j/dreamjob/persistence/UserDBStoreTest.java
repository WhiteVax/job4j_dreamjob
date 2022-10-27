package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.User;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class UserDBStoreTest {

    private UserDBStore store;

    @BeforeEach
    void before() throws SQLException {
        new Main().loadPool().getConnection().prepareStatement("DELETE FROM users").execute();
        store = new UserDBStore(new Main().loadPool());
    }

    @Test
    void whenFindAllUsers() {
        var first = new User(1, "first", "first@email.com", "asd23x", LocalDateTime.now());
        var second = new User(2, "second", "second@email.com", "asd23x", LocalDateTime.now());
        store.addUser(first);
        store.addUser(second);
        assertThat(store.findAllUsers().get(0).getName()).isEqualTo(first.getName());
        assertThat(store.findAllUsers().get(1).getName()).isEqualTo(second.getName());
    }

    @Test
    void whenEditUserAndFindById() {
        var first = new User(1, "first", "first@email.com", "asd23x", LocalDateTime.now());
        store.addUser(first);
        first.setName("second");
        store.editUser(first);
        assertThat(store.findByIdUser(first.getId()).get().getName()).isEqualTo(first.getName());
    }

    @Test
    void whenFindByIdEmail() {
        var first = new User(1, "first", "first@email.com", "asd23x", LocalDateTime.now());
        store.addUser(first);
        assertThat(store.findByIdEmail("first@email.com").get().getEmail()).isEqualTo(first.getEmail());
    }

    @Test
    void whenFindUserByEmailAndPassword() {
        var first = new User(1, "first", "first@email.com", "asd23x", LocalDateTime.now());
        store.addUser(first);
        assertThat(store.findUserByEmailAndPassword("first@email.com", "asd23x")
                .get().getEmail()).isEqualTo(first.getEmail());
    }
}
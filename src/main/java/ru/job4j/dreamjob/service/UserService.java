package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.persistence.UserDBStore;

import java.util.List;
import java.util.Optional;

@Service
@ThreadSafe
public class UserService {
    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public List<User> findAllUser() {
        return store.findAllUsers();
    }

    public Optional<User> addUser(User user) {
        return store.addUser(user);
    }

    public User findByIdUser(int id) {
        return store.findByIdUser(id);
    }

    public User findByIdEmail(String email) {
        return store.findByIdEmail(email);
    }

    public void editUser(User user) {
        store.editUser(user);
    }
}

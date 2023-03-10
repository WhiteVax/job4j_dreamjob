package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        if (!users.isEmpty()) {
            for (var user : users) {
                sql2oUserRepository.deleteByEmail(user.getEmail());
            }
        }
    }

    @Test
    public void whenSaveSuccessful() {
        var user = new User(1, "test@email.ru", "Petr", "pas234#dcvbs", LocalDateTime.now());
        var rsl = sql2oUserRepository.save(user);
        assertThat(rsl.isPresent()).isTrue();
    }

    @Test
    public void whenFailSave() {
        var first = new User(1, "test@email.ru", "Ivan", "pas234#dcvbs", LocalDateTime.now());
        var second = new User(1, "test@email.ru", "Petr", "pas234#dcvbs", LocalDateTime.now());
        sql2oUserRepository.save(first);
        var rsl = sql2oUserRepository.save(second);
        assertThat(rsl.isPresent()).isFalse();
    }

    @Test
    void whenSaveAngGetSameByEmailAndPassword() {
        var user = new User(1, "test@email.ru", "testSave", "pas234#dcvbs", LocalDateTime.now());
        sql2oUserRepository.save(user);
        assertThat(sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get().getName())
                .isEqualTo(user.getName());
    }
}
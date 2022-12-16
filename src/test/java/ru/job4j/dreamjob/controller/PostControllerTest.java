package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @BeforeEach
    void before() throws SQLException {
        try (var cn = new Main().loadPool().getConnection()) {
            cn.prepareStatement("DELETE FROM post").execute();
        }
    }

    @Test
    public void whenCreatePost() {
        var input = new Post(1, "New post", "Description",
                LocalDateTime.now(), new City());
        var postService = mock(PostService.class);
        var cityService = mock(CityService.class);
        var postController = new PostController(
                postService,
                cityService
        );
        var page = postController.createPost(input, false);
        verify(postService).addPost(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    void whenUpdatePost() {
        var first = new Post(1, "first post", "Description",
                LocalDateTime.now(), new City());
        var second = new Post(1, "second post", "Description",
                LocalDateTime.now(), new City());
        var postService = mock(PostService.class);
        var cityService = mock(CityService.class);
        var postController = new PostController(
                postService,
                cityService
        );
        postController.createPost(first, false);
        var page = postController.updatePost(second, false);
        verify(postService).update(second);
        assertThat("redirect:/posts", is(page));
    }

    @Test
    public void whenFindAllPost() {
        List<Post> posts = Arrays.asList(
                new Post(1, "first", "Description",
                        LocalDateTime.now(), new City()),
                new Post(2, "second", "Description",
                        LocalDateTime.now(), new City())
        );
        var model = mock(Model.class);
        var postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        var cityService = mock(CityService.class);
        var postController = new PostController(
                postService,
                cityService
        );
        var page = postController.posts(model, new MockHttpSession());
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }
}
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository repository;

    @Test
    @Transactional
    @Rollback
    public void testCreate() throws Exception {
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"emily@pm.me\", \"password\": \"dogs\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", instanceOf(Number.class) ));
    }

    @Test
    @Transactional
    @Rollback
    public void testListAll() throws Exception {
        User user = new User();
        user.setEmail("emily@pm.me");
        user.setPassword("dogs");
        repository.save(user);

        User user2 = new User();
        user2.setEmail("jake@pm.me");
        user2.setPassword("cats");
        repository.save(user2);

        MockHttpServletRequestBuilder request = get("/users")
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(user.getId().intValue()) ))
                .andExpect(jsonPath("$[1].id", equalTo(user2.getId().intValue()) ));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void testGetOne() throws Exception {
//        User user = new User();
//        user.setEmail("email@email.com");
//        user.setPassword("1234");
//        repository.save(user);
//
//        MockHttpServletRequestBuilder request = get("/users/1")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", instanceOf(Number.class) ));
//    }
//    @Test
//    @Transactional
//    @Rollback
//    public void testDelete() throws Exception {
//        user user = new user();
//        user.setId(1L);
//        user.setEmail("Juggling");
//        user.setPassword(new Date(2020-12-10));
//        repository.save(user);
//
//        MockHttpServletRequestBuilder request = delete("/users/1")
//            .contentType(MediaType.APPLICATION_JSON);
//
//        this.mvc.perform(request)
//            .andExpect(status().isOk());
//            .andExpect(jsonPath("$.id", instanceOf(Number.class) ));

    @Test
    @Transactional
    @Rollback
    public void testPatch() throws Exception {
        MockHttpServletRequestBuilder request = patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"emily@pm.me\", \"password\": \"dogs!!!\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.email", equalTo("emily@pm.me")));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void testAuthenticate() throws Exception {
//        MockHttpServletRequestBuilder request = post("/users/authenticate")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"email\": \"emily@pm.me\", \"password\": \"dogs!\"}");
//
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("authenticated", equalTo(false)));
//    }
}
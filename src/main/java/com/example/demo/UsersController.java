package com.example.demo;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    @JsonView(Views.ReturnView.class)
    public Iterable<User> all() {
        return this.repository.findAll();
    }

    @PostMapping("")
    @JsonView(Views.ReturnView.class)
    public User create(@RequestBody User user) {
        return this.repository.save(user);
    }

    @GetMapping("/{id}")
    @JsonView(Views.ReturnView.class)
    public Optional<User> getUser(@PathVariable Long id) {
        return this.repository.findById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        this.repository.deleteById(id);
        return String.format("{\n \"count\": " + this.repository.count() + "\n}");
    }

    @PatchMapping(path="/{id}", consumes="application/json")
    @JsonView(Views.ReturnView.class)
    public User edit(@PathVariable Long id, @RequestBody User user) {
        User changeThis = new User();
        changeThis.setId(id);
        changeThis.setEmail(user.getEmail());
        changeThis.setPassword(user.getPassword());
        return this.repository.save(changeThis);
    }

//    @PostMapping(path="/authenticate", consumes="application/json")
//    public String authenticate(@RequestBody User user) {
//        String email = user.getEmail();
//        String password = user.getPassword();
//        Boolean success = this.repository.authenticate(email, password);
//        if (success) {
//            return String.format("{\n \"authenticated\": " + true + ",\n" + user + "\n}");
//        } else {
//            return String.format("{\n \"authenticated\": " + false + "\n}");
//        }
//    }

}

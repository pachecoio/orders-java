package io.pacheco.orders.controller;

import io.pacheco.orders.model.User;
import io.pacheco.orders.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public @ResponseBody
    Iterable<User> getAll(@RequestParam(value = "role", required = false) String role) {
        if(role != null) return userRepository.findByRole(role);
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity create(@RequestBody User input) {
        User user = userRepository.save(input);
        return ResponseEntity.status(201).body(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity update(@PathVariable("id") Integer id, @RequestBody User input) {
        if (!userRepository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);

        input.setId(id);
        User user = userRepository.save(input);

        return ResponseEntity.status(200).body(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        if (!userRepository.findById(id).isPresent()) return ResponseEntity.status(404).body(null);
        userRepository.deleteById(id);
        return ResponseEntity.status(202).body(null);
    }

}

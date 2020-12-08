package com.kbytech.init.web;

import com.kbytech.init.domain.User;
import com.kbytech.init.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class apiUserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/{id}")
    public User Detaile(@PathVariable Long id)
    {
        return userRepository.findById(id).get();
    }

}

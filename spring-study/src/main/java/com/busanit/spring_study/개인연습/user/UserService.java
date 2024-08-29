package com.busanit.spring_study.개인연습.user;

import com.busanit.spring_study.개인연습.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(String username, String emil, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(emil);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }

    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}

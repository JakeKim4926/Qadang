package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User findUser(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public String findUserName(Long userId) {
        User user = findUser(userId);
        return user.getUserName();
    }
}

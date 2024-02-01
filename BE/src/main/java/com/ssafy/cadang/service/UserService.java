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


    // 회원 정보 입력

    // 회원 정보 조회

    // 회원 권장량 조회

    // 회원 당, 카페인 최고 섭취량 및 날짜 조회

    //회원 정보 수정

    // 회원 탈퇴
}

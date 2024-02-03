package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.MaxRecord;
import com.ssafy.cadang.dto.UserAmount;
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

    public void delete(Long userId) {
        userRepository.deleteByUserId(userId);
    }

    // 유저 권장량 조회
    public UserAmount userAmount(User user) {

        UserAmount amount = UserAmount.builder()
                .userCaffeine(user.getUserCaffeine())
                .userSugar(user.getUserSugar())
                .build();

        return amount;
    }

    // 유저 회원, 당 카페인 최고 섭취량 및 날짜 조회
    public MaxRecord userMax(User user) {

        MaxRecord max = MaxRecord.builder()
                .maxSugarValue(user.getMaxSugarValue())
                .maxCaffenineValue(user.getMaxCaffeineValue())
                .maxSugarDate(user.getMaxSugarDate())
                .maxCaffeineDate((user.getMaxCaffeineDate()))
                .build();
        return max;
    }


}

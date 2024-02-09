package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.User;
import com.ssafy.cadang.dto.MaxRecord;
import com.ssafy.cadang.dto.UserAmount;
import com.ssafy.cadang.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

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

    // 권장량 계산
    public void detailInfo(User user) {

        LocalDate now = LocalDate.now();
        int age = now.getYear() - user.getUserBirth() + 1; // 나이계산
        // 카페인
        if (age <= 11) { // 아동 : 0
            user.setUserCaffeine(0);
        } else if (age > 11 && age < 20) { // 청소년 : 몸무게 * 2.5mg
            user.setUserCaffeine(2.5 * user.getUserWeight());
        } else {
            user.setUserCaffeine(400); // 성인 최대 권장량 : 400
        }

        double health = 0; // 활동량 점수

        System.out.println("현재연도 : " + now.getYear());
        System.out.println("getUserBirth : " + user.getUserBirth());
        System.out.println(age);

        // 당
        if (age < 20) {
            if (user.getUserGender() == 1) { // 청소년 & 남자

                switch (user.getUserHealth()) {
                    case 1:
                        health = 1.0;
                        break;
                    case 2:
                        health = 1.13;
                        break;
                    case 3:
                        health = 1.26;
                        break;
                    case 4:
                        health = 1.42;
                        break;
                }

                double energy = 88.5 - 61.9 * age + health * (26.7 * user.getUserWeight() + 903 * user.getUserHeight());
                user.setUserSugar(energy * 0.1/4);
            } else { // 청소년 & 여자

                switch (user.getUserHealth()) {
                    case 1:
                        health = 1.0;
                        break;
                    case 2:
                        health = 1.16;
                        break;
                    case 3:
                        health = 1.31;
                        break;
                    case 4:
                        health = 1.56;
                        break;
                }

                double energy = 135.3 - 30.8 * age + health * (10.0 * user.getUserWeight() + 934 * user.getUserHeight() / 100);
                user.setUserSugar(energy * 0.1/4);
            }
        } else { // 성인
            if (user.getUserGender() == 1) { // 성인 & 남자

                switch (user.getUserHealth()) {
                    case 1:
                        health = 1.0;
                        break;
                    case 2:
                        health = 1.11;
                        break;
                    case 3:
                        health = 1.25;
                        break;
                    case 4:
                        health = 1.48;
                        break;
                }

                double energy = 662 - 9.53 * age + health * (15.91 * user.getUserWeight() + 539.6 * user.getUserHeight() / 100);
                user.setUserSugar(energy * 0.1/4);
            } else { // 성인 & 여자

                switch (user.getUserHealth()) {
                    case 1:
                        health = 1.0;
                        break;
                    case 2:
                        health = 1.12;
                        break;
                    case 3:
                        health = 1.27;
                        break;
                    case 4:
                        health = 1.45;
                        break;
                }

                double energy = 354 - 6.91 * age + health * (9.36 * user.getUserWeight() + 726 * user.getUserHeight() / 100);
                user.setUserSugar(energy * 0.1/4);
            }
        }

        update(user);

    }


}

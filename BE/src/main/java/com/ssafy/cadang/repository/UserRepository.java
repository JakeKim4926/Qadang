package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Accumulates;
import com.ssafy.cadang.domain.NickName;
import com.ssafy.cadang.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUserId(Long userId);

    public void deleteByUserId(Long userId);

    public String findByUserName(String userName);

}

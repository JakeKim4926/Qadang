package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NicknameRepository extends JpaRepository<Nickname, Long> {

    @Query(value = "SELECT n.adjective FROM Nickname n ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findRandomAdjective();

    @Query(value = "SELECT n.noun FROM Nickname n ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findRandomNoun();
}

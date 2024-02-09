package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.NickName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NickNameRepository extends JpaRepository<NickName, Long> {

    @Query(value = "SELECT adjective FROM NickName ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findRandomAdjective();

    @Query(value = "SELECT noun FROM NickName ORDER BY RAND() LIMIT 1", nativeQuery = true)
    String findRandomNoun();
}

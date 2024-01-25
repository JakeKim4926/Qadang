package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Cafes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafes,Long> {
}

package com.ssafy.cadang.repository;

import com.ssafy.cadang.domain.Drinks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drinks, Long> {
    Drinks findByDrinkId(Long id);

    List<Drinks> findByCafeId(Long cafeId);

    List<Drinks> findByDrinkFullNameContaining(String keyword);
}

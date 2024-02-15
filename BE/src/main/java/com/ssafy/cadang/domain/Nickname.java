package com.ssafy.cadang.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Nickname {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String adjective;
    private String noun;
}

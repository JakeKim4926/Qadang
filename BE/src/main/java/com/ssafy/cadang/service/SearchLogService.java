package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.SearchLogs;
import com.ssafy.cadang.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchLogService {
    private final SearchRepository searchRepository;

    public void log(Long userId,String keyword) {
        searchRepository.save(SearchLogs.builder()
                        .userId(userId)
                        .searchKeyword(keyword)
                        .build());
    }

    public List<String> readRank() {
        return searchRepository.findByRank();
    }
}

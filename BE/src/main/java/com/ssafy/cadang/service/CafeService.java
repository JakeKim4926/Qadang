package com.ssafy.cadang.service;

import com.ssafy.cadang.domain.Cafes;
import com.ssafy.cadang.repository.CafeRepository;
import com.ssafy.cadang.response.ChatRoomResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

    public List<ChatRoomResponseDTO> getList() {
        List<Cafes> cafes = cafeRepository.findAll();
        List<ChatRoomResponseDTO> result = new ArrayList<>();
        for(Cafes cafe : cafes){
            result.add(
                    ChatRoomResponseDTO.builder()
                            .chatroomId(cafe.getCafeId())
                            .chatroomName(cafe.getCafeName())
                            .build()
            );
        }
        return result;
    }

    public List<Cafes> cafeList() {
        return cafeRepository.findAll();
    }
}

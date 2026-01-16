package com.gamesite.service;

import com.gamesite.dto.GameTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameTypeService {

    public List<GameTypeDto> findAll() {
        return List.of(); // vide pour l'instant
    }

    public Optional<GameTypeDto> findById(Long id) {
        return Optional.empty(); // vide pour l'instant
    }

    public GameTypeDto save(GameTypeDto dto) {
        return dto; // juste pour que ça compile
    }

    public void delete(Long id) {
        // rien pour l'instant
    }
}

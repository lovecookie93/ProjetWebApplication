package com.gamesite.service;

import com.gamesite.dto.GameDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    public List<GameDto> findAll() {
        return List.of(); // vide pour l'instant
    }

    public Optional<GameDto> findById(Long id) {
        return Optional.empty(); // vide pour l'instant
    }

    public List<GameDto> findByPlayerId(Long playerId) {
        return List.of(); // vide pour l'instant
    }

    public List<GameDto> findByGameTypeId(Long gameTypeId) {
        return List.of(); // vide pour l'instant
    }

    public GameDto save(GameDto dto) {
        return dto; // juste pour que ça compile
    }

    public void delete(Long id) {
        // rien pour l'instant
    }
}

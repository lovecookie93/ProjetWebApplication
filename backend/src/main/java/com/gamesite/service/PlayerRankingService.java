package com.gamesite.service;

import com.gamesite.dto.PlayerRankingDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerRankingService {

    public List<PlayerRankingDto> findByPlayerId(Long playerId) { return List.of(); }
    public List<PlayerRankingDto> findByGameTypeId(Long gameTypeId) { return List.of(); }
    public List<PlayerRankingDto> findAll() { return List.of(); }
    public Optional<PlayerRankingDto> findById(Long id) { return Optional.empty(); }
    public PlayerRankingDto save(PlayerRankingDto dto) { return dto; }
    public void delete(Long id) { }
}

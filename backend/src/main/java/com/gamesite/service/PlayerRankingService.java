package com.gamesite.service;

import com.gamesite.dto.PlayerRankingDto;
import com.gamesite.entity.PlayerRanking;
import com.gamesite.entity.Player;
import com.gamesite.entity.GameType;
import com.gamesite.repository.PlayerRankingRepository;
import com.gamesite.repository.PlayerRepository;
import com.gamesite.repository.GameTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerRankingService {
    
    @Autowired
    private PlayerRankingRepository playerRankingRepository;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private GameTypeRepository gameTypeRepository;
    
    public List<PlayerRankingDto> findAll() {
        return playerRankingRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public Optional<PlayerRankingDto> findById(Long id) {
        return playerRankingRepository.findById(id)
            .map(this::convertToDto);
    }
    
    public List<PlayerRankingDto> findByPlayerId(Long playerId) {
        return playerRankingRepository.findByPlayerId(playerId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public List<PlayerRankingDto> findByGameTypeId(Long gameTypeId) {
        return playerRankingRepository.findByGameTypeId(gameTypeId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public PlayerRankingDto save(PlayerRankingDto dto) {
        PlayerRanking ranking = convertToEntity(dto);
        PlayerRanking saved = playerRankingRepository.save(ranking);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        playerRankingRepository.deleteById(id);
    }
    
    // Conversion Entity <-> DTO
    private PlayerRankingDto convertToDto(PlayerRanking entity) {
        PlayerRankingDto dto = new PlayerRankingDto();
        dto.setId(entity.getId());
        dto.setPoints(entity.getPoints());
        dto.setDateAchieved(entity.getDateAchieved());
        dto.setPlayerId(entity.getPlayer().getId());
        dto.setGameTypeId(entity.getGameType().getId());
        return dto;
    }
    
    private PlayerRanking convertToEntity(PlayerRankingDto dto) {
        PlayerRanking entity = new PlayerRanking();
        
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        
        entity.setPoints(dto.getPoints());
        entity.setDateAchieved(dto.getDateAchieved());
        
        Player player = playerRepository.findById(dto.getPlayerId())
            .orElseThrow(() -> new RuntimeException("Player not found"));
        entity.setPlayer(player);
        
        GameType gameType = gameTypeRepository.findById(dto.getGameTypeId())
            .orElseThrow(() -> new RuntimeException("GameType not found"));
        entity.setGameType(gameType);
        
        return entity;
    }
}
package com.gamesite.service;

import com.gamesite.dto.GameDto;
import com.gamesite.entity.Game;
import com.gamesite.entity.Player;
import com.gamesite.entity.GameType;
import com.gamesite.repository.GameRepository;
import com.gamesite.repository.PlayerRepository;
import com.gamesite.repository.GameTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GameService {
    
    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private GameTypeRepository gameTypeRepository;
    
    public List<GameDto> findAll() {
        return gameRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public Optional<GameDto> findById(Long id) {
        return gameRepository.findById(id)
            .map(this::convertToDto);
    }
    
    public List<GameDto> findByPlayerId(Long playerId) {
        return gameRepository.findByPlayerId(playerId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public List<GameDto> findByGameTypeId(Long gameTypeId) {
        return gameRepository.findByGameTypeId(gameTypeId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public GameDto save(GameDto dto) {
        Game game = convertToEntity(dto);
        Game saved = gameRepository.save(game);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        gameRepository.deleteById(id);
    }
    
    // Conversion Entity <-> DTO
    private GameDto convertToDto(Game entity) {
        GameDto dto = new GameDto();
        dto.setId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setScorePlayer1(entity.getScorePlayer1());
        dto.setScorePlayer2(entity.getScorePlayer2());
        dto.setPlayer1Id(entity.getPlayer1() != null ? entity.getPlayer1().getId() : null);
        dto.setPlayer2Id(entity.getPlayer2() != null ? entity.getPlayer2().getId() : null);
        dto.setGameTypeId(entity.getGameType() != null ? entity.getGameType().getId() : null);
        return dto;
    }
    
    private Game convertToEntity(GameDto dto) {
        Game entity = new Game();
        
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setScorePlayer1(dto.getScorePlayer1());
        entity.setScorePlayer2(dto.getScorePlayer2());
        
        // Récupérer les entités liées
        if (dto.getPlayer1Id() != null) {
            Player player1 = playerRepository.findById(dto.getPlayer1Id())
                .orElseThrow(() -> new RuntimeException("Player1 not found"));
            entity.setPlayer1(player1);
        }
        
        if (dto.getPlayer2Id() != null) {
            Player player2 = playerRepository.findById(dto.getPlayer2Id())
                .orElseThrow(() -> new RuntimeException("Player2 not found"));
            entity.setPlayer2(player2);
        }
        
        if (dto.getGameTypeId() != null) {
            GameType gameType = gameTypeRepository.findById(dto.getGameTypeId())
                .orElseThrow(() -> new RuntimeException("GameType not found"));
            entity.setGameType(gameType);
        }
        
        return entity;
    }
}
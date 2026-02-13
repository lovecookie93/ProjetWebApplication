package com.gamesite.service;

import com.gamesite.dto.GameTypeDto;
import com.gamesite.entity.GameType;
import com.gamesite.repository.GameTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class GameTypeService {
    
    @Autowired
    private GameTypeRepository gameTypeRepository;
    
    public List<GameTypeDto> findAll() {
        return gameTypeRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public Optional<GameTypeDto> findById(Long id) {
        return gameTypeRepository.findById(id)
            .map(this::convertToDto);
    }
    
    public GameTypeDto save(GameTypeDto dto) {
        GameType gameType = convertToEntity(dto);
        GameType saved = gameTypeRepository.save(gameType);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        gameTypeRepository.deleteById(id);
    }
    
    // Conversion Entity <-> DTO
    private GameTypeDto convertToDto(GameType entity) {
        GameTypeDto dto = new GameTypeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
    
    private GameType convertToEntity(GameTypeDto dto) {
        GameType entity = new GameType();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        return entity;
    }
}
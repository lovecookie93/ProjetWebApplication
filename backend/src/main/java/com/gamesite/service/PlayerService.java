package com.gamesite.service;

import com.gamesite.dto.PlayerDto;
import com.gamesite.entity.Player;
import com.gamesite.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;
    
    public List<PlayerDto> findAll() {
        return playerRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public Optional<PlayerDto> findById(Long id) {
        return playerRepository.findById(id)
            .map(this::convertToDto);
    }
    
    public PlayerDto save(PlayerDto dto) {
        Player player = convertToEntity(dto);
        Player saved = playerRepository.save(player);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
    
    // Conversion Entity <-> DTO
    private PlayerDto convertToDto(Player entity) {
        PlayerDto dto = new PlayerDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        return dto;
    }
    
    private Player convertToEntity(PlayerDto dto) {
        Player entity = new Player();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}

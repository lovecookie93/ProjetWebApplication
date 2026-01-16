package com.gamesite.service;

import com.gamesite.dto.PlayerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    public List<PlayerDto> findAll() { return List.of(); }
    public Optional<PlayerDto> findById(Long id) { return Optional.empty(); }
    public PlayerDto save(PlayerDto dto) { return dto; }
    public void delete(Long id) { }
}

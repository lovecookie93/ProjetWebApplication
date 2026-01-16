package com.gamesite.controller;

import com.gamesite.dto.PlayerDto;
import com.gamesite.dto.PlayerRankingDto;
import com.gamesite.dto.GameDto;
import com.gamesite.service.PlayerService;
import com.gamesite.service.PlayerRankingService;
import com.gamesite.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private PlayerRankingService playerRankingService;
    
    @Autowired
    private GameService gameService;
    
    @GetMapping
    public List<PlayerDto> getAllPlayers() {
        return playerService.findAll();
    }
    
    @GetMapping("/{id}")
    public PlayerDto getPlayerById(@PathVariable Long id) {
        return playerService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/{id}/rankings")
    public List<PlayerRankingDto> getPlayerRankings(@PathVariable Long id) {
        return playerRankingService.findByPlayerId(id);
    }
    
    @GetMapping("/{id}/games")
    public List<GameDto> getPlayerGames(@PathVariable Long id) {
        return gameService.findByPlayerId(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        return playerService.save(playerDto);
    }
    
    @PutMapping("/{id}")
    public PlayerDto updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerDto playerDto) {
        if (!playerService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        playerDto.setId(id);
        return playerService.save(playerDto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable Long id) {
        if (!playerService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        playerService.delete(id);
    }
}

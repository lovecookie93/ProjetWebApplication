package com.gamesite.controller;

import com.gamesite.dto.PlayerRankingDto;
import com.gamesite.service.PlayerRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rankings")
@CrossOrigin(origins = "*")
public class PlayerRankingController {

    @Autowired
    private PlayerRankingService playerRankingService;
    
    @GetMapping
    public List<PlayerRankingDto> getAllRankings() {
        return playerRankingService.findAll();
    }
    
    @GetMapping("/{id}")
    public PlayerRankingDto getRankingById(@PathVariable Long id) {
        return playerRankingService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/player/{playerId}")
    public List<PlayerRankingDto> getRankingsByPlayer(@PathVariable Long playerId) {
        return playerRankingService.findByPlayerId(playerId);
    }
    
    @GetMapping("/game-type/{gameTypeId}")
    public List<PlayerRankingDto> getRankingsByGameType(@PathVariable Long gameTypeId) {
        return playerRankingService.findByGameTypeId(gameTypeId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerRankingDto createRanking(@Valid @RequestBody PlayerRankingDto rankingDto) {
        return playerRankingService.save(rankingDto);
    }
    
    @PutMapping("/{id}")
    public PlayerRankingDto updateRanking(@PathVariable Long id, @Valid @RequestBody PlayerRankingDto rankingDto) {
        if (!playerRankingService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        rankingDto.setId(id);
        return playerRankingService.save(rankingDto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRanking(@PathVariable Long id) {
        if (!playerRankingService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        playerRankingService.delete(id);
    }
}

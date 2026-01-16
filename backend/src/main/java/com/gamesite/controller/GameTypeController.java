package com.gamesite.controller;

import com.gamesite.dto.GameTypeDto;
import com.gamesite.dto.GameDto;
import com.gamesite.dto.PlayerRankingDto;
import com.gamesite.service.GameTypeService;
import com.gamesite.service.GameService;
import com.gamesite.service.PlayerRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/game-types")
@CrossOrigin(origins = "*")
public class GameTypeController {

    @Autowired
    private GameTypeService gameTypeService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private PlayerRankingService playerRankingService;
    
    @GetMapping
    public List<GameTypeDto> getAllGameTypes() {
        return gameTypeService.findAll();
    }
    
    @GetMapping("/{id}")
    public GameTypeDto getGameTypeById(@PathVariable Long id) {
        return gameTypeService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/{id}/games")
    public List<GameDto> getGamesByGameType(@PathVariable Long id) {
        return gameService.findByGameTypeId(id);
    }
    
    @GetMapping("/{id}/rankings")
    public List<PlayerRankingDto> getRankingsByGameType(@PathVariable Long id) {
        return playerRankingService.findByGameTypeId(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameTypeDto createGameType(@Valid @RequestBody GameTypeDto gameTypeDto) {
        return gameTypeService.save(gameTypeDto);
    }
    
    @PutMapping("/{id}")
    public GameTypeDto updateGameType(@PathVariable Long id, @Valid @RequestBody GameTypeDto gameTypeDto) {
        if (!gameTypeService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameTypeDto.setId(id);
        return gameTypeService.save(gameTypeDto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameType(@PathVariable Long id) {
        if (!gameTypeService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameTypeService.delete(id);
    }
}

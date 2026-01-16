package com.gamesite.controller;

import com.gamesite.dto.GameDto;
import com.gamesite.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameService gameService;
    
    @GetMapping
    public List<GameDto> getAllGames() {
        return gameService.findAll();
    }
    
    @GetMapping("/{id}")
    public GameDto getGameById(@PathVariable Long id) {
        return gameService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/player/{playerId}")
    public List<GameDto> getGamesByPlayer(@PathVariable Long playerId) {
        return gameService.findByPlayerId(playerId);
    }
    
    @GetMapping("/game-type/{gameTypeId}")
    public List<GameDto> getGamesByGameType(@PathVariable Long gameTypeId) {
        return gameService.findByGameTypeId(gameTypeId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createGame(@Valid @RequestBody GameDto gameDto) {
        return gameService.save(gameDto);
    }
    
    @PutMapping("/{id}")
    public GameDto updateGame(@PathVariable Long id, @Valid @RequestBody GameDto gameDto) {
        if (!gameService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameDto.setId(id);
        return gameService.save(gameDto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable Long id) {
        if (!gameService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        gameService.delete(id);
    }
}

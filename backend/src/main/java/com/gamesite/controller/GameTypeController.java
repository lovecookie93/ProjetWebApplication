package com.gamesite.controller;

import com.gamesite.dto.GameTypeDto;
import com.gamesite.dto.GameDto;
import com.gamesite.dto.PlayerRankingDto;
import com.gamesite.service.GameTypeService;
import com.gamesite.service.GameService;
import com.gamesite.service.PlayerRankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/game-types")
@CrossOrigin(origins = "*")
@Tag(name = "Game Types", description = "Gestion des types de jeu - Catégories de jeux disponibles (Chess, Pong, Snake, etc.)")
public class GameTypeController {

    @Autowired
    private GameTypeService gameTypeService;
    
    @Autowired
    private GameService gameService;
    
    @Autowired
    private PlayerRankingService playerRankingService;
    
    @Operation(
        summary = "Récupérer tous les types de jeu",
        description = "Retourne la liste complète de tous les types de jeu disponibles sur la plateforme"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste des types de jeu récupérée avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameTypeDto.class))
        )
    })
    @GetMapping
    public List<GameTypeDto> getAllGameTypes() {
        return gameTypeService.findAll();
    }
    
    @Operation(
        summary = "Récupérer un type de jeu par ID",
        description = "Retourne les informations d'un type de jeu spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Type de jeu trouvé",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameTypeDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Type de jeu non trouvé",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public GameTypeDto getGameTypeById(
            @Parameter(description = "ID du type de jeu à récupérer", required = true, example = "1")
            @PathVariable Long id) {
        return gameTypeService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GameType not found"));
    }
    
    @Operation(
        summary = "Récupérer les parties d'un type de jeu",
        description = "Retourne toutes les parties jouées pour un type de jeu spécifique (ex: toutes les parties de Chess)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Parties récupérées avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        )
    })
    @GetMapping("/{id}/games")
    public List<GameDto> getGamesByGameType(
            @Parameter(description = "ID du type de jeu", required = true, example = "1")
            @PathVariable Long id) {
        return gameService.findByGameTypeId(id);
    }
    
    @Operation(
        summary = "Récupérer le classement d'un type de jeu",
        description = "Retourne le classement de tous les joueurs pour un type de jeu spécifique (ex: top joueurs de Chess)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Classement récupéré avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        )
    })
    @GetMapping("/{id}/rankings")
    public List<PlayerRankingDto> getRankingsByGameType(
            @Parameter(description = "ID du type de jeu", required = true, example = "1")
            @PathVariable Long id) {
        return playerRankingService.findByGameTypeId(id);
    }
    
    @Operation(
        summary = "Créer un nouveau type de jeu",
        description = "Ajoute un nouveau type de jeu à la plateforme (ex: Pong, Snake, Tetris)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Type de jeu créé avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameTypeDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Nom de type de jeu déjà existant",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameTypeDto createGameType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Informations du type de jeu à créer (ne pas fournir l'ID)",
                required = true,
                content = @Content(schema = @Schema(implementation = GameTypeDto.class))
            )
            @Valid @RequestBody GameTypeDto gameTypeDto) {
        return gameTypeService.save(gameTypeDto);
    }
    
    @Operation(
        summary = "Mettre à jour un type de jeu",
        description = "Modifie le nom d'un type de jeu existant"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Type de jeu mis à jour avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameTypeDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Type de jeu non trouvé",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public GameTypeDto updateGameType(
            @Parameter(description = "ID du type de jeu à modifier", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody GameTypeDto gameTypeDto) {
        if (!gameTypeService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GameType not found");
        }
        gameTypeDto.setId(id);
        return gameTypeService.save(gameTypeDto);
    }
    
    @Operation(
        summary = "Supprimer un type de jeu",
        description = "Supprime un type de jeu et toutes ses données associées (parties, classements)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Type de jeu supprimé avec succès"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Type de jeu non trouvé",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGameType(
            @Parameter(description = "ID du type de jeu à supprimer", required = true, example = "1")
            @PathVariable Long id) {
        if (!gameTypeService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GameType not found");
        }
        gameTypeService.delete(id);
    }
}
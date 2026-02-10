package com.gamesite.controller;

import com.gamesite.dto.GameDto;
import com.gamesite.service.GameService;
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
@RequestMapping("/api/games")
@CrossOrigin(origins = "*")
@Tag(name = "Games", description = "Gestion des parties - Création et historique des matchs")
public class GameController {

    @Autowired
    private GameService gameService;
    
    @Operation(
        summary = "Récupérer toutes les parties",
        description = "Retourne la liste complète de toutes les parties jouées sur la plateforme"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste des parties récupérée avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        )
    })
    @GetMapping
    public List<GameDto> getAllGames() {
        return gameService.findAll();
    }
    
    @Operation(
        summary = "Récupérer une partie par ID",
        description = "Retourne les détails complets d'une partie spécifique (joueurs, scores, durée)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Partie trouvée",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Partie non trouvée",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public GameDto getGameById(
            @Parameter(description = "ID de la partie à récupérer", required = true, example = "1")
            @PathVariable Long id) {
        return gameService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }
    
    @Operation(
        summary = "Récupérer les parties d'un joueur",
        description = "Retourne toutes les parties auxquelles un joueur a participé (en tant que player1 ou player2)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Parties du joueur récupérées avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        )
    })
    @GetMapping("/player/{playerId}")
    public List<GameDto> getGamesByPlayer(
            @Parameter(description = "ID du joueur", required = true, example = "1")
            @PathVariable Long playerId) {
        return gameService.findByPlayerId(playerId);
    }
    
    @Operation(
        summary = "Récupérer les parties par type de jeu",
        description = "Retourne toutes les parties d'un type de jeu spécifique (ex: toutes les parties de Chess)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Parties du type de jeu récupérées avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        )
    })
    @GetMapping("/game-type/{gameTypeId}")
    public List<GameDto> getGamesByGameType(
            @Parameter(description = "ID du type de jeu", required = true, example = "1")
            @PathVariable Long gameTypeId) {
        return gameService.findByGameTypeId(gameTypeId);
    }
    
    @Operation(
        summary = "Créer une nouvelle partie",
        description = "Enregistre une nouvelle partie terminée avec les scores des deux joueurs. Utilisé par le frontend après la fin d'un match."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Partie créée avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides (joueurs identiques, scores négatifs, etc.)",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameDto createGame(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Informations de la partie à enregistrer (ne pas fournir l'ID)",
                required = true,
                content = @Content(schema = @Schema(implementation = GameDto.class))
            )
            @Valid @RequestBody GameDto gameDto) {
        return gameService.save(gameDto);
    }
    
    @Operation(
        summary = "Mettre à jour une partie",
        description = "Modifie les informations d'une partie existante (correction de scores, mise à jour de la durée)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Partie mise à jour avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Partie non trouvée",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public GameDto updateGame(
            @Parameter(description = "ID de la partie à modifier", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody GameDto gameDto) {
        if (!gameService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        gameDto.setId(id);
        return gameService.save(gameDto);
    }
    
    @Operation(
        summary = "Supprimer une partie",
        description = "Supprime définitivement une partie de l'historique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Partie supprimée avec succès"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Partie non trouvée",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(
            @Parameter(description = "ID de la partie à supprimer", required = true, example = "1")
            @PathVariable Long id) {
        if (!gameService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        gameService.delete(id);
    }
}
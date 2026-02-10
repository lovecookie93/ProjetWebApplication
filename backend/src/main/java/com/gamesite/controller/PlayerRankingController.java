package com.gamesite.controller;

import com.gamesite.dto.PlayerRankingDto;
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
@RequestMapping("/api/rankings")
@CrossOrigin(origins = "*")
@Tag(name = "Rankings", description = "Gestion des classements - Scores et progression des joueurs par type de jeu")
public class PlayerRankingController {

    @Autowired
    private PlayerRankingService playerRankingService;
    
    @Operation(
        summary = "Récupérer tous les classements",
        description = "Retourne la liste complète de tous les classements (tous joueurs, tous types de jeu confondus)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste des classements récupérée avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        )
    })
    @GetMapping
    public List<PlayerRankingDto> getAllRankings() {
        return playerRankingService.findAll();
    }
    
    @Operation(
        summary = "Récupérer un classement par ID",
        description = "Retourne les détails d'un classement spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Classement trouvé",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Classement non trouvé",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public PlayerRankingDto getRankingById(
            @Parameter(description = "ID du classement à récupérer", required = true, example = "1")
            @PathVariable Long id) {
        return playerRankingService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ranking not found"));
    }
    
    @Operation(
        summary = "Récupérer les classements d'un joueur",
        description = "Retourne tous les classements d'un joueur (un par type de jeu auquel il a joué)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Classements du joueur récupérés avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        )
    })
    @GetMapping("/player/{playerId}")
    public List<PlayerRankingDto> getRankingsByPlayer(
            @Parameter(description = "ID du joueur", required = true, example = "1")
            @PathVariable Long playerId) {
        return playerRankingService.findByPlayerId(playerId);
    }
    
    @Operation(
        summary = "Récupérer le classement par type de jeu",
        description = "Retourne le classement de tous les joueurs pour un type de jeu spécifique (ex: top 10 Chess)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Classement récupéré avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        )
    })
    @GetMapping("/game-type/{gameTypeId}")
    public List<PlayerRankingDto> getRankingsByGameType(
            @Parameter(description = "ID du type de jeu", required = true, example = "1")
            @PathVariable Long gameTypeId) {
        return playerRankingService.findByGameTypeId(gameTypeId);
    }
    
    @Operation(
        summary = "Créer ou mettre à jour un classement",
        description = "Enregistre un nouveau classement ou met à jour les points d'un joueur pour un type de jeu"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Classement créé avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerRankingDto createRanking(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Informations du classement à créer (ne pas fournir l'ID)",
                required = true,
                content = @Content(schema = @Schema(implementation = PlayerRankingDto.class))
            )
            @Valid @RequestBody PlayerRankingDto rankingDto) {
        return playerRankingService.save(rankingDto);
    }
    
    @Operation(
        summary = "Mettre à jour un classement",
        description = "Modifie les points ou la date d'un classement existant"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Classement mis à jour avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Classement non trouvé",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public PlayerRankingDto updateRanking(
            @Parameter(description = "ID du classement à modifier", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PlayerRankingDto rankingDto) {
        if (!playerRankingService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ranking not found");
        }
        rankingDto.setId(id);
        return playerRankingService.save(rankingDto);
    }
    
    @Operation(
        summary = "Supprimer un classement",
        description = "Supprime un classement de l'historique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Classement supprimé avec succès"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Classement non trouvé",
            content = @Content
        )
    })
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRanking(
            @Parameter(description = "ID du classement à supprimer", required = true, example = "1")
            @PathVariable Long id) {
        if (!playerRankingService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ranking not found");
        }
        playerRankingService.delete(id);
    }
}
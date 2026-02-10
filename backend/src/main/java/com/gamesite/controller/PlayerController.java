package com.gamesite.controller;

import com.gamesite.dto.PlayerDto;
import com.gamesite.dto.PlayerRankingDto;
import com.gamesite.dto.GameDto;
import com.gamesite.service.PlayerService;
import com.gamesite.service.PlayerRankingService;
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
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
@Tag(name = "Players", description = "Gestion des joueurs - Inscription, profil et statistiques")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private PlayerRankingService playerRankingService;
    
    @Autowired
    private GameService gameService;
    
    @Operation(
        summary = "Récupérer tous les joueurs",
        description = "Retourne la liste complète de tous les joueurs inscrits sur la plateforme"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste des joueurs récupérée avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDto.class))
        )
    })
    @GetMapping
    public List<PlayerDto> getAllPlayers() {
        return playerService.findAll();
    }
    
    @Operation(
        summary = "Récupérer un joueur par ID",
        description = "Retourne les informations détaillées d'un joueur spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Joueur trouvé",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Joueur non trouvé",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public PlayerDto getPlayerById(
            @Parameter(description = "ID du joueur à récupérer", required = true, example = "1")
            @PathVariable Long id) {
        return playerService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
    }
    
    @Operation(
        summary = "Récupérer les classements d'un joueur",
        description = "Retourne tous les classements d'un joueur (un classement par type de jeu)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Classements récupérés avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerRankingDto.class))
        )
    })
    @GetMapping("/{id}/rankings")
    public List<PlayerRankingDto> getPlayerRankings(
            @Parameter(description = "ID du joueur", required = true, example = "1")
            @PathVariable Long id) {
        return playerRankingService.findByPlayerId(id);
    }
    
    @Operation(
        summary = "Récupérer les parties d'un joueur",
        description = "Retourne l'historique complet de toutes les parties jouées par un joueur (en tant que player1 ou player2)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Historique des parties récupéré avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameDto.class))
        )
    })
    @GetMapping("/{id}/games")
    public List<GameDto> getPlayerGames(
            @Parameter(description = "ID du joueur", required = true, example = "1")
            @PathVariable Long id) {
        return gameService.findByPlayerId(id);
    }
    
    @Operation(
        summary = "Créer un nouveau joueur",
        description = "Inscrit un nouveau joueur sur la plateforme avec un username et un email uniques"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Joueur créé avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides (username ou email déjà utilisé)",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDto createPlayer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Informations du joueur à créer (ne pas fournir l'ID)",
                required = true,
                content = @Content(schema = @Schema(implementation = PlayerDto.class))
            )
            @Valid @RequestBody PlayerDto playerDto) {
        return playerService.save(playerDto);
    }
    
    @Operation(
        summary = "Mettre à jour un joueur",
        description = "Modifie les informations d'un joueur existant (username et/ou email)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Joueur mis à jour avec succès",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlayerDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Joueur non trouvé",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public PlayerDto updatePlayer(
            @Parameter(description = "ID du joueur à modifier", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PlayerDto playerDto) {
        if (!playerService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        playerDto.setId(id);
        return playerService.save(playerDto);
    }
    
    @Operation(
        summary = "Supprimer un joueur",
        description = "Supprime définitivement un joueur et toutes ses données associées (parties, classements, messages)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Joueur supprimé avec succès"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Joueur non trouvé",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(
            @Parameter(description = "ID du joueur à supprimer", required = true, example = "1")
            @PathVariable Long id) {
        if (!playerService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        playerService.delete(id);
    }
}
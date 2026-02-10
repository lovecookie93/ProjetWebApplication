package com.gamesite.controller;

import com.gamesite.dto.ForumDto;
import com.gamesite.dto.MessageDto;
import com.gamesite.service.ForumService;
import com.gamesite.service.MessageService;
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
@RequestMapping("/api/forums")
@CrossOrigin(origins = "*")
@Tag(name = "Forums", description = "Gestion des forums de discussion")
public class ForumController {

    @Autowired
    private ForumService forumService;
    
    @Autowired
    private MessageService messageService;
    
    @Operation(
        summary = "Récupérer tous les forums",
        description = "Retourne la liste complète de tous les forums disponibles sur la plateforme"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste des forums récupérée avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ForumDto.class)
            )
        )
    })
    @GetMapping
    public List<ForumDto> getAllForums() {
        return forumService.findAll();
    }
    
    @Operation(
        summary = "Récupérer un forum par ID",
        description = "Retourne les détails d'un forum spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Forum trouvé",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ForumDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Forum non trouvé",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public ForumDto getForumById(
            @Parameter(description = "ID du forum à récupérer", required = true, example = "1")
            @PathVariable Long id) {
        return forumService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum non trouvé"));
    }
    
    @Operation(
        summary = "Récupérer les messages d'un forum",
        description = "Retourne tous les messages publics postés dans un forum spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Messages récupérés avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        )
    })
    @GetMapping("/{id}/messages")
    public List<MessageDto> getForumMessages(
            @Parameter(description = "ID du forum", required = true, example = "1")
            @PathVariable Long id) {
        return messageService.findByForumId(id);
    }
    
    @Operation(
        summary = "Créer un nouveau forum",
        description = "Crée un nouveau forum de discussion thématique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Forum créé avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ForumDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides",
            content = @Content
        )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ForumDto createForum(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Informations du forum à créer",
                required = true,
                content = @Content(schema = @Schema(implementation = ForumDto.class))
            )
            @Valid @RequestBody ForumDto forumDto) {
        return forumService.save(forumDto);
    }
    
    @Operation(
        summary = "Mettre à jour un forum",
        description = "Modifie les informations d'un forum existant"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Forum mis à jour avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ForumDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Forum non trouvé",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public ForumDto updateForum(
            @Parameter(description = "ID du forum à modifier", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ForumDto forumDto) {
        if (!forumService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum non trouvé");
        }
        forumDto.setId(id);
        return forumService.save(forumDto);
    }
    
    @Operation(
        summary = "Supprimer un forum",
        description = "Supprime définitivement un forum et tous ses messages associés"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Forum supprimé avec succès"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Forum non trouvé",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForum(
            @Parameter(description = "ID du forum à supprimer", required = true, example = "1")
            @PathVariable Long id) {
        if (!forumService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Forum non trouvé");
        }
        forumService.delete(id);
    }
}
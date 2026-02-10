package com.gamesite.controller;

import com.gamesite.dto.MessageDto;
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
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
@Tag(name = "Messages", description = "Gestion des messages (publics et privés)")
public class MessageController {

    @Autowired
    private MessageService messageService;
    
    @Operation(
        summary = "Récupérer tous les messages",
        description = "Retourne la liste de tous les messages (publics et privés)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Liste des messages récupérée avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        )
    })
    @GetMapping
    public List<MessageDto> getAllMessages() {
        return messageService.findAll();
    }
    
    @Operation(
        summary = "Récupérer un message par ID",
        description = "Retourne les détails d'un message spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Message trouvé",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Message non trouvé",
            content = @Content
        )
    })
    @GetMapping("/{id}")
    public MessageDto getMessageById(
            @Parameter(description = "ID du message à récupérer", required = true, example = "1")
            @PathVariable Long id) {
        return messageService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non trouvé"));
    }
    
    @Operation(
        summary = "Récupérer les messages d'un forum",
        description = "Retourne tous les messages publics postés dans un forum spécifique"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Messages du forum récupérés avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        )
    })
    @GetMapping("/forum/{forumId}")
    public List<MessageDto> getMessagesByForum(
            @Parameter(description = "ID du forum", required = true, example = "1")
            @PathVariable Long forumId) {
        return messageService.findByForumId(forumId);
    }
    
    @Operation(
        summary = "Récupérer les messages écrits par un joueur",
        description = "Retourne tous les messages dont un joueur est l'auteur"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Messages de l'auteur récupérés avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        )
    })
    @GetMapping("/author/{authorId}")
    public List<MessageDto> getMessagesByAuthor(
            @Parameter(description = "ID de l'auteur", required = true, example = "1")
            @PathVariable Long authorId) {
        return messageService.findByAuthorId(authorId);
    }
    
    @Operation(
        summary = "Récupérer les messages reçus par un joueur",
        description = "Retourne tous les messages privés dont un joueur est le destinataire"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Messages privés récupérés avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        )
    })
    @GetMapping("/receiver/{receiverId}")
    public List<MessageDto> getMessagesByReceiver(
            @Parameter(description = "ID du destinataire", required = true, example = "1")
            @PathVariable Long receiverId) {
        return messageService.findByReceiverId(receiverId);
    }
    
    @Operation(
        summary = "Créer un nouveau message",
        description = "Poste un nouveau message public (dans un forum) ou privé (à un destinataire)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Message créé avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
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
    public MessageDto createMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Informations du message à créer. " +
                             "Pour un message public: renseigner authorId et forumId. " +
                             "Pour un message privé: renseigner authorId et receiverId.",
                required = true,
                content = @Content(schema = @Schema(implementation = MessageDto.class))
            )
            @Valid @RequestBody MessageDto messageDto) {
        return messageService.save(messageDto);
    }
    
    @Operation(
        summary = "Mettre à jour un message",
        description = "Modifie le contenu d'un message existant"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Message mis à jour avec succès",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = MessageDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Message non trouvé",
            content = @Content
        )
    })
    @PutMapping("/{id}")
    public MessageDto updateMessage(
            @Parameter(description = "ID du message à modifier", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody MessageDto messageDto) {
        if (!messageService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non trouvé");
        }
        messageDto.setId(id);
        return messageService.save(messageDto);
    }
    
    @Operation(
        summary = "Supprimer un message",
        description = "Supprime définitivement un message"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Message supprimé avec succès"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Message non trouvé",
            content = @Content
        )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(
            @Parameter(description = "ID du message à supprimer", required = true, example = "1")
            @PathVariable Long id) {
        if (!messageService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message non trouvé");
        }
        messageService.delete(id);
    }
}
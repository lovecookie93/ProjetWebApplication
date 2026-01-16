package com.gamesite.service;

import com.gamesite.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    public List<MessageDto> findAll() {
        return List.of(); // vide pour l'instant
    }

    public Optional<MessageDto> findById(Long id) {
        return Optional.empty(); // vide pour l'instant
    }

    public List<MessageDto> findByForumId(Long forumId) {
        return List.of(); // vide pour l'instant
    }

    public List<MessageDto> findByAuthorId(Long authorId) {
        return List.of(); // vide pour l'instant
    }

    public List<MessageDto> findByReceiverId(Long receiverId) {
        return List.of(); // vide pour l'instant
    }

    public MessageDto save(MessageDto dto) {
        return dto; // juste pour que ça compile
    }

    public void delete(Long id) {
        // rien pour l'instant
    }
}

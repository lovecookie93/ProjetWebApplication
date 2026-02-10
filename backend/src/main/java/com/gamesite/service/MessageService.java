package com.gamesite.service;

import com.gamesite.dto.MessageDto;
import com.gamesite.entity.Message;
import com.gamesite.entity.Player;
import com.gamesite.entity.Forum;
import com.gamesite.repository.MessageRepository;
import com.gamesite.repository.PlayerRepository;
import com.gamesite.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private ForumRepository forumRepository;
    
    public List<MessageDto> findAll() {
        return messageRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public Optional<MessageDto> findById(Long id) {
        return messageRepository.findById(id)
            .map(this::convertToDto);
    }
    
    public List<MessageDto> findByForumId(Long forumId) {
        return messageRepository.findByForumId(forumId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public List<MessageDto> findByAuthorId(Long authorId) {
        return messageRepository.findByAuthorId(authorId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public List<MessageDto> findByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public MessageDto save(MessageDto dto) {
        Message message = convertToEntity(dto);
        Message saved = messageRepository.save(message);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        messageRepository.deleteById(id);
    }
    
    // Conversion Entity <-> DTO
    private MessageDto convertToDto(Message entity) {
        MessageDto dto = new MessageDto();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setDate(entity.getDate());
        dto.setAuthorId(entity.getAuthor() != null ? entity.getAuthor().getId() : null);
        dto.setReceiverId(entity.getReceiver() != null ? entity.getReceiver().getId() : null);
        dto.setForumId(entity.getForum() != null ? entity.getForum().getId() : null);
        return dto;
    }
    
    private Message convertToEntity(MessageDto dto) {
        Message entity = new Message();
        
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        
        entity.setMessage(dto.getMessage());
        entity.setDate(dto.getDate());
        
        // Author (obligatoire)
        if (dto.getAuthorId() != null) {
            Player author = playerRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
            entity.setAuthor(author);
        }
        
        // Receiver (optionnel - message privé)
        if (dto.getReceiverId() != null) {
            Player receiver = playerRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
            entity.setReceiver(receiver);
        }
        
        // Forum (optionnel - message public)
        if (dto.getForumId() != null) {
            Forum forum = forumRepository.findById(dto.getForumId())
                .orElseThrow(() -> new RuntimeException("Forum not found"));
            entity.setForum(forum);
        }
        
        return entity;
    }
}
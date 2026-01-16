package com.gamesite.controller;

import com.gamesite.dto.MessageDto;
import com.gamesite.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;
    
    @GetMapping
    public List<MessageDto> getAllMessages() {
        return messageService.findAll();
    }
    
    @GetMapping("/{id}")
    public MessageDto getMessageById(@PathVariable Long id) {
        return messageService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/forum/{forumId}")
    public List<MessageDto> getMessagesByForum(@PathVariable Long forumId) {
        return messageService.findByForumId(forumId);
    }
    
    @GetMapping("/author/{authorId}")
    public List<MessageDto> getMessagesByAuthor(@PathVariable Long authorId) {
        return messageService.findByAuthorId(authorId);
    }
    
    @GetMapping("/receiver/{receiverId}")
    public List<MessageDto> getMessagesByReceiver(@PathVariable Long receiverId) {
        return messageService.findByReceiverId(receiverId);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDto createMessage(@Valid @RequestBody MessageDto messageDto) {
        return messageService.save(messageDto);
    }
    
    @PutMapping("/{id}")
    public MessageDto updateMessage(@PathVariable Long id, @Valid @RequestBody MessageDto messageDto) {
        if (!messageService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        messageDto.setId(id);
        return messageService.save(messageDto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@PathVariable Long id) {
        if (!messageService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        messageService.delete(id);
    }
}

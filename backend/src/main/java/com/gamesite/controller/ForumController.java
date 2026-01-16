package com.gamesite.controller;

import com.gamesite.dto.ForumDto;
import com.gamesite.dto.MessageDto;
import com.gamesite.service.ForumService;
import com.gamesite.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/forums")
@CrossOrigin(origins = "*")
public class ForumController {

    @Autowired
    private ForumService forumService;
    
    @Autowired
    private MessageService messageService;
    
    @GetMapping
    public List<ForumDto> getAllForums() {
        return forumService.findAll();
    }
    
    @GetMapping("/{id}")
    public ForumDto getForumById(@PathVariable Long id) {
        return forumService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/{id}/messages")
    public List<MessageDto> getForumMessages(@PathVariable Long id) {
        return messageService.findByForumId(id);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ForumDto createForum(@Valid @RequestBody ForumDto forumDto) {
        return forumService.save(forumDto);
    }
    
    @PutMapping("/{id}")
    public ForumDto updateForum(@PathVariable Long id, @Valid @RequestBody ForumDto forumDto) {
        if (!forumService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        forumDto.setId(id);
        return forumService.save(forumDto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteForum(@PathVariable Long id) {
        if (!forumService.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        forumService.delete(id);
    }
}

package com.gamesite.service;

import com.gamesite.dto.ForumDto;
import com.gamesite.entity.Forum;
import com.gamesite.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ForumService {
    
    @Autowired
    private ForumRepository forumRepository;
    
    public List<ForumDto> findAll() {
        return forumRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }
    
    public Optional<ForumDto> findById(Long id) {
        return forumRepository.findById(id)
            .map(this::convertToDto);
    }
    
    public ForumDto save(ForumDto dto) {
        Forum forum = convertToEntity(dto);
        Forum saved = forumRepository.save(forum);
        return convertToDto(saved);
    }
    
    public void delete(Long id) {
        forumRepository.deleteById(id);
    }
    
    // Conversion Entity <-> DTO
    private ForumDto convertToDto(Forum entity) {
        ForumDto dto = new ForumDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }
    
    private Forum convertToEntity(ForumDto dto) {
        Forum entity = new Forum();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        return entity;
    }
}
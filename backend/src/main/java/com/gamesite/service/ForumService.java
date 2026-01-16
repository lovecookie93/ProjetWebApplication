package com.gamesite.service;

import com.gamesite.dto.ForumDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumService {

    public List<ForumDto> findAll() { return List.of(); }
    public Optional<ForumDto> findById(Long id) { return Optional.empty(); }
    public ForumDto save(ForumDto dto) { return dto; }
    public void delete(Long id) {}
}

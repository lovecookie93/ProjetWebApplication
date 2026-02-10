package com.gamesite.repository;

import com.gamesite.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    // Trouver les messages d'un forum
    List<Message> findByForumId(Long forumId);
    
    // Trouver les messages écrits par un auteur
    List<Message> findByAuthorId(Long authorId);
    
    // Trouver les messages reçus par un destinataire
    List<Message> findByReceiverId(Long receiverId);
}
package com.gamesite.repository;

import com.gamesite.entity.PlayerRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRankingRepository extends JpaRepository<PlayerRanking, Long> {
    
    // Trouver les rankings par joueur
    List<PlayerRanking> findByPlayerId(Long playerId);
    
    // Trouver les rankings par type de jeu
    List<PlayerRanking> findByGameTypeId(Long gameTypeId);
    
    // Trouver le ranking d'un joueur pour un type de jeu spécifique
    List<PlayerRanking> findByPlayerIdAndGameTypeId(Long playerId, Long gameTypeId);
}
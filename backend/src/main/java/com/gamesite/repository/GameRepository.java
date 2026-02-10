package com.gamesite.repository;

import com.gamesite.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByGameTypeId(Long gameTypeId);
    @Query("SELECT g FROM Game g WHERE g.player1.id = :playerId OR g.player2.id = :playerId")
    List<Game> findByPlayerId(Long playerId);
}
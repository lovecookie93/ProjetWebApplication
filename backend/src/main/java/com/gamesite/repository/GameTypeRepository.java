package com.gamesite.repository;

import com.gamesite.entity.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameTypeRepository extends JpaRepository<GameType, Long> {
    Optional<GameType> findByName(String name);
}
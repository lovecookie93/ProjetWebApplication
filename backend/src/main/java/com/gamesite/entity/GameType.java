package com.gamesite.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Game type name is required")
    @Column(nullable = false, unique = true)
    private String name;
    
    @OneToMany(mappedBy = "gameType", cascade = CascadeType.ALL)
    private List<Game> games = new ArrayList<>();
    
    @OneToMany(mappedBy = "gameType", cascade = CascadeType.ALL)
    private List<PlayerRanking> rankings = new ArrayList<>();
}
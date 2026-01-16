package com.gamesite.dto;

public class GameDto {
    private Long id;
    private String name;
    private Long gameTypeId;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getGameTypeId() { return gameTypeId; }
    public void setGameTypeId(Long gameTypeId) { this.gameTypeId = gameTypeId; }
}

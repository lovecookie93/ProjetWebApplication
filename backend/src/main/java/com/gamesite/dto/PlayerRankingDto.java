package com.gamesite.dto;

public class PlayerRankingDto {
    private Long id;
    private Long playerId;
    private Long gameTypeId;
    private int rank;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public Long getGameTypeId() { return gameTypeId; }
    public void setGameTypeId(Long gameTypeId) { this.gameTypeId = gameTypeId; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }
}

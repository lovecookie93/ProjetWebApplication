package com.gamesite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRankingDto {
    private Long id;
    private Integer points;
    private Instant dateAchieved;
    private Long playerId;
    private Long gameTypeId;
}
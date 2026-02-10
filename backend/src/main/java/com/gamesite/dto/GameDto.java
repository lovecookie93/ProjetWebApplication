package com.gamesite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDto {
    private Long id;
    private Instant startTime;
    private Instant endTime;
    private Double scorePlayer1;
    private Double scorePlayer2;
    private Long player1Id;
    private Long player2Id;
    private Long gameTypeId;
}
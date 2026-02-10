package com.gamesite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String message;
    private LocalDateTime date;
    private Long authorId;
    private Long receiverId;
    private Long forumId;
}
package com.gamesite.dto;

import java.time.Instant;

public class MessageDto {
    private Long id;
    private String content;
    private Long authorId;
    private Long receiverId;
    private Long forumId;
    private Instant createdAt;

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

    public Long getForumId() { return forumId; }
    public void setForumId(Long forumId) { this.forumId = forumId; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}

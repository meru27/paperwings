package dev.meru.paperwings.core.engine.model;

import java.time.LocalDateTime;

public class MessageDTO {

    private final Long senderId;
    private final String content;
    private final LocalDateTime timestamp;

    public MessageDTO(Long senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public Long getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

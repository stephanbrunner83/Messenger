package com.runtastic.fhooe.messanger.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Message entity.
 */
public class MessageDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String content;


    private Long senderId;

    private Long receiverId;

    private Long conversationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long participantId) {
        this.senderId = participantId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long participantId) {
        this.receiverId = participantId;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (messageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", sender=" + getSenderId() +
            ", receiver=" + getReceiverId() +
            ", conversation=" + getConversationId() +
            "}";
    }
}

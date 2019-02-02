package com.runtastic.fhooe.messanger.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Conversation entity.
 */
public class ConversationDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String topic;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConversationDTO conversationDTO = (ConversationDTO) o;
        if (conversationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conversationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConversationDTO{" +
            "id=" + getId() +
            ", topic='" + getTopic() + "'" +
            "}";
    }
}

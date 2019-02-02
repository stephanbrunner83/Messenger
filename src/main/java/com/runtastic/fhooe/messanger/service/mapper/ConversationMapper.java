package com.runtastic.fhooe.messanger.service.mapper;

import com.runtastic.fhooe.messanger.domain.*;
import com.runtastic.fhooe.messanger.service.dto.ConversationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Conversation and its DTO ConversationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConversationMapper extends EntityMapper<ConversationDTO, Conversation> {


    @Mapping(target = "messages", ignore = true)
    Conversation toEntity(ConversationDTO conversationDTO);

    default Conversation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Conversation conversation = new Conversation();
        conversation.setId(id);
        return conversation;
    }
}

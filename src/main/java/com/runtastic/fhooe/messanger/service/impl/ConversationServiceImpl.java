package com.runtastic.fhooe.messanger.service.impl;

import com.runtastic.fhooe.messanger.service.ConversationService;
import com.runtastic.fhooe.messanger.domain.Conversation;
import com.runtastic.fhooe.messanger.repository.ConversationRepository;
import com.runtastic.fhooe.messanger.service.dto.ConversationDTO;
import com.runtastic.fhooe.messanger.service.mapper.ConversationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Conversation.
 */
@Service
@Transactional
public class ConversationServiceImpl implements ConversationService {

    private final Logger log = LoggerFactory.getLogger(ConversationServiceImpl.class);

    private final ConversationRepository conversationRepository;

    private final ConversationMapper conversationMapper;

    public ConversationServiceImpl(ConversationRepository conversationRepository, ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
    }

    /**
     * Save a conversation.
     *
     * @param conversationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConversationDTO save(ConversationDTO conversationDTO) {
        log.debug("Request to save Conversation : {}", conversationDTO);
        Conversation conversation = conversationMapper.toEntity(conversationDTO);
        conversation = conversationRepository.save(conversation);
        return conversationMapper.toDto(conversation);
    }

    /**
     * Get all the conversations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConversationDTO> findAll() {
        log.debug("Request to get all Conversations");
        return conversationRepository.findAll().stream()
            .map(conversationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one conversation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConversationDTO> findOne(Long id) {
        log.debug("Request to get Conversation : {}", id);
        return conversationRepository.findById(id)
            .map(conversationMapper::toDto);
    }

    /**
     * Delete the conversation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conversation : {}", id);        conversationRepository.deleteById(id);
    }
}

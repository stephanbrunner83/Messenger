package com.runtastic.fhooe.messanger.service;

import com.runtastic.fhooe.messanger.service.dto.MessageDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Message.
 */
public interface MessageService {

    /**
     * Save a message.
     *
     * @param messageDTO the entity to save
     * @return the persisted entity
     */
    MessageDTO save(MessageDTO messageDTO);

    /**
     * Get all the messages.
     *
     * @return the list of entities
     */
    List<MessageDTO> findAll();


    /**
     * Get the "id" message.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MessageDTO> findOne(Long id);

    /**
     * Delete the "id" message.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

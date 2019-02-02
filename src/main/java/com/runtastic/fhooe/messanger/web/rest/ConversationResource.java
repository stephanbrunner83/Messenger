package com.runtastic.fhooe.messanger.web.rest;
import com.runtastic.fhooe.messanger.service.ConversationService;
import com.runtastic.fhooe.messanger.web.rest.errors.BadRequestAlertException;
import com.runtastic.fhooe.messanger.web.rest.util.HeaderUtil;
import com.runtastic.fhooe.messanger.service.dto.ConversationDTO;
import com.runtastic.fhooe.messanger.service.dto.ConversationCriteria;
import com.runtastic.fhooe.messanger.service.ConversationQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Conversation.
 */
@RestController
@RequestMapping("/api")
public class ConversationResource {

    private final Logger log = LoggerFactory.getLogger(ConversationResource.class);

    private static final String ENTITY_NAME = "conversation";

    private final ConversationService conversationService;

    private final ConversationQueryService conversationQueryService;

    public ConversationResource(ConversationService conversationService, ConversationQueryService conversationQueryService) {
        this.conversationService = conversationService;
        this.conversationQueryService = conversationQueryService;
    }

    /**
     * POST  /conversations : Create a new conversation.
     *
     * @param conversationDTO the conversationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conversationDTO, or with status 400 (Bad Request) if the conversation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conversations")
    public ResponseEntity<ConversationDTO> createConversation(@Valid @RequestBody ConversationDTO conversationDTO) throws URISyntaxException {
        log.debug("REST request to save Conversation : {}", conversationDTO);
        if (conversationDTO.getId() != null) {
            throw new BadRequestAlertException("A new conversation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConversationDTO result = conversationService.save(conversationDTO);
        return ResponseEntity.created(new URI("/api/conversations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conversations : Updates an existing conversation.
     *
     * @param conversationDTO the conversationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conversationDTO,
     * or with status 400 (Bad Request) if the conversationDTO is not valid,
     * or with status 500 (Internal Server Error) if the conversationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conversations")
    public ResponseEntity<ConversationDTO> updateConversation(@Valid @RequestBody ConversationDTO conversationDTO) throws URISyntaxException {
        log.debug("REST request to update Conversation : {}", conversationDTO);
        if (conversationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConversationDTO result = conversationService.save(conversationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conversationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conversations : get all the conversations.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of conversations in body
     */
    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDTO>> getAllConversations(ConversationCriteria criteria) {
        log.debug("REST request to get Conversations by criteria: {}", criteria);
        List<ConversationDTO> entityList = conversationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /conversations/count : count all the conversations.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/conversations/count")
    public ResponseEntity<Long> countConversations(ConversationCriteria criteria) {
        log.debug("REST request to count Conversations by criteria: {}", criteria);
        return ResponseEntity.ok().body(conversationQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /conversations/:id : get the "id" conversation.
     *
     * @param id the id of the conversationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conversationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/conversations/{id}")
    public ResponseEntity<ConversationDTO> getConversation(@PathVariable Long id) {
        log.debug("REST request to get Conversation : {}", id);
        Optional<ConversationDTO> conversationDTO = conversationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conversationDTO);
    }

    /**
     * DELETE  /conversations/:id : delete the "id" conversation.
     *
     * @param id the id of the conversationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conversations/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Long id) {
        log.debug("REST request to delete Conversation : {}", id);
        conversationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

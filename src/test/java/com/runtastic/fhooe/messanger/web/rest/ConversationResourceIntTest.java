package com.runtastic.fhooe.messanger.web.rest;

import com.runtastic.fhooe.messanger.MessangerApp;

import com.runtastic.fhooe.messanger.domain.Conversation;
import com.runtastic.fhooe.messanger.domain.Message;
import com.runtastic.fhooe.messanger.repository.ConversationRepository;
import com.runtastic.fhooe.messanger.service.ConversationService;
import com.runtastic.fhooe.messanger.service.dto.ConversationDTO;
import com.runtastic.fhooe.messanger.service.mapper.ConversationMapper;
import com.runtastic.fhooe.messanger.web.rest.errors.ExceptionTranslator;
import com.runtastic.fhooe.messanger.service.dto.ConversationCriteria;
import com.runtastic.fhooe.messanger.service.ConversationQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.runtastic.fhooe.messanger.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConversationResource REST controller.
 *
 * @see ConversationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessangerApp.class)
public class ConversationResourceIntTest {

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private ConversationQueryService conversationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restConversationMockMvc;

    private Conversation conversation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConversationResource conversationResource = new ConversationResource(conversationService, conversationQueryService);
        this.restConversationMockMvc = MockMvcBuilders.standaloneSetup(conversationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversation createEntity(EntityManager em) {
        Conversation conversation = new Conversation()
            .topic(DEFAULT_TOPIC);
        return conversation;
    }

    @Before
    public void initTest() {
        conversation = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversation() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // Create the Conversation
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);
        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isCreated());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate + 1);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getTopic()).isEqualTo(DEFAULT_TOPIC);
    }

    @Test
    @Transactional
    public void createConversationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversationRepository.findAll().size();

        // Create the Conversation with an existing ID
        conversation.setId(1L);
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = conversationRepository.findAll().size();
        // set the field null
        conversation.setTopic(null);

        // Create the Conversation, which fails.
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);

        restConversationMockMvc.perform(post("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isBadRequest());

        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConversations() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId().intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())));
    }
    
    @Test
    @Transactional
    public void getConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", conversation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversation.getId().intValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()));
    }

    @Test
    @Transactional
    public void getAllConversationsByTopicIsEqualToSomething() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList where topic equals to DEFAULT_TOPIC
        defaultConversationShouldBeFound("topic.equals=" + DEFAULT_TOPIC);

        // Get all the conversationList where topic equals to UPDATED_TOPIC
        defaultConversationShouldNotBeFound("topic.equals=" + UPDATED_TOPIC);
    }

    @Test
    @Transactional
    public void getAllConversationsByTopicIsInShouldWork() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList where topic in DEFAULT_TOPIC or UPDATED_TOPIC
        defaultConversationShouldBeFound("topic.in=" + DEFAULT_TOPIC + "," + UPDATED_TOPIC);

        // Get all the conversationList where topic equals to UPDATED_TOPIC
        defaultConversationShouldNotBeFound("topic.in=" + UPDATED_TOPIC);
    }

    @Test
    @Transactional
    public void getAllConversationsByTopicIsNullOrNotNull() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        // Get all the conversationList where topic is not null
        defaultConversationShouldBeFound("topic.specified=true");

        // Get all the conversationList where topic is null
        defaultConversationShouldNotBeFound("topic.specified=false");
    }

    @Test
    @Transactional
    public void getAllConversationsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        Message message = MessageResourceIntTest.createEntity(em);
        em.persist(message);
        em.flush();
        conversation.addMessage(message);
        conversationRepository.saveAndFlush(conversation);
        Long messageId = message.getId();

        // Get all the conversationList where message equals to messageId
        defaultConversationShouldBeFound("messageId.equals=" + messageId);

        // Get all the conversationList where message equals to messageId + 1
        defaultConversationShouldNotBeFound("messageId.equals=" + (messageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultConversationShouldBeFound(String filter) throws Exception {
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversation.getId().intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)));

        // Check, that the count call also returns 1
        restConversationMockMvc.perform(get("/api/conversations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultConversationShouldNotBeFound(String filter) throws Exception {
        restConversationMockMvc.perform(get("/api/conversations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConversationMockMvc.perform(get("/api/conversations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConversation() throws Exception {
        // Get the conversation
        restConversationMockMvc.perform(get("/api/conversations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Update the conversation
        Conversation updatedConversation = conversationRepository.findById(conversation.getId()).get();
        // Disconnect from session so that the updates on updatedConversation are not directly saved in db
        em.detach(updatedConversation);
        updatedConversation
            .topic(UPDATED_TOPIC);
        ConversationDTO conversationDTO = conversationMapper.toDto(updatedConversation);

        restConversationMockMvc.perform(put("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isOk());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
        Conversation testConversation = conversationList.get(conversationList.size() - 1);
        assertThat(testConversation.getTopic()).isEqualTo(UPDATED_TOPIC);
    }

    @Test
    @Transactional
    public void updateNonExistingConversation() throws Exception {
        int databaseSizeBeforeUpdate = conversationRepository.findAll().size();

        // Create the Conversation
        ConversationDTO conversationDTO = conversationMapper.toDto(conversation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConversationMockMvc.perform(put("/api/conversations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conversation in the database
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConversation() throws Exception {
        // Initialize the database
        conversationRepository.saveAndFlush(conversation);

        int databaseSizeBeforeDelete = conversationRepository.findAll().size();

        // Delete the conversation
        restConversationMockMvc.perform(delete("/api/conversations/{id}", conversation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conversation> conversationList = conversationRepository.findAll();
        assertThat(conversationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conversation.class);
        Conversation conversation1 = new Conversation();
        conversation1.setId(1L);
        Conversation conversation2 = new Conversation();
        conversation2.setId(conversation1.getId());
        assertThat(conversation1).isEqualTo(conversation2);
        conversation2.setId(2L);
        assertThat(conversation1).isNotEqualTo(conversation2);
        conversation1.setId(null);
        assertThat(conversation1).isNotEqualTo(conversation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConversationDTO.class);
        ConversationDTO conversationDTO1 = new ConversationDTO();
        conversationDTO1.setId(1L);
        ConversationDTO conversationDTO2 = new ConversationDTO();
        assertThat(conversationDTO1).isNotEqualTo(conversationDTO2);
        conversationDTO2.setId(conversationDTO1.getId());
        assertThat(conversationDTO1).isEqualTo(conversationDTO2);
        conversationDTO2.setId(2L);
        assertThat(conversationDTO1).isNotEqualTo(conversationDTO2);
        conversationDTO1.setId(null);
        assertThat(conversationDTO1).isNotEqualTo(conversationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conversationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conversationMapper.fromId(null)).isNull();
    }
}

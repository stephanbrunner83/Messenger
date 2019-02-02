package com.runtastic.fhooe.messanger.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.runtastic.fhooe.messanger.domain.Participant;
import com.runtastic.fhooe.messanger.domain.*; // for static metamodels
import com.runtastic.fhooe.messanger.repository.ParticipantRepository;
import com.runtastic.fhooe.messanger.service.dto.ParticipantCriteria;
import com.runtastic.fhooe.messanger.service.dto.ParticipantDTO;
import com.runtastic.fhooe.messanger.service.mapper.ParticipantMapper;

/**
 * Service for executing complex queries for Participant entities in the database.
 * The main input is a {@link ParticipantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ParticipantDTO} or a {@link Page} of {@link ParticipantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ParticipantQueryService extends QueryService<Participant> {

    private final Logger log = LoggerFactory.getLogger(ParticipantQueryService.class);

    private final ParticipantRepository participantRepository;

    private final ParticipantMapper participantMapper;

    public ParticipantQueryService(ParticipantRepository participantRepository, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
    }

    /**
     * Return a {@link List} of {@link ParticipantDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ParticipantDTO> findByCriteria(ParticipantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Participant> specification = createSpecification(criteria);
        return participantMapper.toDto(participantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ParticipantDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ParticipantDTO> findByCriteria(ParticipantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Participant> specification = createSpecification(criteria);
        return participantRepository.findAll(specification, page)
            .map(participantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ParticipantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Participant> specification = createSpecification(criteria);
        return participantRepository.count(specification);
    }

    /**
     * Function to convert ParticipantCriteria to a {@link Specification}
     */
    private Specification<Participant> createSpecification(ParticipantCriteria criteria) {
        Specification<Participant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Participant_.id));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Participant_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Participant_.lastName));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Participant_.email));
            }
        }
        return specification;
    }
}

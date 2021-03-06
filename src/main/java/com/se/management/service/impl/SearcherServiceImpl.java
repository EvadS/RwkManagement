package com.se.management.service.impl;


import com.se.management.domain.*;
import com.se.management.exception.SearcherNotFoundException;
import com.se.management.mapper.AddressMapper;
import com.se.management.mapper.SearcherMapper;
import com.se.management.mapper.SkillScoreMapper;
import com.se.management.model.request.ContactInfoRequest;
import com.se.management.model.request.SearcherRequest;
import com.se.management.model.request.SkillScoreRequest;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.model.response.SkillResponse;
import com.se.management.repository.*;
import com.se.management.service.SearcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearcherServiceImpl implements SearcherService {

    private static final Logger logger = LoggerFactory.getLogger(SearcherServiceImpl.class);

    private final SkillRepository skillRepo;
    private final SearcherRepository searcherRepository;

    private final ContactRepository contactRepository;
    private final MessengerTypeRepository messengerTypeRepository;
    private final SkillsScoreRepository skillsScoreRepository;

    public SearcherServiceImpl(SkillRepository skillRepo, SearcherRepository searcherRepository,
                               ContactRepository contactRepository, MessengerTypeRepository messengerTypeRepository,
                               SkillsScoreRepository skillsScoreRepository) {
        this.skillRepo = skillRepo;
        this.searcherRepository = searcherRepository;
        this.contactRepository = contactRepository;
        this.messengerTypeRepository = messengerTypeRepository;
        this.skillsScoreRepository = skillsScoreRepository;
    }

    @Transactional
    @Override
    public SearcherResponse create(@Valid SearcherRequest searcherRequest) {

        List<SkillScoreRequest> skillsRequestList = searcherRequest.getSkills();
//

        Searcher searcher = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);

                Address address = AddressMapper.INSTANCE.AddressRequestToAddress(searcherRequest.getAddressRequest());
                searcher.getAddresses().add(address);
//
        searcherRepository.save(searcher);
//
        skillsRequestList.stream().forEach(it -> {
            Skill skill = skillRepo.getOne(it.getSkillId());
            SkillsScore skillsScore = SkillsScore.builder()
                    .score(it.getScore())
                    .searcher(searcher)
                    .skill(skill)
                    .build();
            skillsScoreRepository.save(skillsScore);
        });

        List<ContactInfoRequest> contactRequestList = searcherRequest.getContactInfos();

        contactRequestList.stream().forEach(it -> {
            MessengerType messengerType = messengerTypeRepository.getOne(it.getMessengerTypeId());
            Contact contact = Contact.builder()
                    .messengerType(messengerType)
                    .searcher(searcher)
                    .value(it.getAddress())

                    .build();
            contactRepository.save(contact);
        });

        SearcherResponse searcherResponse = SearcherMapper.INSTANCE.SearcherToSearcherResponse(searcher);
        return searcherResponse;
    }

    @Override
    public SearcherResponse update(Long searcherId, SearcherRequest searcherRequest) {
        Optional<Searcher> searcherOptional = searcherRepository.findById(searcherId);

        if (!searcherOptional.isPresent()) {
            logger.error("Can't find searcher by id : {}", searcherId);
            throw new SearcherNotFoundException(String.format("Can't find searcher by id : {}", searcherId));
        }
//
        Searcher searcher = searcherOptional.get();
//
//        //TODO: update address
//
        Searcher searcherModel = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);

        searcher.setFirstName(searcherModel.getFirstName());
        searcher.setLastName(searcherModel.getLastName());
        searcher.setReviewDate(searcherModel.getReviewDate());
        //searcher.setEmail(searcherModel.getEmail());

        searcherRepository.save(searcher);

        // clean current
        List<SkillsScore> currentSkills = skillsScoreRepository.findBySearcherId(searcher.getId());
        currentSkills.stream().forEach((it -> skillsScoreRepository.delete(it)));

        List<SkillScoreRequest> skillsRequestList = searcherRequest.getSkills();

        skillsRequestList.stream().forEach(it -> {
            Skill skill = skillRepo.getOne(it.getSkillId());
            SkillsScore skillsScore = SkillsScore.builder()
                    .score(it.getScore())
                    .searcher(searcher)
                    .skill(skill)
                    .build();
            skillsScoreRepository.save(skillsScore);
        });

        List<ContactInfoRequest> contactRequestList = searcherRequest.getContactInfos();

        List<Contact> currentContacts = contactRepository.findBySearcherId(searcherId);
        currentContacts.stream().forEach((it -> contactRepository.delete(it)));

        contactRequestList.stream().forEach(it -> {
            MessengerType messengerType = messengerTypeRepository.getOne(it.getMessengerTypeId());
            Contact contact = Contact.builder()
                    .messengerType(messengerType)
                    .searcher(searcher)
                    .value(it.getAddress())

                    .build();
            contactRepository.save(contact);
        });

        return SearcherMapper.INSTANCE.SearcherToSearcherResponse(searcher);
    }

    @Transactional
    @Override
    public boolean deleteSearcher(long searcherId) {

        Optional<Searcher> searcherOptional = searcherRepository.findById(searcherId);

        if (!searcherOptional.isPresent()) {
            throw new SearcherNotFoundException(String.format("Can't find searcher by id : %s ", searcherId));
        }

        // clean current
        List<SkillsScore> currentSkills = skillsScoreRepository.findBySearcherId(searcherId);
        currentSkills.stream().forEach((it -> skillsScoreRepository.delete(it)));


        List<Contact> currentContacts = contactRepository.findBySearcherId(searcherId);
        currentContacts.stream().forEach((it -> contactRepository.delete(it)));

        searcherRepository.delete(searcherOptional.get());
        return true;
    }

    @Override
    public Page<SearcherListItem> search(Pageable pageable) {
        return searcherRepository.findAll(pageable)
                .map(this::searcherToSearcherResult);
    }

    // TODO: SE move this code to map struct
    private SearcherListItem searcherToSearcherResult(Searcher searcher) {

        SearcherListItem searcherListItem = new SearcherListItem();
//
        // TOP 3 skill
        List<SkillResponse> skillResponseList = skillsScoreRepository
                .findTop3BySearcherIdOrderByScoreDesc(searcher.getId()).stream()
                .map(SkillScoreMapper.INSTANCE::SkillScoreToSkillResponse).collect(Collectors.toList());

        searcherListItem.setId(searcher.getId());
        searcherListItem.setFName(searcher.getFirstName());
        searcherListItem.setLName(searcher.getLastName());
        searcherListItem.setTolSkillList(skillResponseList);

        return searcherListItem;
    }
}

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
import com.se.management.repository.*;
import com.se.management.service.SearcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    SkillRepository skillRepo;
    @Autowired
    private SearcherRepository searcherRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private MessengerTypeRepository messengerTypeRepository;
    @Autowired
    private SkillsScoreRepository skillsScoreRepository;

    @Transactional
    @Override
    public SearcherResponse create(@Valid SearcherRequest searcherRequest) {

        return null;
//        List<SkillScoreRequest> skillsRequestList = searcherRequest.getSkills();
//
//        List<ContactInfoRequest> contactInfoList = searcherRequest.getContactInfos();
//
//        Address address = AddressMapper.INSTANCE.AddressRequestToAddress(searcherRequest.getAddressRequest());
//
//        Searcher searcher = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);
//        searcher.addAddress(address);
//
//        searcherRepository.save(searcher);
//
//        skillsRequestList.stream().forEach(it -> {
//
//            Skill skill = skillRepo.getOne(it.getSkillId());
//            SkillsScore skillsScore = SkillsScore.builder()
//                    .score(it.getScore())
//                    .skill(skill)
//                    .build();
//
//            skillsScore.addSearcher(searcher);
//            skillsScoreRepository.save(skillsScore);
//        });
//
//
//        // TODO: refactored to stream api
//        for (ContactInfoRequest item : contactInfoList) {
//
//            Contact contact = new Contact();
//            contact.setMessengerAddress(item.getAddress());
//
//            MessengerType messengerType = messengerTypeRepository.getOne(item.getMessengerTypeId());
//            contact.setMessengerType(messengerType);
//            contact.addSearcher(searcher);
//            contactRepository.save(contact);
//        }
//
//        SearcherResponse searcherResponse = SearcherMapper.INSTANCE.SearcherToSearcherResponse(searcher);
//        return searcherResponse;
    }

    @Override
    public SearcherResponse update(Long searcherId, SearcherRequest searcherRequest) {
        Optional<Searcher> searcherOptional = searcherRepository.findById(searcherId);

        if (!searcherOptional.isPresent()) {
            logger.error("Can't find searcher by id : {}", searcherId);
            throw new SearcherNotFoundException(String.format("Can't find searcher by id : {}", searcherId));
        }

        Searcher searcher = searcherOptional.get();

        //TODO: update address

        Searcher searcherModel = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);

        searcher.setFirstName(searcherModel.getFirstName());
        searcher.setLastName(searcherModel.getLastName());
        searcher.setReviewDate(searcherModel.getReviewDate());
        searcher.setEmail(searcherModel.getEmail());
        searcherRepository.save(searcher);

        // new skills
        List<SkillsScore> requestSkillsScoreList = searcherRequest.getSkills().stream().
                map(SkillScoreMapper.INSTANCE::SkillRequestToSkill).collect(Collectors.toList());

        // TODO: many-to -many migration
        // remove currents
//        List<Skill> skillList = skillRepository.findBySearcherId(searcherId);
//        skillRepository.deleteAll(skillList);

        // TODO:
//        requestSkillList.stream().forEach(it -> {
//            it.setSearcher(searcher);
//            skillRepository.save(it);
//        });


        // TODO: migrate to many -to -many
//        List<Contact> requestContactList = searcherRequest.getContactInfos().stream().
//                map(ContactMapper.INSTANCE::ContactInfoRequestToContactInfo)
//                .collect(Collectors.toList());
//
//        List<Contact> contactList = contactInfoRepository.findBySearcherId(searcherId);
//        contactInfoRepository.deleteAll(contactList);
//
//        requestContactList.stream().forEach(it -> {
//            it.setSearcher(searcher);
//            contactInfoRepository.save(it);
//        });

        return SearcherMapper.INSTANCE.SearcherToSearcherResponse(searcher);
    }

    @Transactional
    @Override
    public boolean deleteSearcher(long searcherId) {

        Optional<Searcher> searcherOptional = searcherRepository.findById(searcherId);

        if (!searcherOptional.isPresent()) {
            throw new SearcherNotFoundException(String.format("Can't find searcher by id : %s ", searcherId));
        }

        // TODO: many-to -many migration
//        List<Skill> skillList = skillRepository.findBySearcherId(searcherId);
//        skillRepository.deleteAll(skillList);

//        List<Contact> contactList = contactInfoRepository.findBySearcherId(searcherId);
//        contactInfoRepository.deleteAll(contactList);
//
//        searcherRepository.delete(searcherOptional.get());

        return true;
    }

    @Override
    public Page<SearcherListItem> search(Pageable pageable) {
        return searcherRepository.findAll(pageable)
                .map(this::searcherToSearcherResult);
    }

    private SearcherListItem searcherToSearcherResult(Searcher searcher) {
        SearcherListItem searcherListItem = new SearcherListItem();

        // TODO:  migration to many - to - many
//        List<SkillResponse> skillResponseList = skillRepository
//                .findTop3BySearcherIdOrderByScoreDesc(searcher.getId()).stream()
//                .map(SkillMapper.INSTANCE::SkillToSkillResponse).collect(Collectors.toList());

        searcherListItem.setId(searcher.getId());
        searcherListItem.setFName(searcher.getFirstName());
        searcherListItem.setLName(searcher.getLastName());
        //     searcherListItem.setTolSkillList(skillResponseList);

        return searcherListItem;
    }
}

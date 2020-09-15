package com.se.management.service.impl;


import com.se.management.domain.*;
import com.se.management.exception.SearcherNotFoundException;
import com.se.management.mapper.*;
import com.se.management.model.request.SearcherRequest;
import com.se.management.model.request.SkillScoreRequest;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.repository.ContactInfoRepository;
import com.se.management.repository.SearcherRepository;
import com.se.management.repository.SkillRepository;
import com.se.management.repository.SkillsScoreRepository;
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
    private SearcherRepository searcherRepository;

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private SkillsScoreRepository skillsScoreRepository;

    @Autowired
    SkillRepository skillRepo;

    @Transactional
    @Override
    public SearcherResponse create(@Valid SearcherRequest searcherRequest) {

        List<SkillScoreRequest> skillsRequestList = searcherRequest.getSkills();

        List<SkillsScore> skillsScoreList = skillsRequestList.stream().
                map(SkillScoreMapper.INSTANCE::SkillRequestToSkill).collect(Collectors.toList());

        Address address = AddressMapper.INSTANCE.AddressRequestToAddress(searcherRequest.getAddressRequest());

        Searcher searcher = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);
        searcher.addAddress(address);

        searcherRepository.save(searcher);

        for(SkillScoreRequest item : skillsRequestList){
            SkillsScore skillsScore = new SkillsScore();
            skillsScore.setScore(item.getScore());

            Skill skill = skillRepo.getOne(item.getSkillId());

            skillsScore.setSkill(skill);
            skillsScore.addSearcher(searcher);

            skillsScoreRepository.save(skillsScore);

        }
        // TODO: check is skill name exists
//        for(SkillsScore item: skillsScoreList){
//            item.addSearcher(searcher);
//            skillsScoreRepository.save(item);
//        }

        searcherRepository.save(searcher);

//        List<ContactInfo> contactInfoList = searcherRequest.getContactInfos().stream().
//                map(ContactMapper.INSTANCE::ContactInfoRequestToContactInfo)
//                .collect(Collectors.toList());
//
//        contactInfoList.stream().forEach(it -> {
//            it.setSearcher(searcher);
//            contactInfoRepository.save(it);
//        });



        // TODO:
//        skillList.stream().forEach(it -> {
//            it.setSearcher(searcher);
//            skillRepository.save(it);
//        });

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

        List<ContactInfo> requestContactInfoList = searcherRequest.getContactInfos().stream().
                map(ContactMapper.INSTANCE::ContactInfoRequestToContactInfo)
                .collect(Collectors.toList());

        List<ContactInfo> contactInfoList = contactInfoRepository.findBySearcherId(searcherId);
        contactInfoRepository.deleteAll(contactInfoList);

        requestContactInfoList.stream().forEach(it -> {
            it.setSearcher(searcher);
            contactInfoRepository.save(it);
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

        // TODO: many-to -many migration
//        List<Skill> skillList = skillRepository.findBySearcherId(searcherId);
//        skillRepository.deleteAll(skillList);

        List<ContactInfo> contactInfoList = contactInfoRepository.findBySearcherId(searcherId);
        contactInfoRepository.deleteAll(contactInfoList);

        searcherRepository.delete(searcherOptional.get());

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

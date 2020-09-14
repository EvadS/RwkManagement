package com.se.management.service.impl;


import com.se.management.domain.Address;
import com.se.management.domain.ContactInfo;
import com.se.management.domain.Searcher;
import com.se.management.domain.Skill;
import com.se.management.exception.SearcherNotFoundException;
import com.se.management.mapper.AddressMapper;
import com.se.management.mapper.ContactMapper;
import com.se.management.mapper.SearcherMapper;
import com.se.management.mapper.SkillMapper;
import com.se.management.model.request.SearcherRequest;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SearcherListItem;
import com.se.management.model.response.SearcherResponse;
import com.se.management.model.response.SkillResponse;
import com.se.management.repository.ContactInfoRepository;
import com.se.management.repository.SearcherRepository;
import com.se.management.repository.SkillRepository;
import com.se.management.service.SearcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private SkillRepository skillRepository;

    @Transactional
    @Override
    public SearcherResponse create(SearcherRequest searcherRequest) {

        List<SkillRequest> skillsRequestList = searcherRequest.getSkillRequestList();

        List<Skill> skillList = skillsRequestList.stream().
                map(SkillMapper.INSTANCE::SkillRequestToSkill).collect(Collectors.toList());

        List<ContactInfo> contactInfoList = searcherRequest.getContactInfos().stream().
                map(ContactMapper.INSTANCE::ContactInfoRequestToContactInfo)
                .collect(Collectors.toList());

        Address address = AddressMapper.INSTANCE.AddressRequestToAddress(searcherRequest.getAddressRequest());

        Searcher searcher = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);
        searcher.addAddress(address);

        searcherRepository.save(searcher);

        contactInfoList.stream().forEach(it -> {
            it.setSearcher(searcher);
            contactInfoRepository.save(it);
        });

        skillList.stream().forEach(it -> {
            it.setSearcher(searcher);
            skillRepository.save(it);
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

        Searcher searcher = searcherOptional.get();

        //TODO: update address

        Searcher searcherModel = SearcherMapper.INSTANCE.SearcherRequestToSearcher(searcherRequest);

        searcher.setFirstName(searcherModel.getFirstName());
        searcher.setLastName(searcherModel.getLastName());
        searcher.setReviewDate(searcherModel.getReviewDate());
        searcher.setEmail(searcherModel.getEmail());
        searcherRepository.save(searcher);

        // new skills
        List<Skill> requestSkillList = searcherRequest.getSkillRequestList().stream().
                map(SkillMapper.INSTANCE::SkillRequestToSkill).collect(Collectors.toList());

        // remove currents
        List<Skill> skillList = skillRepository.findBySearcherId(searcherId);
        skillRepository.deleteAll(skillList);

        requestSkillList.stream().forEach(it -> {
            it.setSearcher(searcher);
            skillRepository.save(it);
        });

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

        List<Skill> skillList = skillRepository.findBySearcherId(searcherId);
        skillRepository.deleteAll(skillList);

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

        List<SkillResponse> skillResponseList = skillRepository
                .findTop3BySearcherIdOrderByScoreDesc(searcher.getId()).stream()
                .map(SkillMapper.INSTANCE::SkillToSkillResponse).collect(Collectors.toList());

        searcherListItem.setId(searcher.getId());
        searcherListItem.setFName(searcher.getFirstName());
        searcherListItem.setLName(searcher.getLastName());
        searcherListItem.setTolSkillList(skillResponseList);

        return searcherListItem;
    }
}
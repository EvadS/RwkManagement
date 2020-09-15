package com.se.management.repository.specification;

import com.se.management.domain.Searcher;
import com.se.management.model.SearcherSearch;
import com.se.management.repository.SkillsScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class SearcherSpecification extends SearchSpecification<Searcher, SearcherSearch>{

    @Autowired
    SkillsScoreRepository skillsScoreRepository;

    @Override
    public Specification<Searcher> getFilter(SearcherSearch request) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(userAttributeContains("firstName", request.getFName())
                    .and(userAttributeContains("lastName", request.getLName()))
//                            .and(userAttributeEqual("userId", request.getUserId()))
//                            .and(paymentSystemTypeContains("paymentCurrency", request.getPaymentCurrency()))
            ).toPredicate(root, query, cb);
        };
    }

    private Specification<Searcher> userAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }


    //TODO: many - to - many migration
//    public Specification<Searcher> hasSkill(String searchString) {
//
//        return (root, query, cb) -> {
//            query.distinct(true);
//            if (searchString != null) {
//                List<Class> classes = skillRepository.findAllBySkillName(searchString);
//
//                if (!CollectionUtils.isEmpty(classes)) {
//                    // How to join
//                    SetJoin<Searcher, Class> masterClassJoin = root.joinSet("classes", JoinType.LEFT);
//                    List<Predicate> predicates = new ArrayList<>();
//                    predicates.add(masterClassJoin.in(new HashSet<>(classes)));
//                    Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
//                    return cb.or(p);
//                }
//            }
//
//            return null;
//        };
//    }

}

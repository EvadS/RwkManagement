package com.se.management.repository.specification;

import com.se.management.domain.MessengerType;
import com.se.management.domain.Skill;
import com.se.management.model.search.MessengerTypeSearch;
import com.se.management.model.search.SkillSearch;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public class MessengerTypeSpecification extends SearchSpecification<MessengerType, MessengerTypeSearch> {

    @Override
    public Specification<MessengerType> getFilter(MessengerTypeSearch request) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameContains(request.getSearch())))
            .toPredicate(root, query, cb);
        };

    }

    private Specification<MessengerType> nameContains(String alias) {
        return userAttributeContains("name", alias);
    }


    private Specification<MessengerType> userAttributeContains(String attribute, String value) {
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


}
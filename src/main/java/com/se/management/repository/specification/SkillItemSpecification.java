package com.se.management.repository.specification;

import com.se.management.domain.SkillItem;
import com.se.management.model.SkillSearch;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public class SkillItemSpecification extends SearchSpecification< SkillItem,SkillSearch> {


    @Override
    public Specification<SkillItem> getFilter(SkillSearch request) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameContains(request.getSearch())))
            .toPredicate(root, query, cb);
        };

    }

    private Specification<SkillItem> nameContains(String alias) {
        return userAttributeContains("name", alias);
    }


    private Specification<SkillItem> userAttributeContains(String attribute, String value) {
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
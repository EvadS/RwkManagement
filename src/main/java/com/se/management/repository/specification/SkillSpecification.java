package com.se.management.repository.specification;

import com.se.management.domain.Skill;
import com.se.management.model.SkillSearch;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public class SkillSpecification extends SearchSpecification<Skill,SkillSearch> {

    @Override
    public Specification<Skill> getFilter(SkillSearch request) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the addressAttribute specifications
            return where(
                    where(nameContains(request.getSearch())))
            .toPredicate(root, query, cb);
        };

    }

    private Specification<Skill> nameContains(String alias) {
        return userAttributeContains("name", alias);
    }


    private Specification<Skill> userAttributeContains(String attribute, String value) {
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
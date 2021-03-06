package com.se.management.mapper;

import com.se.management.domain.SkillsScore;
import com.se.management.model.request.SkillScoreRequest;
import com.se.management.model.response.SkillResponse;
import com.se.management.model.response.SkillScoreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SkillScoreMapper {

    SkillScoreMapper INSTANCE = Mappers.getMapper(SkillScoreMapper.class);

//    @Mappings({
//            @Mapping(target = "score", source = "score"),
//            @Mapping(target = "name", source = "skillName")
//    })
    SkillsScore SkillRequestToSkill(SkillScoreRequest request);


    SkillScoreResponse SkillToSkillResponse(SkillsScore skillsScore);


    @Mappings({
            @Mapping(source = "skillScore.skill.name", target = "name"),
            @Mapping(source = "skillScore.id", target = "id")
    })
    SkillResponse SkillScoreToSkillResponse(SkillsScore skillScore);
}

package com.se.management.mapper;


import com.se.management.domain.Skill;
import com.se.management.model.request.SkillRequest;
import com.se.management.model.response.SkillResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SkillMapper {

    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    SkillResponse SkillToSkillResponse(Skill skill);

    Skill SkillRequestToSkillItem(SkillRequest skillRequest);
}

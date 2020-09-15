package com.se.management.mapper;


import com.se.management.domain.SkillItem;
import com.se.management.model.request.SkillItemRequest;
import com.se.management.model.response.SkillItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SkillItemMapper {

    SkillItemMapper INSTANCE = Mappers.getMapper(SkillItemMapper.class);

    SkillItemResponse SkillToSkillResponse(SkillItem skillItem);

    SkillItem SkillRequestToSkillItem(SkillItemRequest skillRequest);
}

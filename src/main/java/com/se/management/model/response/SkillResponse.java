package com.se.management.model.response;

import com.se.management.model.converters.SkillNameConverter;
import com.se.management.model.enums.SkillName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SkillResponse {
    @NotNull
    @Convert(converter = SkillNameConverter.class)
    private SkillName skillName = SkillName.NOT_SET;

    private byte score;
}

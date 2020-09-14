package com.se.management.model.request;

import com.se.management.model.converters.SkillNameConverter;
import com.se.management.model.enums.SkillName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SkillRequest {
    @NotNull
    @Convert(converter = SkillNameConverter.class)
    private SkillName skillName;

    @Min(0)
    @Max(10)
    private byte score;
}

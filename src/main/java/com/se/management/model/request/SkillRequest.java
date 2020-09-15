package com.se.management.model.request;

import com.se.management.model.converters.SkillNameConverter;
import com.se.management.model.enums.SkillName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SkillRequest {

    @NotBlank
    private String skillName;

    @Min(0)
    @Max(10)
    private byte score;
}

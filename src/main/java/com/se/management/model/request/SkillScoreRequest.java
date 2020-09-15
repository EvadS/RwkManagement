package com.se.management.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SkillScoreRequest {

    @NotBlank
    private String skillName;

    @Min(0)
    @Max(10)
    private byte score;
}

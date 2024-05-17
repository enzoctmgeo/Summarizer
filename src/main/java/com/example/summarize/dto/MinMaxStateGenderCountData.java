package com.example.summarize.dto;

import lombok.Data;

import java.util.List;

@Data
public class MinMaxStateGenderCountData {
    private List<AgeCountDto> min;
    private List<AgeCountDto> max;
}
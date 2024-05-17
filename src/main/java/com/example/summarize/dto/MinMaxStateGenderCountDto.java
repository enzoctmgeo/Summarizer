package com.example.summarize.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinMaxStateGenderCountDto {
    private String gender;
    private String state;
    private Long total;
}

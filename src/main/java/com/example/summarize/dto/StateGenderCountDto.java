package com.example.summarize.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateGenderCountDto {
    private String state;
    private Long male;
    private Long female;
}

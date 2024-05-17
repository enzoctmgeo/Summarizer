package com.example.summarize.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class AgeCountData {
    private List<AgeCountDto> greaterEquals50;
    private List<AgeCountDto> lowerEquals20;
}

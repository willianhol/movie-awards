package com.awards.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class IntervalResultDTO {
    private List<ProducerIntervalDTO> min;
    private List<ProducerIntervalDTO> max;
}
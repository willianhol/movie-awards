package com.awards.api.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProducerIntervalDTO {

    private String producer;

    private int interval;

    private int previousWin;

    private int followingWin;
}
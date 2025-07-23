package com.awards.api.dto;

import com.awards.service.ProducerAwardInterval;

import java.util.List;
import java.util.Map;

public class ApiResponseAdapter {

    private ApiResponseAdapter() {
    }

    public static IntervalResultDTO convertReturn(Map<String, List<ProducerAwardInterval>> stringListMap) {
        List<ProducerAwardInterval> min = stringListMap.get("min");
        List<ProducerAwardInterval> max = stringListMap.get("max");

        return IntervalResultDTO.builder()
                .min(min.stream().map(ApiResponseAdapter::convertProducerInterval).toList())
                .max(max.stream().map(ApiResponseAdapter::convertProducerInterval).toList())
                .build();
    }

    private static ProducerIntervalDTO convertProducerInterval(ProducerAwardInterval producerAwardInterval) {

        return ProducerIntervalDTO.builder()
                .producer(producerAwardInterval.getProducer())
                .interval(producerAwardInterval.getInterval())
                .previousWin(producerAwardInterval.getPreviousWin())
                .followingWin(producerAwardInterval.getFollowingWin())
                .build();
    }

}

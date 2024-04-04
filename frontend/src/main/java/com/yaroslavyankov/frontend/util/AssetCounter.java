package com.yaroslavyankov.frontend.util;

import com.yaroslavyankov.frontend.dto.PositionResponse;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AssetCounter {

    public static Map<String, Long> countSectors(List<PositionResponse> positions,
                                                 Function<PositionResponse, String> sectorExtractor) {

        return positions.stream()
                .map(sectorExtractor)
                .collect(Collectors.groupingBy(InstrumentTypeMapper::valueOfRussianName, Collectors.counting()));

    }
}

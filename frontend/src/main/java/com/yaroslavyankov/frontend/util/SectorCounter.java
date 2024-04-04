package com.yaroslavyankov.frontend.util;

import com.yaroslavyankov.frontend.dto.Asset;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SectorCounter {

    public static Map<String, Long> countSectors(List<Asset> positions,
                                                 Function<Asset, String> sectorExtractor) {

        return positions.stream()
                .filter(obj -> obj.getSector() != null)
                .map(sectorExtractor)
                .collect(Collectors.groupingBy(SectorMapper::valueOfRussianName, Collectors.counting()));

    }
}

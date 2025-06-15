package com.projects.marketmosaic.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.marketmosaic.common.exception.MarketMosaicCommonException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new MarketMosaicCommonException("Failed to parse JSON", e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        try {
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new MarketMosaicCommonException("Failed to parse JSON", e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new MarketMosaicCommonException("Failed to convert object to JSON", e);
        }
    }

    public static Map<String, Object> toMap(Object obj) {
        return mapper.convertValue(obj, new TypeReference<>() {
        });
    }
}

package com.fastcampus.fastcampusprojectboardreview.util;

import java.util.Map;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestComponent
public class FormDataEncoder {

    private final ObjectMapper mapper;

     FormDataEncoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }


     public String encode(Object obj) {
        Map<String, String> fieldMap = mapper.convertValue(obj, new TypeReference<>() {});
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.setAll(fieldMap);

        return UriComponentsBuilder.newInstance()
                .queryParams(valueMap)
                .encode()
                .build()
                .getQuery();
    }

}

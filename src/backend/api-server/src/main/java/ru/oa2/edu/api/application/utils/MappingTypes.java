package ru.oa2.edu.api.application.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.oa2.edu.api.domain.dto.RequestData;

public class MappingTypes {

    final static ObjectMapper objectMapper = new ObjectMapper();

    public static RequestData parseRequestData(Object object) throws Exception {
        return objectMapper.readValue((JsonParser) object, RequestData.class);
    }

    public static String marshalRequestData(RequestData requestData) throws Exception {
        return objectMapper.writeValueAsString(requestData);
    }
}

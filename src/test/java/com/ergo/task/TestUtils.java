package com.ergo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class TestUtils {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata";

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getContentFromFile(String path) throws IOException {
        File file = new File(TEST_DATA_PATH + path);
        JsonNode jsonNode = objectMapper.readTree(file);
        return objectMapper.writeValueAsString(jsonNode);
    }

    public static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}

package hello.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonParser {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String prepareObject(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    public static <T> T parseObject(String obj, Class<T> c) throws IOException {
        return mapper.readValue(obj, c);
    }
}

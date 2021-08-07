package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Json {
    private static final TypeFactory typeFactory = JsonMapper.singleton().getTypeFactory();
    private static final Logger log = LoggerFactory.getLogger(Json.class.getSimpleName());

    public static String getJsonFrom(Object object) {
        try {
            return JsonMapper.singleton().writeValueAsString(object);

        } catch(Exception e) {
            log.info(e.getMessage());
            return "{}";
        }
    }

    public static <T> T deserializeAs(String content, final Class<T> clazz) {
        try {
            JavaType type = typeFactory.constructType(clazz);
            return JsonMapper.singleton().readValue(content, type);

        } catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public static <T> List<T> deserializeAsListOf(String content, final Class<T> clazz)  {
        try {
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, clazz);
            return JsonMapper.singleton().readValue(content, collectionType);

        } catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public static <T> T deserializeWithTypeReference(String content, TypeReference<T> typeReference)  {
        try {
            return JsonMapper.singleton().readValue(content, typeReference);

        } catch(Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }
}
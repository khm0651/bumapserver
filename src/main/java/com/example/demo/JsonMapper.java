package com.example.demo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class JsonMapper extends ObjectMapper {
    private static JsonMapper jsonMapper;

    private JsonMapper() {
        this.findAndRegisterModules()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//                .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                .setDateFormat(new SimpleDateFormat(UTC.ISO_FIXED_DATE_TIME_PATTERN))
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
                .setTimeZone(TimeZone.getTimeZone(UTC.UTC_ZONE))
                .registerModule((new SimpleModule())
                        .addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer())
                        .addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer())
                        .addDeserializer(Date.class, new DateDeserializers.DateDeserializer())
                        .addSerializer(Date.class, new DateSerializer())
                );
    }

    public static JsonMapper singleton() {
        if(jsonMapper == null) jsonMapper = new JsonMapper();
        return JsonMapper.jsonMapper;
    }

    static class ZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
        @Override public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return UTC.parseOrNull(p.getValueAsString());
        }
    }

    static class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {
        @Override public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(UTC.getIsoFixedDateTimeStringFrom(value));
        }
    }
}
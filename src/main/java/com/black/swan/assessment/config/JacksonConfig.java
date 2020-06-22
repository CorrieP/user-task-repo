package com.black.swan.assessment.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Configuration
public class JacksonConfig {
    public static final String ZONE_OFFSET = "+02:00";
    @Bean
    public ObjectMapper provideObjectMapper(){

        var module = new JavaTimeModule();
        module.addSerializer(OffsetDateTime.class, new JsonSerializer<>() {
            @Override
            public void serialize(OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd HH:mm:ss")
                        .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                        .toFormatter();
                jsonGenerator.writeString(offsetDateTime.atZoneSameInstant(ZoneId.of(ZONE_OFFSET)).format(formatter));
            }
        });
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<>() {
            @Override
            public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd HH:mm:ss")
                        .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
                        .toFormatter();
                var dateTime = LocalDateTime.parse(parser.getText(), formatter);
                return OffsetDateTime.of(dateTime, ZoneOffset.of(ZONE_OFFSET));
            }
        });

        return new ObjectMapper()
                .registerModule(module)
                .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .enable(SerializationFeature.INDENT_OUTPUT);

    }
}

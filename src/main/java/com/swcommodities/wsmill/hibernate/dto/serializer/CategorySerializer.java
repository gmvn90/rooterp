package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.Category;

/**
 * Created by dunguyen on 7/21/16.
 */
public class CategorySerializer extends JsonSerializer<Category> {
    @Override
    public void serialize(Category value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeObjectField("created", value.getCreated());
        jgen.writeObjectField("updated", value.getUpdated());
        jgen.writeObjectField("children", value.getChildren());
        jgen.writeObjectField("options", value.getOptions());
        //System.out.println(value.getName());
        //System.out.println(value.getOptions());
        //System.out.println(value.getChildren());
        //System.out.println(value.getName());


        jgen.writeEndObject();
    }
}

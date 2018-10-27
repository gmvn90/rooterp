/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.User;

/**
 *
 * @author macOS
 */
public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("fullName", value.getFullName());
        jgen.writeStringField("phone", value.getPhone());
        jgen.writeStringField("email", value.getEmail());
        jgen.writeObjectField("dob", value.getDob());
        //System.out.println(value.getOptions());
        //System.out.println(value.getChildren());
        //System.out.println(value.getName());


        jgen.writeEndObject();
    }
}
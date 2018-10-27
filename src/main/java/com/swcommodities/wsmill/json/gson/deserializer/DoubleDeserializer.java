/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcommodities.wsmill.json.gson.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 *
 * @author kiendn
 */
public class DoubleDeserializer  implements JsonDeserializer<Double>{

    @Override
    public Double deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        try {
            return (!je.getAsString().equals("")) ? Double.parseDouble(je.getAsString().replace(',', '.')) : 0;
        } catch (NumberFormatException e) {
            throw new JsonParseException(e);
        }
    }
    
}

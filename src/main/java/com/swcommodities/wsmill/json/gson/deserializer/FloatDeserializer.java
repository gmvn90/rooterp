package com.swcommodities.wsmill.json.gson.deserializer;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class FloatDeserializer implements JsonDeserializer<Float>{
	@Override
    public Float deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        try {
            return (!je.getAsString().equals("")) ? Float.parseFloat(je.getAsString().replace(',', '.')) : 0;
        } catch (NumberFormatException e) {
            throw new JsonParseException(e);
        }
    }
}

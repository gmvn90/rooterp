package com.swcommodities.wsmill.json.jackson;
import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeserializer extends JsonDeserializer<Date>
{
    @Override
    public Date deserialize(JsonParser jsonparser,
            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {

        //SimpleDateFormat format = new SimpleDateFormat(Common.date_format_a);
        long date = jsonparser.getLongValue();
        System.out.println("DAte is: " + date);
        return new Date(date);

    }

}
package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;

@Component
public class DeliveryListViewInstructionSerializer extends JsonSerializer<DeliveryInstruction> {
	public DeliveryListViewInstructionSerializer() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public void serialize(DeliveryInstruction value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("refNumber", value.getRefNumber());
        
        jgen.writeEndObject();
    }
	
}

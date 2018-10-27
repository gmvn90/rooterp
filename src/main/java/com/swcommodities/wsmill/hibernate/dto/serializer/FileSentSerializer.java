package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.FileSent;

@Component
public class FileSentSerializer  extends JsonSerializer<FileSent> {
	public FileSentSerializer() {
		
	}
	
	@Override
    public void serialize(FileSent value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("updater", value.getUpdater());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("emails", value.getEmails());
        try {
        	jgen.writeStringField("fileUploadUrl", value.getFileUpload().getUrl());
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
        	jgen.writeStringField("fileUploadName", value.getFileUpload().getOriginalName());
        } catch (Exception e) {
			// TODO: handle exception
		}
        jgen.writeEndObject();
    }
}

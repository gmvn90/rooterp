package com.swcommodities.wsmill.hibernate.dto.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.swcommodities.wsmill.hibernate.dto.FileUpload;

public class FileUploadSerializer extends JsonSerializer<FileUpload>{
	public FileUploadSerializer() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
    public void serialize(FileUpload value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        
        jgen.writeEndObject();
    }

}

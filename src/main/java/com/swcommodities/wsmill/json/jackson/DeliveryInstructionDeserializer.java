package com.swcommodities.wsmill.json.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.swcommodities.wsmill.hibernate.dto.CompanyMaster;
import com.swcommodities.wsmill.hibernate.dto.DeliveryInstruction;

public class DeliveryInstructionDeserializer extends StdDeserializer<DeliveryInstruction> { 
	 
    public DeliveryInstructionDeserializer() { 
        this(null); 
    } 
 
    public DeliveryInstructionDeserializer(Class<?> vc) { 
        super(vc); 
    }
 
    @Override
    public DeliveryInstruction deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
    	//2016-12-31 16:31:37
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JsonNode node = jp.getCodec().readTree(jp);
        //String itemName = node.get("itemName").asText();
        Integer id = null;
        try {
        	id = node.get("id").asInt();
        	
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
        	String dateString = node.get("date").asText();
			Date date = format.parse(dateString);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
        CompanyMaster companyMaster = new CompanyMaster();
        companyMaster.setId(node.get("companyMasterByClientId").asInt());
        DeliveryInstruction deliveryInstruction = new DeliveryInstruction();
        deliveryInstruction.setId(id);
        
        deliveryInstruction.setCompanyMasterByClientId(companyMaster);
        String refNumber = "";
        try {
        	refNumber = node.get("refNumber").asText();
        } catch (Exception e) {
			// TODO: handle exception
		}
        
        deliveryInstruction.setRefNumber(refNumber);
        
        return deliveryInstruction;
    }
}
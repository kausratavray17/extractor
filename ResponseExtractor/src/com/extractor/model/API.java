package com.extractor.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.extractor.engine.TempCache;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class API {
	
	public String URL;
	public TempCache payload;
	public String rawPayload;
	public String rawEffectivePaylod;
	public TempCache response;
	public boolean isRoot;
	public LinkedHashMap<String,String> correlationMapping = new LinkedHashMap<String,String>();
	
	
	
	public API(String uRL, TempCache payload, String rawPayload, String rawEffectivePaylod, TempCache response,
			boolean isRoot, LinkedHashMap<String, String> correlationMapping) {
		super();
		URL = uRL;
		this.payload = payload;
		this.rawPayload = rawPayload;
		this.rawEffectivePaylod = rawEffectivePaylod;
		this.response = response;
		this.isRoot = isRoot;
		this.correlationMapping = correlationMapping;
	}
	
	
	
	public String getURL() {
		return URL;
	}



	public void setURL(String uRL) {
		URL = uRL;
	}



	public TempCache getPayload() {
		return payload;
	}



	public void setPayload(TempCache payload) {
		this.payload = payload;
	}



	public String getRawPayload() {
		return rawPayload;
	}



	public void setRawPayload(String rawPayload) {
		this.rawPayload = rawPayload;
	}



	public String getRawEffectivePaylod() {
		return rawEffectivePaylod;
	}



	public void setRawEffectivePaylod(String rawEffectivePaylod) {
		this.rawEffectivePaylod = rawEffectivePaylod;
	}



	public TempCache getResponse() {
		return response;
	}



	public void setResponse(TempCache response) {
		this.response = response;
	}



	public boolean isRoot() {
		return isRoot;
	}



	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}



	public LinkedHashMap<String, String> getCorrelationMapping() {
		return correlationMapping;
	}



	public void setCorrelationMapping(LinkedHashMap<String, String> correlationMapping) {
		this.correlationMapping = correlationMapping;
	}



	public void generateCorrelationalMapping(String key, String value)
	{
		this.correlationMapping.put(key, value);
	}
	
	public void generateEffectiveTempCachePayload()
	{
		for(Map.Entry<String, String> key : this.correlationMapping.entrySet() )
			this.payload.getInstance().addElement(key.getKey(),key.getValue());
	}
	
	public void generateRawEffectivePayload() throws IOException
	{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(this.rawPayload);
		
		for(Map.Entry<String,String> key : this.correlationMapping.entrySet())
			((ObjectNode) rootNode).put(key.getKey(), key.getValue());
		
		this.rawPayload=rootNode.toString();
	}
	
}

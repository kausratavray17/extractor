package com.extractor.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.extractor.test.Test;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;

public class HttpConnection {

	public static void main(String[] args) throws IOException
	{
		createTempCache();
		Test test = new Test();
		
		//callAPI("www.json-generator.com","http","/api/json/get/bUqyGNdlyW?indent=1");
		callAPI("jsonplaceholder.typicode.com","http","/photos/");
		
		printMap();
		
		
	}
	
	public static void createTempCache()
	{
		
	}
	
	public static void printMap()
	{
		TempCache.getInstance().printMap();
	}
	
	public static void callAPI(String host, String method, String path) throws IOException
	{
		//String Path=path+TempCache.getInstance().getElement(key);
		String URL=method+"://"+host+path;
		System.out.println("URL: "+URL);
		try {
		URL url = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		con.setRequestMethod("GET");
		System.out.println("Success");
		int status = con.getResponseCode();
		System.out.println(status);
		
		Map<String, List<String>> map = con.getHeaderFields();

		System.out.println("Printing Response Header...\n");

		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			System.out.println(entry.getKey()
	                           +" ------> " + entry.getValue());
		}
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			//System.out.println("*****");
			response.append(inputLine);
		}
		in.close();
		System.out.println(response);
		parseResponse(response.toString());
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
	}
	public static void parseResponse(String response) throws IOException
	{
		JsonFactory factory = new JsonFactory();
		try {
	       ObjectMapper mapper = new ObjectMapper(factory);
	       mapper.enable(SerializationFeature.INDENT_OUTPUT);
	       JsonNode rootNode = mapper.readTree(response);
	       traverse(rootNode,false,"test");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void traverse(JsonNode node, boolean isArrayElement, String keyval)
	{
		if (node.isObject())
        {
            Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();

            ArrayList<Map.Entry<String, JsonNode>> nodesList = Lists.newArrayList(iterator);
            for (Map.Entry<String, JsonNode> nodEntry : nodesList)
            {
                String key = nodEntry.getKey();
                JsonNode newNode = nodEntry.getValue();
                traverse(newNode,false,key);
            }
        }
        else if (node.isArray())
        {
            Iterator<JsonNode> arrayItemsIterator = node.elements();
            ArrayList<JsonNode> arrayItemsList = Lists.newArrayList(arrayItemsIterator);
            for (JsonNode arrayNode : arrayItemsList)
            {
                traverse(arrayNode,true,keyval);
            }
        }
        else
        {
            if (node.isValueNode())
            {
            		if(isArrayElement)
            			System.out.println("Key: "+keyval+",   val: "+node.asText());
            		else
            			System.out.println("Key: "+keyval+",   val: "+node.asText());
            		TempCache.getInstance().addElement(keyval, node.asText());
            }
            else
            {
                System.out.println("  node some other type");
            }
        }
	}
}

package com.extractor.engine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class TempCache {

	 private LinkedHashMap<String,ArrayList<String>> hm=new LinkedHashMap<String,ArrayList<String>>();
	 private static TempCache tempCache = null;
	 
	 public static TempCache getInstance()
	 {
		 if(tempCache == null)
			 tempCache = new TempCache();
		 return tempCache;
	 }
	 
	 public void addElement(String key, String value)
	 {
		 this.hm.putIfAbsent(key, new ArrayList<String>());
		 this.hm.get(key).add(value);
	 }
	 
	 public void printMap()
	 {
		 System.out.println("=================================\n\n\n");
	 for (Entry<String, ArrayList<String>> ee : this.hm.entrySet()) {
		    String key = ee.getKey();
		    System.out.println("Key: "+key);
		    List<String> values = ee.getValue();
		    for(String e : values)
		    		System.out.println(e);
		}
	 }
}

package com.entheos.store.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(ObjectUtils.class);
	
	private ObjectUtils() {}

	public static String logObject(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
    	String jsonString = "";
		try {
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage(), e);
		}
    	return jsonString;
	}
}

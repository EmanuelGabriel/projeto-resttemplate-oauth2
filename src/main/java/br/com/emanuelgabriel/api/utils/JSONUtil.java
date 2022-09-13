package br.com.emanuelgabriel.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author emanuel.sousa
 *
 */

public class JSONUtil {

	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
	}

	/**
	 * Converter Java para JSON
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertJavaToJson(Object obj) {
		String resultAsJSON = "";
		try {
			resultAsJSON = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultAsJSON;
	}

	/**
	 * Converter JSON para Java
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T convertJsonToJava(String jsonString, Class<T> cls) {
		T resultAsJavaObject = null;
		try {
			resultAsJavaObject = mapper.readValue(jsonString, cls);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultAsJavaObject;
	}

}

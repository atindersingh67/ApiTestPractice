package com.demo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(DataService.class);
	public static String readJson(File file) {
		FileReader reader;
		JSONObject payloadlist = null;
		try {
			reader = new FileReader(file);
			JSONParser jsonParser = new JSONParser();

			// Read JSON file
			Object obj = jsonParser.parse(reader);

			payloadlist = (JSONObject) obj;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("-------------------------- readJson------------" + e.getMessage(),e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("-------------------------- readJson------------" + e.getMessage(),e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("-------------------------- readJson------------" + e.getMessage(),e);
		}

		// LOGGER.log(Level.INFO, "payloadlist: "+payloadlist);
		return payloadlist.toString();
	}
}

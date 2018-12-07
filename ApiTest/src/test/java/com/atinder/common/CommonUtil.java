package com.atinder.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommonUtil {


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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// LOGGER.log(Level.INFO, "payloadlist: "+payloadlist);
		return payloadlist.toString();
	}
}

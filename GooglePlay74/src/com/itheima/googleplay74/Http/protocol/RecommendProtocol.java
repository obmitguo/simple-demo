package com.itheima.googleplay74.Http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecommendProtocol extends BaseProtocol<ArrayList<String>> {

	@Override
	public String getKey() {
		
		return "recommend";
	}

	@Override
	public String getParams() {
		
		return "";
	}

	@Override
	public ArrayList<String> parseData(String result) {
		try {
			JSONArray ja = new JSONArray(result);
			ArrayList<String> list=new ArrayList<String>();
			for(int i=0;i<ja.length();i++){
				String keyword = ja.getString(i);
				list.add(keyword);
			}
			return list;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

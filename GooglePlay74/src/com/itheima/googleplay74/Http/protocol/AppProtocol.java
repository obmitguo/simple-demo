package com.itheima.googleplay74.Http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.googleplay74.doman.appInfo;

public class AppProtocol extends BaseProtocol<ArrayList<appInfo>> {

	@Override
	public String getKey() {
		return "app";
	}

	@Override
	public String getParams() {
		return ""; // 返回一个空串
	}

	@Override
	public ArrayList<appInfo> parseData(String result){
		try {
			JSONArray ja= new JSONArray(result);
			ArrayList<appInfo> infolist=new ArrayList<appInfo>();
			for(int i=0;i<ja.length();i++){
				appInfo appInfo = new appInfo();
				JSONObject jo1 = ja.getJSONObject(i);
				appInfo.des= jo1.getString("des");
				appInfo.downloadUrl= jo1.getString("downloadUrl");
				appInfo.size= jo1.getLong("size");
				appInfo.stars= (float) jo1.getDouble("stars");
				appInfo.iconUrl= jo1.getString("iconUrl");
				appInfo.packageName= jo1.getString("packageName");
				appInfo.name= jo1.getString("name");
				appInfo.id= jo1.getString("id");
				infolist.add(appInfo);
				}
			return infolist;
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

		return null;
	}

}

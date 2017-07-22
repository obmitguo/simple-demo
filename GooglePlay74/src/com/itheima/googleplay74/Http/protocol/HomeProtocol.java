package com.itheima.googleplay74.Http.protocol;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.googleplay74.doman.appInfo;
public class HomeProtocol extends BaseProtocol<ArrayList<appInfo>> {

	private ArrayList<String> pictures;

	@Override
	public String getKey() {
		
		return "home";
	}

	@Override
	public String getParams() {
		// TODO Auto-generated method stub
		return "";//如果没有参数就传空串
	}

	@Override
	public ArrayList<appInfo> parseData(String result) {
		//使用JsonObject解析方式：如果遇到{}，就是JsonObject;如果遇到[],就是jsonArray
		try {
			JSONObject jo = new JSONObject(result);
			JSONArray ja = jo.getJSONArray("list");
			//常规解析方式,解析主要数据
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
			//解析轮播图的地址
			JSONArray ja1 = jo.getJSONArray("picture");
			pictures = new ArrayList<String>();
			for(int i=0;i<ja1.length();i++){
				String pic = ja1.getString(i);
				pictures.add(pic);
			}
			return infolist; //将appinfo的信息返回
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getPictureList(){
		
		return pictures;
	}

}

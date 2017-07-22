package com.itheima.googleplay74.Http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itheima.googleplay74.doman.CategotyInfo;

/**
 * 分类模块请求网络
 * 
 * @author Kevin
 * @date 2015-11-1
 */
public class CategoryProtocol extends BaseProtocol<ArrayList<CategotyInfo>> {

	@Override
	public String getKey() {
		return "category";
	}

	@Override
	public String getParams() {
		return "";
	}

	@Override
	public ArrayList<CategotyInfo> parseData(String result) {
		try {
			JSONArray ja = new JSONArray(result);

			ArrayList<CategotyInfo> list = new ArrayList<CategotyInfo>();
			for (int i = 0; i < ja.length(); i++) {// 遍历大分类, 2次
				JSONObject jo = ja.getJSONObject(i);

				// 初始化标题对象
				if (jo.has("title")) {// 判断是否有title这个字段
					CategotyInfo titleInfo = new CategotyInfo();
					titleInfo.title = jo.getString("title");
					titleInfo.istitle = true;
					list.add(titleInfo);
				}

				// 初始化分类对象
				if (jo.has("infos")) {
					JSONArray ja1 = jo.getJSONArray("infos");

					for (int j = 0; j < ja1.length(); j++) {// 遍历小分类
						JSONObject jo1 = ja1.getJSONObject(j);
						CategotyInfo info = new CategotyInfo();
						info.name1 = jo1.getString("name1");
						info.name2 = jo1.getString("name2");
						info.name3 = jo1.getString("name3");
						info.url1 = jo1.getString("url1");
						info.url2 = jo1.getString("url2");
						info.url3 = jo1.getString("url3");
						info.istitle = false;

						list.add(info);
					}
				}
			}

			return list;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}

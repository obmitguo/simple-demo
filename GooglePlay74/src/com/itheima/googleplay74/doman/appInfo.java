package com.itheima.googleplay74.doman;

import java.util.ArrayList;

public class appInfo {
	
	public String id;
	public String name;
	public String packageName;
	public String iconUrl;
	public float stars;
	public long size;
	public String downloadUrl;
	public String des;
	
	//补充字段，供应用详情页使用
	public String author;
	public String date;
	public String downloadNum;
	public String version;
	public ArrayList<SafeInfo> safe;
	public ArrayList<String> screen;
	
	//当一个内部类是public static的时候，和外部类没什么区别
	public static class SafeInfo{
		public String safeDes;
		public String safeDesUrl;
		public String safeUrl;
	}
}

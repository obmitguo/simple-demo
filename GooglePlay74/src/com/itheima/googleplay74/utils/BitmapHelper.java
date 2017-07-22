package com.itheima.googleplay74.utils;

import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {
	private static BitmapUtils mBitmapUtils=null;
	//单例，懒汉
	public static BitmapUtils getBitmapUtils(){
		if(mBitmapUtils==null){
			synchronized (BitmapHelper.class) {  //加一个同步锁
				if(mBitmapUtils==null){
					mBitmapUtils=new BitmapUtils(UIUtils.getContext());
				}
			}
		}
		return mBitmapUtils;
	}
}

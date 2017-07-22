package com.itheima.googleplay74.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtils {
	//获取一个形状
	public static GradientDrawable getGradientDrawable(int color,int radius){
		//xml中定义的shape标签 对应此类
		GradientDrawable sharp = new GradientDrawable();
		sharp.setShape(GradientDrawable.RECTANGLE);
		sharp.setCornerRadius(radius);//设置圆角半径
		sharp.setColor(color);
		return sharp;
	}
	//获得一个状态选择器
	public static StateListDrawable getSelector(Drawable normal,Drawable press){
		StateListDrawable selector = new StateListDrawable();
		selector.addState(new int []{android.R.attr.state_pressed}, press);//按下图片
		selector.addState(new int[]{}, normal);//默认图片
		return selector;
	}
	
	//重载
	public static StateListDrawable getSelector(int normal,int press,int radius){
		GradientDrawable bgNormal = getGradientDrawable(normal,radius);
		GradientDrawable bgPress = getGradientDrawable(press,radius);
		StateListDrawable selector = getSelector(bgNormal,bgPress);
		return selector;
	}
}

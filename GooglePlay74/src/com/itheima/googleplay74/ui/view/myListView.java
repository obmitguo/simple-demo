package com.itheima.googleplay74.ui.view;

import com.itheima.googleplay74.utils.UIUtils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

public class myListView extends ListView {

	public myListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public myListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public myListView(Context context) {
		super(context);
		initView();
	}

	private void initView() {
		//设置自己需要的样式，然后创建自己的myListView
		this.setSelector(new ColorDrawable());//设置默认状态选择器为全透明
		this.setDivider(null);//设置分割线
		this.setCacheColorHint(Color.TRANSPARENT);//有时候滑动背景会变成黑色，此方法可以将背景设为透明
	}

}

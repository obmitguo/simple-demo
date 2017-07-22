package com.itheima.googleplay74.ui.holder;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;


import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AppHolder extends BaseHolder<appInfo>{
	//应用布局

	private ImageView iv_cone;
	private TextView tv_name;
	private TextView tv_memor;
	private TextView tv_des;
	private BitmapUtils bitmapUtils;
	private RatingBar rb_pro;
	
	@Override
	public View initView(){
		//1.加载布局
		View view = UIUtils.inflate(R.layout.list_item_app);
		/*//2.初始化控件
		tvContent = (TextView) view.findViewById(R.id.tv_data);*/
		iv_cone = (ImageView) view.findViewById(R.id.iv_icon);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_memor = (TextView) view.findViewById(R.id.tv_memor);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
		//使用bitmapUtils,单例模式
		bitmapUtils = BitmapHelper.getBitmapUtils();
		rb_pro = (RatingBar) view.findViewById(R.id.rb_pro);
		return view;
	}
	
	@Override
	public void refreshView(appInfo data){
		//设置数据
		bitmapUtils.display(iv_cone, HttpHelper.URL+"image?name="+data.iconUrl);
		tv_des.setText(data.des);
		tv_memor.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
		tv_name.setText(data.name);
		rb_pro.setRating(data.stars);
	}

}

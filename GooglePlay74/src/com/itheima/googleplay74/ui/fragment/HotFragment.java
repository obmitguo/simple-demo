package com.itheima.googleplay74.ui.fragment;

import java.util.ArrayList;
import java.util.Random;

import com.itheima.googleplay74.Http.protocol.HotProtocol;
import com.itheima.googleplay74.ui.view.FlowLayout;
import com.itheima.googleplay74.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay74.utils.DrawableUtils;
import com.itheima.googleplay74.utils.UIUtils;

import android.R.color;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 排行
 * @author Kevin
 * @date 2015-10-27
 */
public class HotFragment extends BaseFragment {

	private ArrayList<String> data;

	@Override
	public View onCreateSuccessView() {
		
		ScrollView scrollView = new ScrollView(UIUtils.getContext());
		FlowLayout flow = new FlowLayout(UIUtils.getContext());//自定义控件
		//设置padding的值
		int padding = UIUtils.dip2px(10);
		flow.setPadding(padding, padding, padding, padding);
		//设置flow的左右上下的宽度
		flow.setHorizontalSpacing(UIUtils.dip2px(6));//水平间距
		
		flow.setVerticalSpacing(UIUtils.dip2px(8));//竖直间距
		
		for(int i=0;i<data.size();i++){
			String keyword = data.get(i);
			TextView view = new TextView(UIUtils.getContext());
			//view.setTextColor(color.black);
			view.setTextColor(Color.WHITE);  //设置文字颜色
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);//设置文字大小
			view.setGravity(Gravity.CENTER);
			view.setPadding(padding, padding, padding, padding);
			Random random = new Random();
			int r=30+random.nextInt(200);
			int g=30+random.nextInt(200);
			int b=30+random.nextInt(200);
			
			int color =0xffcecece;//按下后偏白色
			StateListDrawable selector = DrawableUtils.getSelector(Color.rgb(r, g, b), color,UIUtils.dip2px(6));  //状态选择器
			view.setText(keyword);
			view.setBackgroundDrawable(selector);//设置DrawableUtils的矩形背景
			view.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
				}
			});
			flow.addView(view);//添加 
		}
		scrollView.addView(flow);
		return scrollView;
	}
	
	@Override
	public ResultState onLoad() {
		//获取数据并校验
		HotProtocol protocol = new HotProtocol();
		data = protocol.getData(0);
		return check(data);
	}

}

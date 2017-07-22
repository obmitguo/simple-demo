package com.itheima.googleplay74.ui.activity;

import java.io.Serializable;
import java.util.ArrayList;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class disPicActivity extends Activity {
	private ViewPager display;
	private ArrayList<String> list;
	private BitmapUtils bitmapUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_display_activity);
		initUI();
		initdata();
	}

	private void initdata(){
		list = (ArrayList<String>) getIntent().getSerializableExtra("list");
		int index = getIntent().getIntExtra("index", 0);
		display.setAdapter(new myAdapter());
		display.setCurrentItem(index);
	}

	private void initUI() {
		display = (ViewPager) findViewById(R.id.vp_display);
		bitmapUtils = BitmapHelper.getBitmapUtils();
	}
	
	//适配器
	class myAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}
		
		@Override
		public Object instantiateItem(View container, int position){
			ImageView imageView = new ImageView(UIUtils.getContext());
			bitmapUtils.display(imageView, HttpHelper.URL+"image?name="+list.get(position));
			((ViewGroup) container).addView(imageView);
			return imageView;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
		
	}
}

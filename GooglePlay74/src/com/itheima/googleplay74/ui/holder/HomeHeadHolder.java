package com.itheima.googleplay74.ui.holder;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

public class HomeHeadHolder extends BaseHolder<ArrayList<String>> {
	private ViewPager mViewPager;
	private ArrayList<String> data;
	private LinearLayout llContainer;
	private int mPreviousPos;//上个圆点位置
	@Override
	public View initView() {
		// 创建根布局, 相对布局
				RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());
				// 初始化布局参数, 根布局上层控件是listview, 所以要使用listview定义的LayoutParams
				AbsListView.LayoutParams params = new AbsListView.LayoutParams(
						AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
				rlRoot.setLayoutParams(params);

				// ViewPager
				mViewPager = new ViewPager(UIUtils.getContext());
				RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT);
				// mViewPager.setLayoutParams(vpParams);
				rlRoot.addView(mViewPager, vpParams);// 把viewpager添加给相对布局

				// 初始化指示器
				llContainer = new LinearLayout(UIUtils.getContext());
				llContainer.setOrientation(LinearLayout.HORIZONTAL);// 水平方向

				RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
						RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);

				// 设置内边距
				int padding = UIUtils.dip2px(10);
				llContainer.setPadding(padding, padding, padding, padding);

				// 添加规则, 设定展示位置
				llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);// 底部对齐
				llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);// 右对齐

				// 添加布局
				rlRoot.addView(llContainer, llParams);

				return rlRoot;
	}

	@Override
	public void refreshView(final ArrayList<String> data){
			this.data=data;
			//填充viewpager的数据
			mViewPager.setAdapter(new HomeHeaderAdapter());
			mViewPager.setCurrentItem(data.size()*100000); //将当前位置设置到很远很远
			//初始化指示器
			for(int i=0;i<data.size();i++){
				ImageView point = new ImageView(UIUtils.getContext());
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				if(i==0){
					point.setImageResource(R.drawable.indicator_selected);
				}else{
					point.setImageResource(R.drawable.indicator_normal);
					//point.setPadding(0, 6, 6, 0);
					params.leftMargin = UIUtils.dip2px(4);// 左边距
				}
				point.setLayoutParams(params); //设置边距
				llContainer.addView(point);
				//设置选中的监听事件
				mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
					
					public void onPageSelected(int position) {
						position=position%data.size();
						//当前点被选中
						ImageView point = (ImageView) llContainer.getChildAt(position);
						point.setImageResource(R.drawable.indicator_selected);
						//上个点变为不选中
						ImageView prePoint = (ImageView) llContainer.getChildAt(mPreviousPos);
						prePoint.setImageResource(R.drawable.indicator_normal);
						mPreviousPos=position;
					}
					
					public void onPageScrolled(int position, float positionoffSet, int positionOffsetPixels) {
						
					}
					
					public void onPageScrollStateChanged(int state) {
						
					}
				});
				
			}
			
			HomeHeaderTask task = new HomeHeaderTask();
			task.start(); //开始发送消息
	}
	
	class HomeHeaderTask implements Runnable{
		
		public void start(){
			//移除之前发送的所有消息,Handler在此是全局变量，需要先移除之前的消息，防止出现错误
			UIUtils.getHandler().removeCallbacksAndMessages(null);
			UIUtils.getHandler().postDelayed(this, 3000);
		}
		public void run() {
			int currentItem = mViewPager.getCurrentItem();
			currentItem++;
			mViewPager.setCurrentItem(currentItem);
			
			//继续发送延时三秒的消息，实现内循环
			UIUtils.getHandler().postDelayed(this, 3000);  //传递一个Runnable的消息，因为本身就是一个Runnable所以传递一个this
		}
		
	}
	
	
	class HomeHeaderAdapter extends PagerAdapter {
		
		private BitmapUtils bitmapUtils;
		//构造方法
		public HomeHeaderAdapter(){
			//获得bitmaputils
			bitmapUtils = BitmapHelper.getBitmapUtils();
		}
		@Override
		public int getCount() {
			//return data.size();
			return Integer.MAX_VALUE;
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {                                                                                                                             
			return view==object;
		}
		@Override
		public Object instantiateItem(View container, int position){
			position=position%data.size();//解决教标越界问题
			String url = data.get(position);
			//创建一个imageView
			ImageView view = new ImageView(UIUtils.getContext());
			bitmapUtils.display(view, HttpHelper.URL+"image?name="+url);
			
			((ViewGroup) container).addView(view);
			return view;
		}
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
	}
}

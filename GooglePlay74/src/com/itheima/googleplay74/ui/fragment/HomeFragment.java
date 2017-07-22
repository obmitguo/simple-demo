package com.itheima.googleplay74.ui.fragment;

import java.sql.ResultSet;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.protocol.HomeProtocol;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.ui.activity.HomeDetailActivity;
import com.itheima.googleplay74.ui.adapter.myBaseAdapter;
import com.itheima.googleplay74.ui.holder.BaseHolder;
import com.itheima.googleplay74.ui.holder.HomeHeadHolder;
import com.itheima.googleplay74.ui.holder.HomeHolder;
import com.itheima.googleplay74.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay74.ui.view.myListView;
import com.itheima.googleplay74.utils.UIUtils;

/**
 * 首页
 * 
 * @author Kevin
 * @param <T>
 * @date 2015-10-27
 */
public class HomeFragment<T> extends BaseFragment {

	/*private ArrayList<String> sums;*/
	  private ArrayList<appInfo> data;
	private HomeProtocol protocol; 
	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		
		//创建自己的样式的ListView
		myListView myListView = new myListView(UIUtils.getContext());
		
		//给listView添加头布局展示轮播条
		HomeHeadHolder HeaderHolder = new HomeHeadHolder();
		ArrayList<String> picturedata = protocol.getPictureList();
		myListView.addHeaderView(HeaderHolder.getRootView());
		//设置适配器
		if(picturedata!=null){
			HeaderHolder.SetData(picturedata);//调用一下，可以去加载数据
		}
		
		myListView.setAdapter(new myAdapter(data));
		
		//为listview设置点击事件，跳转页面
		myListView.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
					appInfo appInfo = data.get(position-1);//去掉头布局
					Intent intent = new Intent(UIUtils.getContext(),HomeDetailActivity.class);
					intent.putExtra("packagename", appInfo.packageName);//传递包名
					startActivity(intent);
			}
			
		});
		return myListView;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public ResultState onLoad(){
		protocol = new HomeProtocol();
		data =protocol.getData(0);//加载第一页数据
		// 请求网络，校验数据
		return check(data);
	}
	
	class myAdapter extends myBaseAdapter<appInfo>{

		public myAdapter(ArrayList<appInfo> data){
			super(data);
		}

		@Override
		public HomeHolder getHolder(int postion ){
			
			return new HomeHolder();
		}
		
		@Override
		public boolean hasMore() {
			return true;
		}

		@Override
		public ArrayList<appInfo> onLoadMore() {
			/*ArrayList<String> moreData=new ArrayList<String>();
			for(int i=0;i<10;i++){
				moreData.add("测试更多数据："+i);
			}*/
			//睡一下，让数据更加的真实
			//SystemClock.sleep(2000);
			HomeProtocol protocol = new HomeProtocol();
			ArrayList<appInfo> moredata = protocol.getData(getListSize());
			
			return moredata ;
		}
	} 
}

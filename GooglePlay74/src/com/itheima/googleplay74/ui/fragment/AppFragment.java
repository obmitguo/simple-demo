package com.itheima.googleplay74.ui.fragment;

import java.util.ArrayList;

import com.itheima.googleplay74.Http.protocol.AppProtocol;
import com.itheima.googleplay74.Http.protocol.HomeProtocol;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.ui.adapter.myBaseAdapter;
import com.itheima.googleplay74.ui.fragment.HomeFragment.myAdapter;
import com.itheima.googleplay74.ui.holder.AppHolder;
import com.itheima.googleplay74.ui.holder.BaseHolder;
import com.itheima.googleplay74.ui.view.myListView;
import com.itheima.googleplay74.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay74.utils.UIUtils;

import android.view.View;

/**
 * 应用
 * @author Kevin
 * @date 2015-10-27
 */
public class AppFragment extends BaseFragment {
	private ArrayList<appInfo> data;
	//只有成功才走此方法
	@Override
	public View onCreateSuccessView(){
		//创建自己的样式的ListView
				myListView myListView = new myListView(UIUtils.getContext());
				//设置适配器
				myListView.setAdapter(new AppAdapter(data));
				return myListView;
	} 
	
	@Override
	public ResultState onLoad(){
		
		AppProtocol protocol = new AppProtocol();
		data =protocol.getData(0);//加载第一页数据
		// 请求网络，校验数据
		return check(data);
	}
	
	class AppAdapter extends myBaseAdapter<appInfo>{

		public AppAdapter(ArrayList<appInfo> data) {
			super(data);
		}
		
		@Override
		public BaseHolder<appInfo> getHolder(int postion) {
			return new AppHolder();
		}
		
		@Override
		public boolean hasMore() {
			return true;
		}
		
		@Override
		public ArrayList<appInfo> onLoadMore() {
			AppProtocol protocol = new AppProtocol();
			ArrayList<appInfo> moredata = protocol.getData(getListSize());
			return moredata ;
		}
		
	}
}

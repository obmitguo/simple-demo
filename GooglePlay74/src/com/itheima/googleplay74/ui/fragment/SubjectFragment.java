package com.itheima.googleplay74.ui.fragment;

import java.util.ArrayList;

import com.itheima.googleplay74.Http.protocol.SubjectProtocol;
import com.itheima.googleplay74.doman.SubjectInfo;
import com.itheima.googleplay74.ui.adapter.myBaseAdapter;
import com.itheima.googleplay74.ui.holder.BaseHolder;
import com.itheima.googleplay74.ui.holder.HomeHolder;
import com.itheima.googleplay74.ui.holder.SubjectHolder;
import com.itheima.googleplay74.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay74.ui.view.myListView;
import com.itheima.googleplay74.utils.UIUtils;

import android.view.View;

/**
 * 专题
 * @author Kevin
 * @date 2015-10-27
 */
public class SubjectFragment extends BaseFragment {
	private ArrayList<SubjectInfo> data; 
	@Override
	public View onCreateSuccessView(){
		myListView listView = new myListView(UIUtils.getContext());
		listView.setAdapter(new SubjectAdapter(data));  //切记切记
		return listView;
	}
	
	//获得数据，校验数据，更改页面状态
	@Override
	public ResultState onLoad(){
		SubjectProtocol Protocol = new SubjectProtocol();
		data=Protocol.getData(0);
		return check(data);
	}
	
	class SubjectAdapter extends myBaseAdapter<SubjectInfo>{

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder(int postion) {
			return new SubjectHolder();
		}
		
		@Override
		public boolean hasMore() {
			return super.hasMore();
		}
		@Override
		public ArrayList<SubjectInfo> onLoadMore() {
			SubjectProtocol protocol = new SubjectProtocol();
			ArrayList<SubjectInfo> morelist = protocol.getData(getListSize());
			return morelist;
		}
		
	}

}

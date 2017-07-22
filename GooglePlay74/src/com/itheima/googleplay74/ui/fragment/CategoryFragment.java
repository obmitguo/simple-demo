package com.itheima.googleplay74.ui.fragment;

import java.util.ArrayList;

import com.itheima.googleplay74.Http.protocol.CategoryProtocol;
import com.itheima.googleplay74.doman.CategotyInfo;
import com.itheima.googleplay74.ui.adapter.myBaseAdapter;
import com.itheima.googleplay74.ui.holder.BaseHolder;
import com.itheima.googleplay74.ui.holder.CatrgoryHolder;
import com.itheima.googleplay74.ui.holder.TiltleHodler;
import com.itheima.googleplay74.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay74.utils.UIUtils;

import android.view.View;
import android.widget.ListView;

/**
 * 首页
 * @author Kevin
 * @date 2015-10-27
 */
public class CategoryFragment extends BaseFragment {

	private ArrayList<CategotyInfo> data;

	@Override
	public View onCreateSuccessView(){
		ListView listView = new ListView(UIUtils.getContext());
		listView.setAdapter(new CategoryAdapter(data));
		return listView;
	}

	@Override
	public ResultState onLoad() {
		CategoryProtocol protocol = new CategoryProtocol();
		data = protocol.getData(0);
		return check(data);
	}
	
	class CategoryAdapter extends myBaseAdapter<CategotyInfo>{

		public CategoryAdapter(ArrayList<CategotyInfo> data) {
			super(data);
		}
		
		@Override
		public int getViewTypeCount(){
			return super.getViewTypeCount()+1;//在这里是三种类型，所以需要父类的类型+1
		}
		
	
		@Override
		public int getInnerType(int position){
			CategotyInfo info = data.get(position);
			if(info.istitle){
				//返回标题类型
				return super.getInnerType(position)+1;//原来基础类型上+1
			}else{
				//返回普通类型
				return super.getInnerType(position);
			}
		}
		//没有更多不用返回了
		@Override
		public boolean hasMore() {
			return false;
		}
		@Override
		public BaseHolder<CategotyInfo> getHolder(int postion) { 
			//判断是标题类型还是普通类型，来返回不同的holder
			CategotyInfo info = data.get(postion);
			if(info.istitle){
				TiltleHodler holder = new TiltleHodler();
				return holder;
			}else{
				return new CatrgoryHolder();
			}
		}

		@Override
		public ArrayList<CategotyInfo> onLoadMore() {
			
			return null;
		}
		
	}

}

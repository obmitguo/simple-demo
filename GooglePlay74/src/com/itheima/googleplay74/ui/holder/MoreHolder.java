package com.itheima.googleplay74.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.utils.UIUtils;

public class MoreHolder extends BaseHolder<Integer> {
	//加载更多的几种状态
	//1.可以加载更多
	//2.加载更多失败
	//3.没有更多数据
	
	public static final int STATE_MORE_MORE=1;
	public static final int	STATE_MORE_ERROR=2;
	public static final int	STATE_MORE_NONE=3;
	private LinearLayout linear;
	private TextView texview;
	
	public MoreHolder(boolean hasMore) {
			//如果有更多数据，状态未STATE_MORE_MORE，否则为STATE_MORE_NONE，将此状态专题传递给父类的data，父类会同时刷新界面
			SetData(hasMore?STATE_MORE_MORE:STATE_MORE_NONE);  //将Data的数据传递回父类，再由父类传递给refreshView，来加载布局
	}
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_more);
		linear = (LinearLayout) view.findViewById(R.id.ll_more);  //设置布局
		texview = (TextView) view.findViewById(R.id.tv_error);
		return view;
	}

	
	@Override
	public void refreshView(Integer data) {
		switch (data) {
		case STATE_MORE_MORE:
			//显示加载更多
			linear.setVisibility(View.VISIBLE);
			texview.setVisibility(View.GONE);
			break;
		case STATE_MORE_ERROR:
			//显示加载更多错误
			linear.setVisibility(View.GONE);
			texview.setVisibility(View.VISIBLE);
			break;
		case STATE_MORE_NONE:
			//显示加载更多
			linear.setVisibility(View.GONE);
			texview.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

}

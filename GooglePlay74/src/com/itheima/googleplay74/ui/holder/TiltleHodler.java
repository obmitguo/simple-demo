package com.itheima.googleplay74.ui.holder;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.doman.CategotyInfo;
import com.itheima.googleplay74.utils.UIUtils;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class TiltleHodler extends BaseHolder<CategotyInfo> {
	public TextView tvTitle;

	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.list_item_title);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		return view;
	}

	@Override
	public void refreshView(CategotyInfo data) {
		tvTitle.setText(data.title);
	}

}

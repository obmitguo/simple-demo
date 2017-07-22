package com.itheima.googleplay74.ui.holder;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.doman.SubjectInfo;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class SubjectHolder extends BaseHolder<SubjectInfo> {

	private BitmapUtils bitmap;
	private TextView title;
	private ImageView picture;

	@Override
	public View initView() {
		View subject = UIUtils.inflate(R.layout.list_item_subject);
		title = (TextView) subject.findViewById(R.id.tv_title);
		picture = (ImageView) subject.findViewById(R.id.iv_pic);
		//得到bitmap对像
		bitmap =  BitmapHelper.getBitmapUtils();
		return subject;
	}

	@Override
	public void refreshView(SubjectInfo data) {
		title.setText(data.des);
		bitmap.display(picture, HttpHelper.URL+"image?name="+data.url);
	}
}

package com.itheima.googleplay74.ui.holder;

import java.util.ArrayList;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.ui.activity.disPicActivity;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DetailPicsHolder extends BaseHolder<appInfo> {
	private ImageView[] ivPics;
	private BitmapUtils mBitmapUtils;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_picinfo);
		ivPics=new ImageView[5];
		ivPics[0]=(ImageView) view.findViewById(R.id.iv_pic1);
		ivPics[1]=(ImageView) view.findViewById(R.id.iv_pic2);
		ivPics[2]=(ImageView) view.findViewById(R.id.iv_pic3);
		ivPics[3]=(ImageView) view.findViewById(R.id.iv_pic4);
		ivPics[4]=(ImageView) view.findViewById(R.id.iv_pic5);
		//得到bitmap对象
		mBitmapUtils = BitmapHelper.getBitmapUtils();
		return view;
	}
	@Override
	public void refreshView(appInfo data){
		final ArrayList<String> screen = data.screen;
		for(int i=0;i<5;i++){
			if(i<screen.size()){
				mBitmapUtils.display(ivPics[i], HttpHelper.URL+"image?name="+screen.get(i));
				final int index=i;
				ivPics[i].setOnClickListener(new OnClickListener() {
					public void onClick(View v){
						Intent intent = new Intent(UIUtils.getContext(),disPicActivity.class);
						//需要开一个栈，才能正常启动
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
						intent.putExtra("list",screen);
						intent.putExtra("index", index);
						//需要添加一个上下文环境
						UIUtils.getContext().startActivity(intent);
					}
				});
			}else{
				//数据不存在的，则隐藏
				ivPics[i].setVisibility(View.GONE);
			}
		}
	}

}

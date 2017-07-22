package com.itheima.googleplay74.ui.activity;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.protocol.HomeDetailProtocol;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.ui.holder.DetailAppInfoHolder;
import com.itheima.googleplay74.ui.holder.DetailDesHolder;
import com.itheima.googleplay74.ui.holder.DetailDownloadHolder;
import com.itheima.googleplay74.ui.holder.DetailPicsHolder;
import com.itheima.googleplay74.ui.holder.DetailSafeHolder;
import com.itheima.googleplay74.ui.view.LoadingPage;
import com.itheima.googleplay74.ui.view.LoadingPage.ResultState;
import com.itheima.googleplay74.utils.UIUtils;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class HomeDetailActivity extends BaseActivity {
	private LoadingPage mLoadingPage;
	private String packageNames;
	private appInfo data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//创建loadingpager
		mLoadingPage = new LoadingPage(UIUtils.getContext()){
			@Override
			public View onCreateSuccessView() {
				//调用方法 ,更新页面
				return HomeDetailActivity.this.onCreateSuccessView();
			}
			@Override
			public ResultState onLoad() {
				//调用方法,加载数据
				return HomeDetailActivity.this.onLoad();
			}
		};
		//加载布局
		setContentView(mLoadingPage);
		packageNames = getIntent().getStringExtra("packagename");
		
		// 开始加载网络数据
		mLoadingPage.loadData();
		
		initActionbar();
	}
	
	// 初始化actionbar
		private void initActionbar() {
			ActionBar actionbar = getSupportActionBar();
			// actionbar.setHomeButtonEnabled(true);// home处可以点击
			actionbar.setDisplayHomeAsUpEnabled(true);// 显示左上角返回键,当和侧边栏结合时展示三个杠图片
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			default:
				break;
			}
			return super.onOptionsItemSelected(item);
		}
	
		//加载成功的布局, 
		public  View onCreateSuccessView(){
			//初始化布局
			View view = UIUtils.inflate(R.layout.page_home_detail);
			//初始化应用信息模块
			FrameLayout flDetailAppIndo = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
			DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
			flDetailAppIndo.addView(appInfoHolder.getRootView());
			//初始化安全描述模块
			FrameLayout flDetailAppSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
			DetailSafeHolder safeHolder = new DetailSafeHolder();
			flDetailAppSafe.addView(safeHolder.getRootView());
			//初始化截图模块
			HorizontalScrollView hsvPic = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pic);
			DetailPicsHolder detailPicsHolder = new DetailPicsHolder();
			hsvPic.addView(detailPicsHolder.getRootView());
			//初始化描述模块
			FrameLayout flDetailDes = (FrameLayout) view.findViewById(R.id.fl_detail_des);
			DetailDesHolder desHolder = new DetailDesHolder();
			flDetailDes.addView(desHolder.getRootView());
			//初始化下载模块布局
			FrameLayout download = (FrameLayout) view.findViewById(R.id.fl_detail_download);
			DetailDownloadHolder detailDownLoad = new DetailDownloadHolder();
			download.addView(detailDownLoad.getRootView());
			//设置数据更新页面
			appInfoHolder.SetData(data);
			safeHolder.SetData(data);
			detailPicsHolder.SetData(data);
			desHolder.SetData(data);
			detailDownLoad.SetData(data);
			
			return view;
			
		}

		//加载网络数据,
		public  ResultState onLoad(){
			//传入包名为了，连接url
			HomeDetailProtocol protocol = new HomeDetailProtocol(packageNames);
			data = protocol.getData(0);
			if(data==null){
				return ResultState.STATE_ERROR;
			}else{
				return ResultState.STATE_SUCCESS;
			}
		}
	
}

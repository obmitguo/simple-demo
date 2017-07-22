package com.itheima.googleplay74.ui.holder;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.doman.DownloadInfo;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.manger.DownloadManager;
import com.itheima.googleplay74.manger.DownloadManager.DownloadObserver;
import com.itheima.googleplay74.ui.view.ProgressArc;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class HomeHolder extends BaseHolder<appInfo> implements
		DownloadObserver, OnClickListener {

	private ImageView iv_cone;
	private TextView tv_name;
	private TextView tv_memor;
	private TextView tv_des;
	private BitmapUtils bitmapUtils;
	private RatingBar rb_pro;
	private TextView tvDownload;

	private ProgressArc pbProgress;
	private DownloadManager mDM;
	
	private int mCurrentState;
	private float mProgress;
	@Override
	public View initView() {
		// 1.加载布局
		View view = UIUtils.inflate(R.layout.list_item_home);
		/*
		 * //2.初始化控件 tvContent = (TextView) view.findViewById(R.id.tv_data);
		 */
		iv_cone = (ImageView) view.findViewById(R.id.iv_icon);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_memor = (TextView) view.findViewById(R.id.tv_memor);
		tv_des = (TextView) view.findViewById(R.id.tv_des);
		// 使用bitmapUtils,单例模式
		bitmapUtils = BitmapHelper.getBitmapUtils();
		rb_pro = (RatingBar) view.findViewById(R.id.rb_pro);

		tvDownload = (TextView) view.findViewById(R.id.tv_download);
		// 初始化进度条
		// 初始化进度条
		FrameLayout flProgress = (FrameLayout) view
				.findViewById(R.id.fl_progress);
		
		flProgress.setOnClickListener(this);

		pbProgress = new ProgressArc(UIUtils.getContext());
		// 设置圆形进度条直径
		pbProgress.setArcDiameter(UIUtils.dip2px(26));
		// 设置进度条颜色
		pbProgress.setProgressColor(UIUtils.getColor(R.color.progress));
		// 设置进度条宽高布局参数
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				UIUtils.dip2px(27), UIUtils.dip2px(27));
		flProgress.addView(pbProgress, params);

		// pbProgress.setOnClickListener(this);

		mDM = DownloadManager.getInstance();
		mDM.registerObserver(this);// 注册观察者, 监听状态和进度变化

		return view;
	}

	@Override
	public void refreshView(appInfo data) {
		// 设置数据
		bitmapUtils.display(iv_cone, HttpHelper.URL + "image?name="
				+ data.iconUrl);
		tv_des.setText(data.des);
		tv_memor.setText(Formatter.formatFileSize(UIUtils.getContext(),
				data.size));
		tv_name.setText(data.name);
		rb_pro.setRating(data.stars);
		
		// 判断当前应用是否下载过
				DownloadInfo downloadInfo = mDM.getDownloadInfo(data);
				if (downloadInfo != null) {
					// 之前下载过
					mCurrentState = downloadInfo.currentState;
					mProgress = downloadInfo.getProgress();
				} else {
					// 没有下载过
					mCurrentState = DownloadManager.STATE_UNDO;
					mProgress = 0;
				}

				refreshUI(mCurrentState, mProgress, data.id);
	}

	private void refreshUI(int state, float progress, String id) {
		// 由于listview重用机制, 要确保刷新之前, 确实是同一个应用
				if (!getData().id.equals(id)) {
					return;
				}

				mCurrentState = state;
				mProgress = progress;
				switch (state) {
				case DownloadManager.STATE_UNDO:
					// 自定义进度条背景
					pbProgress.setBackgroundResource(R.drawable.ic_download);
					// 没有进度
					pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
					tvDownload.setText("下载");
					break;
				case DownloadManager.STATE_WAITING:
					pbProgress.setBackgroundResource(R.drawable.ic_download);
					// 等待模式
					pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_WAITING);
					tvDownload.setText("等待");
					break;
				case DownloadManager.STATE_DOWNLOADING:
					pbProgress.setBackgroundResource(R.drawable.ic_pause);
					// 下载中模式
					pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_DOWNLOADING);
					pbProgress.setProgress(progress, true);
					tvDownload.setText((int) (progress * 100) + "%");
					break;
				case DownloadManager.STATE_PAUSE:
					pbProgress.setBackgroundResource(R.drawable.ic_resume);
					pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
					break;
				case DownloadManager.STATE_ERROR:
					pbProgress.setBackgroundResource(R.drawable.ic_redownload);
					pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
					tvDownload.setText("下载失败");
					break;
				case DownloadManager.STATE_SUCCESS:
					pbProgress.setBackgroundResource(R.drawable.ic_install);
					pbProgress.setStyle(ProgressArc.PROGRESS_STYLE_NO_PROGRESS);
					tvDownload.setText("安装");
					break;

				default:
					break;
				}
	}
	
	// 主线程更新ui 3-4
		private void refreshUIOnMainThread(final DownloadInfo info) {
			// 判断下载对象是否是当前应用
			appInfo appInfo = getData();
			if (appInfo.id.equals(info.id)) {
				UIUtils.runOnUIThread(new Runnable() {

					public void run() {
						refreshUI(info.currentState, info.getProgress(), info.id);
					}
				});
			}
		}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fl_progress:
			// 根据当前状态来决定下一步操作
			if (mCurrentState == DownloadManager.STATE_UNDO
					|| mCurrentState == DownloadManager.STATE_ERROR
					|| mCurrentState == DownloadManager.STATE_PAUSE) {
				mDM.download(getData());// 开始下载
			} else if (mCurrentState == DownloadManager.STATE_DOWNLOADING
					|| mCurrentState == DownloadManager.STATE_WAITING) {
				mDM.pause(getData());// 暂停下载
			} else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
				mDM.install(getData());// 开始安装
			}

			break;

		default:
			break;
		}
	}

	public void onDownloadStateChanged(DownloadInfo info) {
		refreshUIOnMainThread(info);
	}

	public void onDownloadProgressChanged(DownloadInfo info) {
		refreshUIOnMainThread(info);
	}

}

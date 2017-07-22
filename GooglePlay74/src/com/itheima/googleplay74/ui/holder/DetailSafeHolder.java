package com.itheima.googleplay74.ui.holder;

import java.util.ArrayList;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.doman.appInfo.SafeInfo;
import com.itheima.googleplay74.utils.BitmapHelper;
import com.itheima.googleplay74.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailSafeHolder extends BaseHolder<appInfo> {
	
	private ImageView[] mSafeIcons;// 安全标识图片
	private ImageView[] mDesIcons;// 安全描述图片
	private TextView[] mSafeDes;// 安全描述文字
	private LinearLayout[] mSafeDesBar;// 安全描述条目(图片+文字)
	private BitmapUtils mBitmapUtils;
	private RelativeLayout rlDesRoot;
	private LinearLayout llDesRoot;
	private ImageView ivArrow;
	private int mDesHeight;
	private LinearLayout.LayoutParams mParams;
	
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_safeinfo);
		mSafeIcons = new ImageView[4];
		mSafeIcons[0] = (ImageView) view.findViewById(R.id.iv_safe1);
		mSafeIcons[1] = (ImageView) view.findViewById(R.id.iv_safe2);
		mSafeIcons[2] = (ImageView) view.findViewById(R.id.iv_safe3);
		mSafeIcons[3] = (ImageView) view.findViewById(R.id.iv_safe4);

		mDesIcons = new ImageView[4];
		mDesIcons[0] = (ImageView) view.findViewById(R.id.iv_des1);
		mDesIcons[1] = (ImageView) view.findViewById(R.id.iv_des2);
		mDesIcons[2] = (ImageView) view.findViewById(R.id.iv_des3);
		mDesIcons[3] = (ImageView) view.findViewById(R.id.iv_des4);

		mSafeDes = new TextView[4];
		mSafeDes[0] = (TextView) view.findViewById(R.id.tv_des1);
		mSafeDes[1] = (TextView) view.findViewById(R.id.tv_des2);
		mSafeDes[2] = (TextView) view.findViewById(R.id.tv_des3);
		mSafeDes[3] = (TextView) view.findViewById(R.id.tv_des4);

		mSafeDesBar = new LinearLayout[4];
		mSafeDesBar[0] = (LinearLayout) view.findViewById(R.id.ll_des1);
		mSafeDesBar[1] = (LinearLayout) view.findViewById(R.id.ll_des2);
		mSafeDesBar[2] = (LinearLayout) view.findViewById(R.id.ll_des3);
		mSafeDesBar[3] = (LinearLayout) view.findViewById(R.id.ll_des4);
		rlDesRoot = (RelativeLayout) view.findViewById(R.id.rl_des_root);
		//找到布局，并设置点击时间
		rlDesRoot.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggle();
				Toast.makeText(UIUtils.getContext(), "!!!!!!!!!!!!!", 0).show();
			}
		});
		//得到bitmap对象
		mBitmapUtils=BitmapHelper.getBitmapUtils();
		//得到需要隐藏的部分
		llDesRoot = (LinearLayout) view.findViewById(R.id.ll_des_root);
		ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
		
				/*//获取安全扫描的完整高度
				llDesRoot.measure(0 , 0);
				mDesHeight = llDesRoot.getMeasuredHeight();
				
				System.out.println("安全描述高度"+mDesHeight);
				//修改安全描述的高度达到隐藏的效果
				mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
				mParams.height=0;
				llDesRoot.setLayoutParams(mParams);*/
		
		return view;
	}
	private boolean isOpen =false;// 标记安全描述开关状态,默认关
	//打开或者关闭安全描述信息
	//导入jar包 ningoldAndroids-2.4.0.jar
	private void toggle(){
		ValueAnimator animator=null;
		if(isOpen){
			//关闭
			isOpen=false;
			animator = ValueAnimator.ofInt(mDesHeight,0);//属性动画
		}else{
			//开启
			isOpen=true;
			//属性动画
			animator = ValueAnimator.ofInt(0,mDesHeight);
		}
				// 动画更新的监听
				animator.addUpdateListener(new AnimatorUpdateListener() {

					// 启动动画之后, 会不断回调此方法来获取最新的值
					public void onAnimationUpdate(ValueAnimator animator) {
						// 获取最新的高度值
						Integer height = (Integer) animator.getAnimatedValue();
						System.out.println("最新高度:" + height);

						//重新修改布局高度
						mParams.height = height;
						llDesRoot.setLayoutParams(mParams);
					}
				});
				//添加动画监听
				animator.addListener(new AnimatorListener() {
					
					public void onAnimationStart(Animator arg0) {
						// TODO Auto-generated method stub
						
					}
					
					public void onAnimationRepeat(Animator arg0) {
						// TODO Auto-generated method stub
						
					}
					//动画结束
					public void onAnimationEnd(Animator arg0) {
						if(isOpen){
							ivArrow.setImageResource(R.drawable.arrow_up);
						}else{
							ivArrow.setImageResource(R.drawable.arrow_down);
						}
					}
					
					public void onAnimationCancel(Animator arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				//不要忘了开启动画
				animator.setDuration(200);// 动画时间
				animator.start();// 启动动画
	}
	@Override
	public void refreshView(appInfo data) {
		ArrayList<SafeInfo> safe = data.safe;
		for(int i=0;i<4;i++){
			if(i<safe.size()){
				//安全标识图片
				SafeInfo safeInfo = safe.get(i);
				mBitmapUtils.display(mSafeIcons[i], HttpHelper.URL+"image?name="+safeInfo.safeUrl);
				//安全描述文字
				mSafeDes[i].setText(safeInfo.safeDes);
				//安全描述图片
				mBitmapUtils.display(mDesIcons[i], HttpHelper.URL+"image?name="+safeInfo.safeDesUrl);
			}else{
				//剩下不应该显示的图片
				mSafeIcons[i].setVisibility(View.GONE);
				//隐藏多余的描述条目
				mSafeDesBar[i].setVisibility(View.GONE);
			}
		}
		//获取安全扫描的完整高度
		llDesRoot.measure(0 , 0);
		mDesHeight = llDesRoot.getMeasuredHeight();
		
		System.out.println("安全描述高度"+mDesHeight);
		//修改安全描述的高度达到隐藏的效果
		mParams = (LinearLayout.LayoutParams) llDesRoot.getLayoutParams();
		mParams.height=0;
		llDesRoot.setLayoutParams(mParams);
		
	}

}

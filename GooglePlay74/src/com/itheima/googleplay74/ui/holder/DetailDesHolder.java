package com.itheima.googleplay74.ui.holder;

import com.itheima.googleplay74.R;
import com.itheima.googleplay74.doman.appInfo;
import com.itheima.googleplay74.utils.UIUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailDesHolder extends BaseHolder<appInfo> {
	
	private TextView tvDes;
	private TextView tvAuthor;
	private ImageView ivArrow;
	private RelativeLayout rlToggle;
	private LinearLayout.LayoutParams mParams;
	private boolean isOpen=false;
	@Override
	public View initView() {
		View view = UIUtils.inflate(R.layout.layout_detail_desinfo);
		tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
		tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
		ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
		rlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);
		rlToggle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				toggle();
			}
		});
		return view;
	}
	
	@Override
	public void refreshView(appInfo data) {
		tvDes.setText(data.des);
		tvAuthor.setText(data.author);
		
		//放在消息队列中，解决当前只有三行描述也是七行高度debug
		tvDes.post(new Runnable(){
			public void run() {
				//默认展示7行的高度
				int shortHeight = getShortHeight();
				mParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
				mParams.height=shortHeight;
				tvDes.setLayoutParams(mParams);
			}
		});
		
	}
	//开关函数
	public void toggle(){
		int longHeight = getLongHeight();
		int shortHeight = getShortHeight();
		ValueAnimator animator=null;
		if(isOpen){
			//关闭
			isOpen=false;
			if(longHeight>shortHeight){//只有描述信息大于7行才启动动画
				animator= ValueAnimator.ofInt(longHeight+20,shortHeight);
			}
		 }else{
			 isOpen=true;
				if(longHeight>shortHeight){//只有描述信息大于7行才启动动画
					animator= ValueAnimator.ofInt(shortHeight,longHeight+20);
				}
		}
		if(animator!=null){//只有描述信息大于7行才启动动画
			animator.addUpdateListener(new AnimatorUpdateListener() {
				public void onAnimationUpdate(ValueAnimator arg0) {
					Integer height = (Integer) arg0.getAnimatedValue();
					mParams.height=height;
					tvDes.setLayoutParams(mParams);
				}
			});
			animator.addListener(new AnimatorListener() {
				public void onAnimationStart(Animator arg0) {
					
				}
				public void onAnimationRepeat(Animator arg0) {
					
				}
				public void onAnimationEnd(Animator arg0) {
					// ScrollView要滑动到最底部
					final ScrollView scrollView = getScrollView();

					// 为了运行更加安全和稳定, 可以讲滑动到底部方法放在消息队列中执行
					scrollView.post(new Runnable() {

						public void run() {
							scrollView.fullScroll(ScrollView.FOCUS_DOWN);// 滚动到底部
						}
					});

					if (isOpen) {
						ivArrow.setImageResource(R.drawable.arrow_up);
					} else {
						ivArrow.setImageResource(R.drawable.arrow_down);
					}

				}
				public void onAnimationCancel(Animator arg0) {
					
				}
			});
			
			animator.setDuration(200);
			animator.start();
		}
		
	}
	
	//获取7行textView的高度,copy一个已经存在的，来计算7行高度
	private int getShortHeight(){
		//模拟一个textview，设置最大行数为7行，计算该虚拟textview的高度，从而知道tvDes在展示7行时应该多高
		int width = tvDes.getMeasuredWidth();//宽度
		//int Height = tvDes.getMeasuredHeight();
		TextView view = new TextView(UIUtils.getContext());
		view.setText(getData().des);//设置文字
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//文字大小设置一致
		view.setMaxLines(6);//最大行数为7
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);//宽度不变，确定值，match_parent
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000, MeasureSpec.AT_MOST);//高度包裹内容，wrap_content;当包裹内容时
																//参数1表示尺寸最大值，暂写2000，也可以时屏幕高度
		//开始测量
		view.measure(widthMeasureSpec, heightMeasureSpec);
		return view.getMeasuredHeight();//返回测量后的高度
	}
	
	/**
	 * 获取完整textview的高度
	 */
	private int getLongHeight() {
		// 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
		int width = tvDes.getMeasuredWidth();// 宽度
		//int Height = tvDes.getMeasuredHeight();//高度
		TextView view = new TextView(UIUtils.getContext());
		view.setText(getData().des);// 设置文字
		view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);// 文字大小一致
		// view.setMaxLines(7);// 最大行数为7行

		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000,
				MeasureSpec.AT_MOST);// 高度包裹内容, wrap_content;当包裹内容时,
										// 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

		// 开始测量
		view.measure(widthMeasureSpec, heightMeasureSpec);
		return view.getMeasuredHeight();// 返回测量后的高度
	}

	
	//获得srcollView，一层一层往上找
	//指导找到scrollView才返回，注意一顶要保证父控件有ScrollView，否则死循环
	private ScrollView getScrollView(){
		ViewParent parent = tvDes.getParent();
		while(!(parent instanceof ScrollView)){
			parent=parent.getParent();
		}
		return (ScrollView)parent;
	}
	
	
}

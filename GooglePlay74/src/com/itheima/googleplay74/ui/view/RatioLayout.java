package com.itheima.googleplay74.ui.view;

import com.itheima.googleplay74.R;

import android.R.attr;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 自定义控件，按照比例来决定布局的高度（将图片的大小设置的刚刚好）
 * @author admin
 *
 */
public class RatioLayout extends FrameLayout {

	private float ratio;

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//获取属性值
		//attrs.getAttributeFloatValue("", "ratio",-1);  //简便方法
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Ratiolayout);
		//id=属性名_具体属性字段名称(此id系统自动生成)
		ratio = typedArray.getFloat(R.styleable.Ratiolayout_ratio, -1);
		typedArray.recycle();//回收typedArray,提高性能
		System.out.println("ratio:"+ratio);
	}

	public RatioLayout(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 1. 获取宽度
		// 2. 根据宽度和比例ratio, 计算控件的高度
		// 3. 重新测量控件
		
		//1000000000000000000000111001110
		System.out.println("widthMeasureSpec:" + widthMeasureSpec);

		// MeasureSpec.AT_MOST; 至多模式, 控件有多大显示多大, wrap_content
		// MeasureSpec.EXACTLY; 确定模式, 类似宽高写死成dip, match_parent
		// MeasureSpec.UNSPECIFIED; 未指定模式.

		int width = MeasureSpec.getSize(widthMeasureSpec);// 获取宽度值
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 获取宽度模式
		int height = MeasureSpec.getSize(heightMeasureSpec);// 获取高度值
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 获取高度模式

		// 宽度确定, 高度不确定, ratio合法, 才计算高度值
		if (widthMode == MeasureSpec.EXACTLY
				&& heightMode != MeasureSpec.EXACTLY && ratio > 0) {
			// 图片宽度 = 控件宽度 - 左侧内边距 - 右侧内边距
			int imageWidth = width - getPaddingLeft() - getPaddingRight();

			// 图片高度 = 图片宽度/宽高比例
			int imageHeight = (int) (imageWidth / ratio + 0.5f);

			// 控件高度 = 图片高度 + 上侧内边距 + 下侧内边距
			height = imageHeight + getPaddingTop() + getPaddingBottom();

			// 根据最新的高度来重新生成heightMeasureSpec(高度模式是确定模式)
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
					MeasureSpec.EXACTLY);
		}

		// 按照最新的高度测量控件
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
}

package com.itheima.googleplay74.ui.holder;

import android.view.View;

/**
 * @author admin
 * 设置一个父类，让其他的都实现这个
 */
public abstract class BaseHolder<T>{
	private View mRootView;//一个item的根布局
	private T data;
	//当new这个对象时，就会加载布局，初始化控件，设置tag
	public BaseHolder(){
		mRootView=initView();
		//3.打一个标记TAg
		mRootView.setTag(this);
	}
	
	//1.加载布局文件
	//2.初始化控件， findViewById
	public abstract View initView();
	
	//返回item的布局对象
	public View getRootView(){
		return mRootView;
	}
	//设置当前的item的数据
	public void SetData(T data){
		this.data=data;
		refreshView(data);
	}
	
	//获得当前的item的数据
	public T getData(){
		return data;
	}
	
	//4.根据数据来刷新界面
	public abstract void refreshView(T data);
	
	}

package com.itheima.googleplay74.ui.adapter;

import java.util.ArrayList;

import com.itheima.googleplay74.manger.ThreadManager;
import com.itheima.googleplay74.ui.holder.BaseHolder;
import com.itheima.googleplay74.ui.holder.MoreHolder;
import com.itheima.googleplay74.utils.UIUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;



/**
 * @author admin
 * 
 */
public abstract class myBaseAdapter<T> extends BaseAdapter{
	
	public static final int TYPE_NORMAl=1;
	public static final int TYPE_MORE=0;
	
	ArrayList<T> data;
	public myBaseAdapter(ArrayList<T> data){
		this.data=data;
	}
	public int getCount() {
		return data.size()+1;//多加了一个下拉加载更多，所以要加“1”
	}
	//返回布局类型个数
	@Override
	public int getViewTypeCount(){
		
		return 2;
	}
	//返回当前位置应展示那种布局类型
	@Override
	public int getItemViewType(int position){
		if(position==getCount()-1){
			return TYPE_MORE;
		}else{
			return getInnerType(position);//让一个类来返回正常的布局，在子类中可以覆写，增强拓展性
		}
	}
	//getInnerType*******
	public int getInnerType(int position) {
		return TYPE_NORMAl;//默认是普通类型
	}
	
	public T getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder;
		if(convertView==null){
			//1.加载布局文件
			//2.初始化findviewByid
			//3.打一个标记tag
			
			//判断是否是加载更多的类型
			if(getItemViewType(position)==TYPE_MORE){
				//加载更多的布局
				holder=(BaseHolder) new MoreHolder(hasMore()); //不懂可见父类的调用.
			}else{
				holder=getHolder(position);//子类返回具体值
			}
		}else{
			holder= (BaseHolder)convertView.getTag();
		}
		
		//4.根据数据来刷新页面
		if(getItemViewType(position)!=TYPE_MORE){
			holder.SetData(getItem(position));
		}else{
			//加载更多的布局，在上面直接完成了，不用通过抽象类
			//一旦加载更多布局展示出来，就开始加载更多
			 MoreHolder morehoder=(MoreHolder)holder;
			//只有在有更多数据的时候才加载
			 if(morehoder.getData()==morehoder.STATE_MORE_MORE){
				 loadMore(morehoder);
			 }
			 
		}
		return holder.getRootView();//返回数据
	}
	
	public boolean hasMore(){
		return true;       //此处写方法返回true或者false为了让子类更加灵活
	}
	//返回当前页面的holder对象，必须子类实现
	public abstract  BaseHolder<T> getHolder(int position);
	
	
	private boolean isLoadMore=false;
	//加载更多数据
	public void loadMore(final MoreHolder holder){
		if(!isLoadMore){
			isLoadMore=true;
			/*new Thread(){
				@Override
				public void run() {
					final ArrayList<T> moreData=onLoadMore();
					
					UIUtils.runOnUIThread(new Runnable(){
						
						public void run() {
							if(moreData!=null){
								//每页有20条数据，如果返回的数据小于20条，就认为到了最后一页
								if(moreData.size()<20){
									holder.SetData(MoreHolder.STATE_MORE_NONE);
									Toast.makeText(UIUtils.getContext(), "没有更多数据", 0).show();
								}else{
									//还有更多数据
									holder.SetData(MoreHolder.STATE_MORE_MORE);
								}
								//将更多数据加载到当前集合中
								data.addAll(moreData);
								//刷新界面,更新adapter
								myBaseAdapter.this.notifyDataSetChanged();
							}else{
								//加载更多失败
								holder.SetData(MoreHolder.STATE_MORE_ERROR);
							}
							isLoadMore=false;
						}
					});
					super.run();
				}
			}.start();*/
			
			ThreadManager.getThreadPool().execute(new Runnable(){
				
				public void run() {
					final ArrayList<T> moreData=onLoadMore();
					
					UIUtils.runOnUIThread(new Runnable(){
						
						public void run(){
							if(moreData!=null){
								//每页有20条数据，如果返回的数据小于20条，就认为到了最后一页
								if(moreData.size()<20){
									holder.SetData(MoreHolder.STATE_MORE_NONE);
									Toast.makeText(UIUtils.getContext(), "没有更多数据", 0).show();
								}else{
									//还有更多数据
									holder.SetData(MoreHolder.STATE_MORE_MORE);
								}
								//将更多数据加载到当前集合中
								data.addAll(moreData);
								//刷新界面,更新adapter
								myBaseAdapter.this.notifyDataSetChanged();
							}else{
								//加载更多失败
								holder.SetData(MoreHolder.STATE_MORE_ERROR);
							}
							isLoadMore=false;
						}
					});
					//super.run();
				}
			});
		}
	}
	//加载更多数据，必须要由子类实现
	public abstract ArrayList<T> onLoadMore();
	
	//获取当前集合的大小
	public int getListSize(){
		return data.size();
	}
}

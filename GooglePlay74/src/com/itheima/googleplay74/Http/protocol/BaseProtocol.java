package com.itheima.googleplay74.Http.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import android.database.CursorJoiner.Result;

import com.itheima.googleplay74.Http.HttpHelper;
import com.itheima.googleplay74.Http.HttpHelper.HttpResult;
import com.itheima.googleplay74.utils.IOUtils;
import com.itheima.googleplay74.utils.StringUtils;
import com.itheima.googleplay74.utils.UIUtils;

public abstract class BaseProtocol<T> {
	public T getData(int index){
		//先判断是否有缓存，有的话就加载数据
		String result=getCache(index);
		if(StringUtils.isEmpty(result)){//没有缓存，请求网络加载
			
			result=getDataFromServer(index);
		}
		//开始解析
		if(result!=null){
			T data = parseData(result);
			return data;
		}
		return null;
	}
	
	//从网络获取数据
	//index表示的是从哪个位置开始返回20条数据，用于分页
	private String getDataFromServer(int index) {  //index用于分页的标志
		//例子 http://www.itheima.com/home?name=zhangsan&age=18
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL+getKey()+"?index="+index+getParams());
		
		if(httpResult!=null){
			String result = httpResult.getString();
			System.out.println("访问结果："+result);
			
			//请求成功写入缓存
			if(!StringUtils.isEmpty(result)){
				setCache(index, result);
			}
			return result;
		}
		return null;
	}
	
	//获取网络连接关键词，子类必须实现
	public abstract String getKey();
	
	//获取网络连接参数，子类必须实现
	public abstract String getParams();
	
	//写缓存
	//以url为key，以json为value
	public void setCache(int index,String json){
		//以url为文件名，以json为文件内容，保存到本地
		File cacheDir = UIUtils.getContext().getCacheDir();
		//生成缓存文件
		File cacheFile = new File(cacheDir,getKey()+"?index"+index+getParams());//不理解
		FileWriter writer=null;
		try {
			writer =new FileWriter(cacheFile);
			//缓存失效的截至时间
			long deadline=System.currentTimeMillis()+30*60*1000;//半个小时的有效期
			writer.write(deadline+"\n");//在第一行写入缓存时间，换行
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.close(writer);
		}
	}
	
	//读缓存
	public String getCache(int index){
		//以url为文件名，以json为文件内容，保存到本地
				File cacheDir = UIUtils.getContext().getCacheDir();
				//生成缓存文件
				File cacheFile = new File(cacheDir,getKey()+"?index"+index+getParams());//不理解
				//判断缓存是否存在
			if(cacheFile.exists()){
				//判断缓存是否有效
				BufferedReader reader=null;
				try {
					reader=new BufferedReader(new FileReader(cacheFile));
					String deadLine=reader.readLine();//读取第一行的有效期
					
					long parsetime = Long.parseLong(deadLine);//转化成long类型
					if(System.currentTimeMillis()<parsetime){ //当前时间小于截至时间
								                             //说明缓存有效
						//缓存有效
						StringBuffer sb = new StringBuffer();
						String line;
						while((line=reader.readLine())!=null){  //line中不会包含有效期的
							sb.append(line);//链接字符串
						}
						return sb.toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					//将流关闭
					IOUtils.close(reader);
				}
			}
			return null;	
	}
	
	public abstract T parseData(String result);
		
}

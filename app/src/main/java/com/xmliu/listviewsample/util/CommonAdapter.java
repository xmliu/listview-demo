package com.xmliu.listviewsample.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 代码来自张鸿洋的慕课网视频，特此声明
 * Created by Admin on 2015/11/11.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int mLayoutId;

    public CommonAdapter(Context context,List<T> datas,int layoutId){
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = ViewHolder.get(mContext,convertView,parent,mLayoutId,position);
        convert(holder,getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder,T t);

}

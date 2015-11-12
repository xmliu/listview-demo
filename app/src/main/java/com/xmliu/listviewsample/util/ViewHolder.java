package com.xmliu.listviewsample.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * 代码来自张鸿洋的慕课网视频，特此声明
 * Created by Admin on 2015/11/11.
 */
public class ViewHolder {

    private SparseArray<View> mViews; // 一个比map更高效的键值对，存放view
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context,ViewGroup parent,int layoutId,int position){

        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);

    }

    public static ViewHolder get(Context context,View convertView,ViewGroup parent,int layoutId,int position)
    {
        if(convertView == null){

            return new ViewHolder(context,parent,layoutId,position);
        }else{
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);

        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }

        return (T) view;
    }

    public View getConvertView(){
        return mConvertView;
    }

    // 链式编程
    public ViewHolder setText(int viewId,String text){

        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}

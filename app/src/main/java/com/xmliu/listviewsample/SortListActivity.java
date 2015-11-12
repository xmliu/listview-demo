package com.xmliu.listviewsample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobeta.android.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/11/5 19:36
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class SortListActivity extends Activity{


    private DragSortListView mListView;
    private FunctionListAdapter adapter;
    private List<FunctionData> mListData = new ArrayList<>();
    private List<FunctionData> mNewList = new ArrayList<>();

    private Button mBottomBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_list);

        mListView = (DragSortListView) findViewById(R.id.dslvList);
        mBottomBtn = (Button) findViewById(R.id.model_bottom_btn);

        mListData.clear();
        for (int i = 0; i < 10; i++) {
            FunctionData object = new FunctionData();
            object.name = "我是第" + i + "个";
            object.isSelected = 1;
            mListData.add(object);
        }
        mNewList.clear();
        mNewList.addAll(mListData);

        adapter = new FunctionListAdapter(mListData, SortListActivity.this);
        adapter.setNewList(mNewList);
        mListView.setAdapter(adapter);
        mListView.setDragEnabled(true); // 设置是否可拖动。

        mBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Toast.makeText(SortListActivity.this,
                        mListData.size() + "new list==>" + gson.toJson(adapter.getNewList()) + adapter.getNewList().size(), Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setDropListener(new DragSortListView.DropListener() {

            @Override
            public void drop(int from, int to) {
                if (from != to) {
                    FunctionData item = (FunctionData) adapter.getItem(from);
                    adapter.remove(from);
                    adapter.insert(item, to);
                }
            }
        });
    }

    class FunctionData {
        public String remove;
        public String name;
        public int isSelected;
    }


    class FunctionListAdapter extends BaseAdapter {

        List<FunctionData> list;
        List<FunctionData> newList;
        Context context;


        public FunctionListAdapter(List<FunctionData> list, Context context) {
            super();
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int pos) {
            list.remove(pos);
        }

        public void insert(FunctionData item, int to) {
            list.add(to, item);
            newList.clear();
            for(int i = 0;i <list.size();i++){
                if(list.get(i).isSelected == 1)
                    newList.add(list.get(i));
            }
            notifyDataSetChanged();
        }

        public void setNewList(List<FunctionData> newListData) {
            newList = newListData;
        }

        public List<FunctionData> getNewList() {
            return newList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DragHolder holder;
            final int pos = position;
            if (convertView == null) {
                holder = new DragHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.drag_list_item, parent, false);
                holder.mCheckedIV = (ImageView) convertView.findViewById(R.id.drag_checkbox_iv);
                holder.nameTV = (TextView) convertView.findViewById(R.id.tvTitle);
                convertView.setTag(holder);
            } else {
                holder = (DragHolder) convertView.getTag();
            }
            holder.nameTV.setText(list.get(position).name);

            holder.mCheckedIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (list.get(pos).isSelected == 1) {
                        list.get(pos).isSelected = 0;
                        newList.remove(list.get(pos));

                    } else {

                        list.get(pos).isSelected = 1;
                        // 获取正确的选择后的List
                        newList.clear();
                        for(int i = 0;i <list.size();i++){
                            if(list.get(i).isSelected == 1)
                                newList.add(list.get(i));
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            if (list.get(pos).isSelected == 1) {
                holder.mCheckedIV.setImageResource(R.mipmap.model_function_checked);

            } else {
                holder.mCheckedIV.setImageResource(R.mipmap.model_function_unchecked);

            }

            holder.nameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(SortListActivity.this,
                            "点击名称", Toast.LENGTH_SHORT).show();
                }

            });
            return convertView;
        }

        public class DragHolder {

            TextView nameTV;
            ImageView mCheckedIV;

        }


    }

}

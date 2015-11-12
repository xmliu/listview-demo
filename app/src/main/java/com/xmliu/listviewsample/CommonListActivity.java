package com.xmliu.listviewsample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.xmliu.listviewsample.util.CommonAdapter;
import com.xmliu.listviewsample.util.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2015/11/12 11:09
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class CommonListActivity extends Activity{

    private ListView mListView;
    private MyAdapter myAdapter;
    private List<MyData> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);

        mListView = (ListView) findViewById(R.id.id_listview);

        for(int i = 0;i < 5 ;i ++){
            MyData data = new MyData();
            data.id = i;
            data.name = "15"+ i + " 5048 1234";
            listData.add(data);
        }
        myAdapter = new MyAdapter(CommonListActivity.this,listData);
        mListView.setAdapter(myAdapter);

    }

    private class MyAdapter extends CommonAdapter<MyData> {


        public MyAdapter(Context context, List<MyData> datas) {
            super(context, datas, R.layout.listview_item);

        }

        @Override
        public void convert(ViewHolder holder, MyData myData) {
            holder.setText(R.id.listview_name,myData.name);
        }
    }

    private class MyData {

        String name;
        int id;
    }
}

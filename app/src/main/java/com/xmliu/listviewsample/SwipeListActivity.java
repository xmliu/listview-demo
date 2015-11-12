package com.xmliu.listviewsample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.xmliu.listviewsample.refreshlib.library.PullToRefreshBase;
import com.xmliu.listviewsample.refreshlib.library.PullToRefreshScrollView;
import com.xmliu.listviewsample.refreshlib.library.extras.AbScrollView;
import com.xmliu.listviewsample.view.AbListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diyangxia on 2015/10/10.
 */
public class SwipeListActivity extends Activity {

    private AbListView mListView;
    private PullToRefreshScrollView mPullToRefesh;
    private MySwipeAdapter mySwipeAdapter;
    private int start = 0; // 当前页数
    private int limit = 8; // 为每页显示数据数目
    private int totalCount = 50;
    private PullToRefreshBase.Mode currentMode;
    private List<String> listData = new ArrayList<>();
    private List<String> listDataMore = new ArrayList<>();
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mySwipeAdapter = new MySwipeAdapter(listData);
                mListView.setAdapter(mySwipeAdapter);
            }
        };

        mListView = (AbListView) findViewById(R.id.listview1);
        mPullToRefesh = (PullToRefreshScrollView) findViewById(R.id.pulltorefreshscrollview);

        listData.clear();
        for (int i = 0; i < 10; i++) {
            listData.add("数据行" + i);
        }
        mHandler.sendEmptyMessage(0);

        mPullToRefesh
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<AbScrollView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<AbScrollView> refreshView) {
                        String label = DateUtils.formatDateTime(
                                SwipeListActivity.this,
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME
                                        | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);

                        mPullToRefesh.getLoadingLayoutProxy()
                                .setLastUpdatedLabel("最近更新: " + label);
                        new LoadMoreDataTask().execute();

                    }

                });

    }


    // 分页加载
    private class LoadMoreDataTask extends AsyncTask<Integer, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            currentMode = mPullToRefesh.getCurrentMode();
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            // TODO Auto-generated method stub

            if (currentMode == PullToRefreshBase.Mode.PULL_FROM_START) {
                start = 0;
                limit = 8;
                try {
                    Thread.sleep(2000);
                    listData.clear();
                    for (int i = 0; i < 10; i++) {
                        listData.add("数据行" + i);
                    }
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    Log.e("msg", "GetDataTask:" + e.getMessage());
                }

            } else if (currentMode == PullToRefreshBase.Mode.PULL_FROM_END) {
                calculate();
                listDataMore.clear();
                for (int i = start; i < start + limit; i++) {
                    listDataMore.add("数据行" + i);
                }
                listData.addAll(listDataMore);

            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            if (mPullToRefesh.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {

                mySwipeAdapter.notifyDataSetChanged();
                // mListView.invalidate();
                if (totalCount <= mySwipeAdapter.getCount()) {
                    if (mPullToRefesh.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {
                        Toast.makeText(SwipeListActivity.this,
                                "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                        mPullToRefesh.onRefreshComplete();
                    }
                    mPullToRefesh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                } else {
                    mPullToRefesh.setMode(PullToRefreshBase.Mode.BOTH);
                }
            }
            mPullToRefesh.onRefreshComplete();
        }

    }

    private void calculate() {
        int current = mySwipeAdapter.getCount();
        if (current + limit < totalCount) {
            start = current;
        } else {
            start = current;
            limit = totalCount - current;
        }
    }


    private class MySwipeAdapter extends BaseSwipeAdapter {

        List<String> listStr;

        public MySwipeAdapter(List<String> listStr) {
            this.listStr = listStr;
        }

        @Override
        public int getSwipeLayoutResourceId(int i) {
            return R.id.sample1;
        }

        @Override


        public View generateView(int i, ViewGroup viewGroup) {
            return LayoutInflater.from(SwipeListActivity.this).inflate(R.layout.swipe_list_item, viewGroup, false);
        }

        @Override
        public void fillValues(int i, View view) {
            final int pos = i;
            final SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.sample1);
            TextView textView = (TextView) view.findViewById(R.id.tvTitle);
            Button editBtn = (Button) view.findViewById(R.id.swipe_edit_btn);
            Button deleteBtn = (Button) view.findViewById(R.id.swipe_delete_btn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listStr.remove(pos);
                    notifyDataSetChanged();
                    swipeLayout.close();// 删除成功后需要关闭侧滑
                }
            });
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SwipeListActivity.this,
                            "编辑"+ pos, Toast.LENGTH_SHORT).show();
                }
            });
            textView.setText(listStr.get(i));
        }

        @Override
        public int getCount() {
            return listStr.size();
        }

        @Override
        public Object getItem(int position) {
            return listStr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}

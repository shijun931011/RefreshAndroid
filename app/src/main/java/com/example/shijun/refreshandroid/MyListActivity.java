package com.example.shijun.refreshandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.Arrays;
import java.util.LinkedList;

public class MyListActivity extends AppCompatActivity {

    private PullToRefreshListView mPullToRefreshListView;
    private LinkedList<String> mListItems;
    private ArrayAdapter<String> mAdapter;
    private String[] mStrings = {"苹果", "IT男", "Amy", "科比", "Mary",
            "小明", "Sunny", "耳机", "Maria", "苍老师", "Sarah", "奥迪",
            "Lily", "William", "聚合", "Paul", "安卓", "Peter",
            "深圳", "印象笔记", "Rachel", "Thomas", "科鲁兹", "Daniel", "上海",
            "Kevin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        ListView actualListView = mPullToRefreshListView.getRefreshableView();

        mListItems = new LinkedList<String>();
        mListItems.addAll(Arrays.asList(mStrings));

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
        actualListView.setAdapter(mAdapter);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mListItems.addFirst("Added after refresh...");
            mAdapter.notifyDataSetChanged();
            // Call onRefreshComplete when the list has been refreshed.
            mPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}

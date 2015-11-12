package com.xmliu.listviewsample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView swipeTV = (TextView) findViewById(R.id.swipeTV);
        TextView sortTV = (TextView) findViewById(R.id.sortTV);
        TextView commonTV = (TextView) findViewById(R.id.commonTV);
        swipeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SwipeListActivity.class));
            }
        });
        sortTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SortListActivity.class));
            }
        });
        commonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CommonListActivity.class));
            }
        });


    }
}

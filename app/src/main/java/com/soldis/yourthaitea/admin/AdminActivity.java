package com.soldis.yourthaitea.admin;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.admin.adapter.ViewPagerAdapterAdmin;


/**
 * Created by User on 8/15/2017.
 */

public class AdminActivity extends AppCompatActivity {
    ImageView imgMenu;
    CharSequence Titles[]={"Reset Device","Delete Visit", "Change Name"};
    int Numboftabs = 3;

    ViewPager pager;
    ViewPagerAdapterAdmin adapter;
    TabLayout tabs;

    RelativeLayout layout_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.green));
        }
        setContentView(R.layout.activity_admin);

        InitControl();
    }

    void InitControl(){
        layout_back = (RelativeLayout)findViewById(R.id.layout_back);
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        adapter =  new ViewPagerAdapterAdmin(getSupportFragmentManager(),Titles,Numboftabs);

        layout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Assigning ViewPager View and setting the adapter
        pager.setAdapter(adapter);

        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorBar));
        tabs.setupWithViewPager(pager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.clear();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

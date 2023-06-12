package com.soldis.yourthaitea.master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_PRODUCT_CATEGORY;
import com.soldis.yourthaitea.master.adapter.AdapterListProductCategory;
import com.soldis.yourthaitea.transaction.adapter.AdapterListKelurahan;

import java.util.ArrayList;

/**
 * Created by User on 8/22/2017.
 */

public class ListCategory extends AppCompatActivity {
    TextView txtLabel;
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_PRODUCT_CATEGORY productCategory;
    ArrayList<Obj_PRODUCT_CATEGORY> productCategories;

    RelativeLayout lyKembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list_product_category);

        InitControl();
        FillGrid();
    }

    void InitControl(){
        txtLabel = (TextView)findViewById(R.id.textLabel);

        lyKembali = (RelativeLayout)findViewById(R.id.layout_kembali);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridlayoutManager = new GridLayoutManager(this,2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        lyKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    void FillGrid(){
        productCategories = new ArrayList<>();
        productCategory = new Obj_PRODUCT_CATEGORY();
        productCategories = productCategory.getDataList();

        productCategory = new Obj_PRODUCT_CATEGORY();
        productCategory.setCATEGORY_ID("");
        productCategory.setCATEGORY_NAME( getResources().getString(R.string.daily_all));
        productCategories.add(productCategory);

        mAdapter = new AdapterListProductCategory(this, productCategories, new AdapterListProductCategory.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String CATEGORY_ID, String CATEGORY_NAME) {
                Intent mIntent = new Intent();
                mIntent.putExtra("CATEGORY_ID", CATEGORY_ID);
                mIntent.putExtra("CATEGORY_NAME", CATEGORY_NAME);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

}

package com.soldis.yourthaitea.dashboard.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.adapter.AdapterDashboard;
import com.soldis.yourthaitea.entity.Obj_MASTER;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ftctest on 11/09/2017.
 */

public class Frag_Dashboard_Stock extends Fragment {
    PieChart mChart;
    private long[] yValues ;

    private String[] xValues;
    // colors for different sections in pieChart
    public  int[] MY_COLORS ;
    int iEC;
    Toolbar toolbar;

    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridlayoutManager;

    Obj_MASTER objEmaster;
    ArrayList<Obj_MASTER> objEmasters;

    double dStockIn = 0;
    double dStockOut = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }


    private void InitControl(final View v) {
        MY_COLORS = new int[]{
                getActivity().getResources().getColor(R.color.red_deep),
                getActivity().getResources().getColor(R.color.green)

        };
        toolbar = (Toolbar) v.findViewById(R.id.tool_bar);
        mChart = (PieChart)v.findViewById(R.id.chart1);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mGridlayoutManager = new GridLayoutManager(v.getContext(),2);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        xValues = new String[]{getResources().getString(R.string.dash_stock_in),
                getResources().getString(R.string.dash_stock_out)
        };

        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");

        mChart.setRotationEnabled(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;
                if (xValues[e.getXIndex()].equals(getResources().getText(R.string.dash_stock_in))){
                    View rootView = getActivity().getWindow().getDecorView().getRootView();
                    Snackbar.make(rootView, getResources().getText(R.string.dash_stock_in) + " "
                            + AppController.getInstance().toCurrency(dStockIn) , Snackbar.LENGTH_LONG)
                            .setAction("Close", null).show();
                }else{
                    View rootView = getActivity().getWindow().getDecorView().getRootView();
                    Snackbar.make(rootView, getResources().getText(R.string.dash_stock_out) + " "
                            + AppController.getInstance().toCurrency(dStockOut) , Snackbar.LENGTH_LONG)
                            .setAction("Close", null).show();
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

        objEmaster = new Obj_MASTER();
        objEmaster = objEmaster.getDataStockTMP();

        if (objEmaster == null){
            yValues = new long[]{0, 0};
        }else{
            dStockIn = objEmaster.getSTOCK_MOTORIS();
            dStockOut = objEmaster.getQTY_PCS();
            yValues = new long[]{objEmaster.getSTOCK_MOTORIS(), objEmaster.getQTY_PCS()};
        }


        setDataForPieChart();

        FillGrid();
    }

    public void setDataForPieChart() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);

        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());

        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);


        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal if needed
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + ""; // e.g. append a dollar-sign
        }
    }

    void FillGrid(){
        objEmasters = new ArrayList<>();
        objEmaster = new Obj_MASTER();
        objEmasters = objEmaster.getDataListStockNew();

        mAdapter = new AdapterDashboard(getActivity(), objEmasters, new AdapterDashboard.OnDownloadClicked() {
            @Override
            public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}

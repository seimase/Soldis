package com.soldis.yourthaitea.transaction.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.transaction.CustcardActivity;
import com.soldis.yourthaitea.transaction.collection.CollectionActivity;
import com.soldis.yourthaitea.transaction.complaint.ComplainActivity;
import com.soldis.yourthaitea.transaction.maintenance.MaintenanceActivity;
import com.soldis.yourthaitea.transaction.taking_order.InputProductActivity;
import com.soldis.yourthaitea.transaction.taking_order.ListOrderActivity;

public class frag_transaction_menu extends Fragment {

    LinearLayout lyTO,
            lyCollection,
            lyComplaint,
            lyMaintenance;

    String CUSTNO, CUSTNAME, ADDRESS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction_menu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
    }

    private void InitControl(final View v) {
        lyTO = (LinearLayout)v.findViewById(R.id.layout_to);
        lyCollection = (LinearLayout)v.findViewById(R.id.layout_collection);
        lyComplaint = (LinearLayout)v.findViewById(R.id.layout_complain);
        lyMaintenance = (LinearLayout)v.findViewById(R.id.layout_maintenance);

        CUSTNO = ((CustcardActivity)this.getActivity()).sCustNo;
        CUSTNAME = ((CustcardActivity)this.getActivity()).sCustName;
        ADDRESS = ((CustcardActivity)this.getActivity()).sAddress;

        lyTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Click" , "Taking Order");
                Intent mIntent = new Intent(getActivity(), ListOrderActivity.class);
                startActivity(mIntent);
            }
        });

        lyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Click" , "Collection");
                Intent mIntent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(mIntent);
            }
        });

        lyComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Click" , "Complaint");
                Intent mIntent = new Intent(getActivity(), ComplainActivity.class);
                startActivity(mIntent);
            }
        });

        lyMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Click" , "Maintenance");
                Intent mIntent = new Intent(getActivity(), MaintenanceActivity.class);
                startActivity(mIntent);
            }
        });
    }
}

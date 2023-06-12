package com.soldis.yourthaitea.transaction.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_CUSTMST;
import com.soldis.yourthaitea.entity.Obj_TYPEOUT;
import com.soldis.yourthaitea.transaction.CustcardActivity;

public class frag_transaction_data_outlet extends Fragment {
    TextView txtCompanyName,
            txtAddress,
            txtPhoneOutlet,
            txtKelurahan,
            txtBranch,
            txtTypeOut,
            text_tgl,
            txtKTPNo,
            txtKTPName,
            txtKTPAddress,
            txtNPWPNo,
            txtNPWPName,
            txtNPWPAddress,
            txtPhone,
            txtPaymentType,
            txtOrderPeriod,
            txtStartDate,
            txtEndDate
    ;

    CheckBox chkContract;

    Obj_CUSTMST objCustmst;
    String CUSTNO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaction_dataoutlet, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        InitControl(view);
        FillForm();
    }

    private void InitControl(final View v) {
        txtCompanyName = (TextView)v.findViewById(R.id.txtCompanyName) ;
        txtAddress = (TextView)v.findViewById(R.id.txtAddress) ;
        txtPhoneOutlet = (TextView)v.findViewById(R.id.txtPhoneOutlet) ;
        txtKelurahan = (TextView)v.findViewById(R.id.txtKelurahan) ;
        txtBranch = (TextView)v.findViewById(R.id.txtBranch) ;
        text_tgl = (TextView)v.findViewById(R.id.text_tgl) ;
        txtTypeOut = (TextView)v.findViewById(R.id.txtTypeOut) ;
        txtKTPNo = (TextView)v.findViewById(R.id.txtKTPNo) ;
        txtKTPName = (TextView)v.findViewById(R.id.txtKTPName) ;
        txtKTPAddress = (TextView)v.findViewById(R.id.txtKTPAddress) ;
        txtNPWPNo = (TextView)v.findViewById(R.id.txtNPWPNo) ;
        txtNPWPName = (TextView)v.findViewById(R.id.txtNPWPName) ;
        txtNPWPAddress = (TextView)v.findViewById(R.id.txtNPWPAddress) ;
        txtPhone = (TextView)v.findViewById(R.id.txtPhone) ;
        txtPaymentType = (TextView)v.findViewById(R.id.txtPaymentType) ;
        txtOrderPeriod = (TextView)v.findViewById(R.id.txtOrderPeriod) ;
        txtStartDate = (TextView)v.findViewById(R.id.txtStartDate) ;
        txtEndDate = (TextView)v.findViewById(R.id.txtEndDate) ;

        chkContract = (CheckBox) v.findViewById(R.id.chkContract) ;

    }

    void FillForm(){
        objCustmst = new Obj_CUSTMST();
        CUSTNO = ((CustcardActivity)this.getActivity()).sCustNo;

        Obj_TYPEOUT objTypeout;
        String TYPEOUT = "";

        if (CUSTNO != null){
            objCustmst = objCustmst.getData(CUSTNO);
            txtCompanyName.setText((objCustmst.getCOMPANY_NAME() == null ? "" : objCustmst.getCOMPANY_NAME()));
            txtAddress.setText((objCustmst.getCUSTADD1() == null ? "" : objCustmst.getCUSTADD1()));
            txtPhoneOutlet.setText((objCustmst.getCPHONE1() == null ? "" : objCustmst.getCPHONE1()));
            txtKelurahan.setText((objCustmst.getKELURAHAN() == null ? "" : objCustmst.getKELURAHAN()));

            TYPEOUT = (objCustmst.getTYPEOUT() == null ? "" : objCustmst.getTYPEOUT());
            objTypeout = new Obj_TYPEOUT();
            objTypeout = objTypeout.getData(TYPEOUT);
            txtTypeOut.setText((objTypeout.getTYPENAME() == null ? "" : objTypeout.getTYPENAME()));

            txtBranch.setText(Integer.toString(objCustmst.getNUMBER_OF_BRANCH()));
            text_tgl.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(objCustmst.getDATE_NEXT_VISIT() == null ? "" : objCustmst.getDATE_NEXT_VISIT()));
            txtKTPNo.setText((objCustmst.getKTP_ID() == null ? "" : objCustmst.getKTP_ID()));
            txtKTPName.setText((objCustmst.getKTP_NAME() == null ? "" : objCustmst.getKTP_NAME()));
            txtKTPAddress.setText((objCustmst.getKTP_ADDRESS() == null ? "" : objCustmst.getKTP_ADDRESS()));
            txtNPWPNo.setText((objCustmst.getNPWP_ID() == null ? "" : objCustmst.getNPWP_ID()));
            txtNPWPName.setText((objCustmst.getNPWP_NAME() == null ? "" : objCustmst.getNPWP_NAME()));
            txtNPWPAddress.setText((objCustmst.getNPWP_ADDRESS() == null ? "" : objCustmst.getNPWP_ADDRESS()));
            txtOrderPeriod.setText((objCustmst.getPERIODE_ORDER() == null ? "" : objCustmst.getPERIODE_ORDER()));
            txtPaymentType.setText((objCustmst.getPAYMENT_TYPE() == null ? "" : objCustmst.getPAYMENT_TYPE()));
            txtStartDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY (objCustmst.getSTARTDATE_CONTRACT() == null ? "" : objCustmst.getSTARTDATE_CONTRACT()));
            txtEndDate.setText(AppController.getInstance().getDateYYYYMMDDtoDDMMYYYY(objCustmst.getENDDATE_CONTRACT() == null ? "" : objCustmst.getENDDATE_CONTRACT()));

            String STATUS_CONTRACT = (objCustmst.getSTATUS_CONTRACT() == null ? "" : objCustmst.getSTATUS_CONTRACT());
            if (STATUS_CONTRACT.equals("Y")){
                chkContract.setText(getActivity().getResources().getString(R.string.text_yes));
                chkContract.setChecked(true);
            }else{
                chkContract.setChecked(false);
                chkContract.setText(getActivity().getResources().getString(R.string.text_no));
            }
        }
    }
}

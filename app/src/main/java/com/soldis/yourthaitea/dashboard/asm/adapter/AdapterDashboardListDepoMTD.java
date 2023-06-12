package com.soldis.yourthaitea.dashboard.asm.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.dashboard.summary_sales.DashboardSummarySalesBySalesman;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.net.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 9/19/2016.
 */
public class AdapterDashboardListDepoMTD extends  RecyclerView.Adapter<AdapterDashboardListDepoMTD.ViewHolder>{
    Tbl_ListDepo tblListDepo;
    Tbl_List_Motoris listMotoris;
    private Context context;
    ProgressDialog progress;
    boolean bDays;
    String sDATE;
    public AdapterDashboardListDepoMTD(Context context, Tbl_ListDepo tblListDepo, boolean bDays, String sDATE, OnDownloadClicked listener) {
        this.context = context;
        this.tblListDepo = tblListDepo;
        this.listener = listener;
        this.bDays = bDays;
        this.sDATE = sDATE;
        if (tblListDepo == null) {
            throw new IllegalArgumentException("courses ArrayList must not be null");
        }
    }

    public interface OnDownloadClicked {
        public void OnDownloadClicked(String FAKTUR_NO, String DEPARTEMEN);
    }

    private OnDownloadClicked listener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_dashboard_asm_mtd, null);

        return new ViewHolder(itemView, context, this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Tbl_ListDepo.Datum dat = tblListDepo.data.get(position);

        holder.txtDepo.setText(dat.NAMACABANG == null ? "" : dat.NAMACABANG);

        holder.txtTotalEC.setText(AppController.getInstance().toCurrency(Double.parseDouble(dat.TOTAL_EC)));
        holder.txtTotalECMix.setText(AppController.getInstance().toCurrency(Double.parseDouble(dat.TOTAL_EC)));
        holder.txtTotalECAFH.setText(AppController.getInstance().toCurrency(Double.parseDouble(dat.TOTAL_EC_AFH)));
        holder.txtECTarget.setText(AppController.getInstance().toCurrency(dat.TARGET_EC_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS)) );
        holder.txtECTargetMix.setText(AppController.getInstance().toCurrency(dat.TARGET_EC_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS)) );
        holder.txtECTargetAFH.setText(AppController.getInstance().toCurrency(dat.TARGET_EC_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) );
        holder.txtCallTarget.setText(AppController.getInstance().toCurrency(dat.TARGET_CALL_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS)) );
        holder.txtCallTargetMix.setText(AppController.getInstance().toCurrency(dat.TARGET_CALL_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS)) );
        holder.txtCallTargetAFH.setText(AppController.getInstance().toCurrency(dat.TARGET_CALL_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) );
        holder.txtSalesTarget.setText(AppController.getInstance().toCurrencyRp(dat.TARGET_SALES_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS)) );
        holder.txtSalesTargetMix.setText(AppController.getInstance().toCurrencyRp(dat.TARGET_SALES_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS)) );
        holder.txtSalesTargetAFH.setText(AppController.getInstance().toCurrencyRp(dat.TARGET_SALES_MTD * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) );

        holder.txtTotalCall.setText(AppController.getInstance().toCurrency(Double.parseDouble(dat.TOTAL_CALL)));
        holder.txtTotalSales.setText(AppController.getInstance().toCurrency(dat.INVAMOUNT));
        holder.txtTotalCallAFH.setText(AppController.getInstance().toCurrency(Double.parseDouble(dat.TOTAL_CALL_AFH)));
        holder.txtTotalSalesAFH.setText(AppController.getInstance().toCurrency(dat.INVAMOUNT_AFH));
        holder.txtTotalCallMix.setText(AppController.getInstance().toCurrency(Double.parseDouble(dat.TOTAL_CALL)));
        holder.txtTotalSalesMix.setText(AppController.getInstance().toCurrency(dat.INVAMOUNT));


        double lStatus = 0;
        if (dat.BROW){
            /*if (dat.TOTAL_MIX == 0){
                holder.layout_row.setVisibility(View.VISIBLE);
            }else{
                holder.layout_row_mix.setVisibility(View.VISIBLE);
            }*/
            holder.layout_row.setVisibility(View.VISIBLE);
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
        }else{
            /*if (dat.TOTAL_MIX == 0){
                holder.layout_row.setVisibility(View.GONE);
            }else{
                holder.layout_row_mix.setVisibility(View.GONE);
            }*/
            holder.layout_row.setVisibility(View.GONE);
            holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
        }

        holder.layout_depo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dat.BROW){
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_up));
                    holder.layout_row.setVisibility(View.GONE);
                    /*if (dat.TOTAL_MIX == 0){
                        holder.layout_row.setVisibility(View.GONE);
                    }else{
                        holder.layout_row_mix.setVisibility(View.GONE);
                    }*/
                    dat.BROW = false;
                } else{
                    holder.imgArrow.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down));
                    holder.layout_row.setVisibility(View.VISIBLE);
                    /*if (dat.TOTAL_MIX == 0){
                        holder.layout_row.setVisibility(View.VISIBLE);
                    }else{
                        holder.layout_row_mix.setVisibility(View.VISIBLE);
                    }*/
                    dat.BROW = true;
                }


            }
        });

        //Call
        try{
            if ((dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                lStatus = (Double.parseDouble(dat.TOTAL_CALL) / (dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                if (lStatus > 100){
                    holder.progressCall.setProgress(100);
                } else{
                    holder.progressCall.setProgress((int)lStatus);
                }
            }else{
                holder.progressCall.setProgress(0);

            }

        }catch (Exception e){
            holder.progressCall.setProgress(0);

        }

        if ((dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) > 0){
            lStatus = 0;
            lStatus = (Double.parseDouble(dat.TOTAL_CALL_AFH) / (dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH))) * 100;
            if (lStatus > 100){
                holder.progressCallAFH.setProgress(100);
            } else{
                holder.progressCallAFH.setProgress((int)lStatus);
            }
        }else{
            holder.progressCallAFH.setProgress(0);
        }

        try{
            if ((dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                lStatus = (Double.parseDouble(dat.TOTAL_EC) / (dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                if (lStatus > 100){
                    holder.progressEC.setProgress(100);
                } else{
                    holder.progressEC.setProgress((int)lStatus);
                }

            }else{
                holder.progressEC.setProgress(0);

            }

        }catch (Exception e){
            holder.progressEC.setProgress(0);
        }

        if ((dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) > 0){
            lStatus = 0;
            lStatus = (Double.parseDouble(dat.TOTAL_EC_AFH) / (dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH))) * 100;
            if (lStatus > 100){
                holder.progressECAFH.setProgress(100);
            } else{
                holder.progressECAFH.setProgress((int)lStatus);
            }
        }else{
            holder.progressECAFH.setProgress(0);
        }

        try{
            if ((dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                lStatus = (dat.INVAMOUNT / (dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                if (lStatus > 100){
                    holder.progressSales.setProgress(100);
                } else{
                    holder.progressSales.setProgress((int)lStatus);
                }


            }else{
                holder.progressSales.setProgress(0);
            }


            if ((dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) > 0){
                lStatus = (dat.INVAMOUNT_AFH / (dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH))) * 100;
                if (lStatus > 100){
                    holder.progressSalesAFH.setProgress(100);
                } else{
                    holder.progressSalesAFH.setProgress((int)lStatus);
                }
            }else{
                holder.progressSalesAFH.setProgress(0);
            }
        }catch (Exception e){
            holder.progressSales.setProgress(0);
            holder.progressSalesAFH.setProgress(0);
        }


        /*if (dat.TOTAL_MIX == 0){

            //Call
            try{
                if ((dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                    lStatus = (Double.parseDouble(dat.TOTAL_CALL) / (dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                    if (lStatus > 100){
                        holder.progressCall.setProgress(100);
                    } else{
                        holder.progressCall.setProgress((int)lStatus);
                    }
                }else{
                    holder.progressCall.setProgress(0);

                }

            }catch (Exception e){
                holder.progressCall.setProgress(0);

            }

            if ((dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) > 0){
                lStatus = 0;
                lStatus = (Double.parseDouble(dat.TOTAL_CALL_AFH) / (dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH))) * 100;
                if (lStatus > 100){
                    holder.progressCallAFH.setProgress(100);
                } else{
                    holder.progressCallAFH.setProgress((int)lStatus);
                }
            }else{
                holder.progressCallAFH.setProgress(0);
            }

            try{
                if ((dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                    lStatus = (Double.parseDouble(dat.TOTAL_EC) / (dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                    if (lStatus > 100){
                        holder.progressEC.setProgress(100);
                    } else{
                        holder.progressEC.setProgress((int)lStatus);
                    }

                }else{
                    holder.progressEC.setProgress(0);

                }

            }catch (Exception e){
                holder.progressEC.setProgress(0);
            }

            if ((dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) > 0){
                lStatus = 0;
                lStatus = (Double.parseDouble(dat.TOTAL_EC_AFH) / (dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH))) * 100;
                if (lStatus > 100){
                    holder.progressECAFH.setProgress(100);
                } else{
                    holder.progressECAFH.setProgress((int)lStatus);
                }
            }else{
                holder.progressECAFH.setProgress(0);
            }

            try{
                if ((dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                    lStatus = (dat.INVAMOUNT / (dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                    if (lStatus > 100){
                        holder.progressSales.setProgress(100);
                    } else{
                        holder.progressSales.setProgress((int)lStatus);
                    }


                }else{
                    holder.progressSales.setProgress(0);
                }


                if ((dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH)) > 0){
                    lStatus = (dat.INVAMOUNT_AFH / (dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS_AFH))) * 100;
                    if (lStatus > 100){
                        holder.progressSalesAFH.setProgress(100);
                    } else{
                        holder.progressSalesAFH.setProgress((int)lStatus);
                    }
                }else{
                    holder.progressSalesAFH.setProgress(0);
                }
            }catch (Exception e){
                holder.progressSales.setProgress(0);
                holder.progressSalesAFH.setProgress(0);
            }
        }else{
            //Motorist MIX

            //Call
            try{
                if ((dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                    lStatus = (Double.parseDouble(dat.TOTAL_CALL) / (dat.TARGET_CALL * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                    if (lStatus > 100){
                        holder.progressCallMix.setProgress(100);
                    } else{
                        holder.progressCallMix.setProgress((int)lStatus);
                    }
                }else{
                    holder.progressCallMix.setProgress(0);

                }

            }catch (Exception e){
                holder.progressCallMix.setProgress(0);

            }


            try{
                if ((dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                    lStatus = (Double.parseDouble(dat.TOTAL_EC) / (dat.TARGET_EC * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                    if (lStatus > 100){
                        holder.progressECMix.setProgress(100);
                    } else{
                        holder.progressECMix.setProgress((int)lStatus);
                    }

                }else{
                    holder.progressECMix.setProgress(0);

                }

            }catch (Exception e){
                holder.progressECMix.setProgress(0);
            }


            try{
                if ((dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS)) > 0){
                    lStatus = (dat.INVAMOUNT / (dat.TARGET_SALES * Integer.parseInt(dat.JUMLAH_MOTORIS))) * 100;
                    if (lStatus > 100){
                        holder.progressSalesMix.setProgress(100);
                    } else{
                        holder.progressSalesMix.setProgress((int)lStatus);
                    }


                }else{
                    holder.progressSalesMix.setProgress(0);
                }

            }catch (Exception e){
                holder.progressSalesMix.setProgress(0);
            }

        }*/


        holder.layout_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(context, context.getResources().getString(R.string.main_Information),
                        context.getResources().getString(R.string.setting_sync_data), true);

                try{
                    Call<Tbl_List_Motoris> call;
                    if (bDays){
                        call = NetworkManager.getNetworkService().ListDepoMotoris(dat.KODECABANG,
                                AppConstant.strSlsNo,
                                sDATE);
                    }else{
                        call = NetworkManager.getNetworkService().ListDepoMotorisMTD(dat.KODECABANG,
                                AppConstant.strSlsNo,
                                sDATE);
                    }

                    call.enqueue(new Callback<Tbl_List_Motoris>() {
                        @Override
                        public void onResponse(Call<Tbl_List_Motoris> call, Response<Tbl_List_Motoris> response) {
                            progress.dismiss();
                            int code = response.code();
                            if (code == 200){
                                listMotoris = response.body();
                                if (!listMotoris.error){
                                    AppController.getInstance().getSessionManager().setListMotoris(null);
                                    AppController.getInstance().getSessionManager().setListMotoris(listMotoris);
                                    AppController.getInstance().getSessionManager().putBooleanData(AppConstant.DATA_SALES_MOTORIS, bDays);

                                    Intent mIntent = new Intent(context, DashboardSummarySalesBySalesman.class);
                                    mIntent.putExtra("NAMACABANG", dat.NAMACABANG);
                                    mIntent.putExtra("KODECABANG", dat.KODECABANG);
                                    context.startActivity(mIntent);
                                }else{
                                    AppController.getInstance().CustomeDialog(context,context.getResources().getString(R.string.text_data_not_found));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Tbl_List_Motoris> call, Throwable t) {
                            progress.dismiss();
                        }
                    });
                }catch (Exception e){
                    progress.dismiss();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return tblListDepo.data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView txtDepo,
                txtTotalEC,
                txtECTarget,
                txtCallTarget,
                txtTotalCall,
                txtSalesTarget,
                txtTotalSales,
                txtTotalECAFH,
                txtECTargetAFH,
                txtCallTargetAFH,
                txtTotalCallAFH,
                txtSalesTargetAFH,
                txtTotalSalesAFH,
                txtTotalECMix,
                txtECTargetMix,
                txtCallTargetMix,
                txtSalesTargetMix,
                txtTotalCallMix,
                txtTotalSalesMix,
                txtTotalMotorisMix
                        ;
        LinearLayout layout_row, layout_depo, layout_row_mix;
        ImageView imgArrow;

        ProgressBar progressEC,
                progressCall,
                progressSales,
                progressECAFH,
                progressCallAFH,
                progressSalesAFH,
                progressECMix,
                progressCallMix,
                progressSalesMix

                        ;

        public ViewHolder(View itemView,
                          final Context context,
                          final AdapterDashboardListDepoMTD mCourseAdapter) {
            super(itemView);
            layout_row = (LinearLayout)itemView.findViewById(R.id.layout_row);
            layout_depo = (LinearLayout)itemView.findViewById(R.id.layout_depo);
            txtDepo = (TextView)itemView.findViewById(R.id.txtDepo);
            txtTotalEC = (TextView)itemView.findViewById(R.id.txtTotalEC);
            txtECTarget = (TextView)itemView.findViewById(R.id.txtECTarget);
            txtCallTarget = (TextView)itemView.findViewById(R.id.txtCallTarget);
            txtSalesTarget = (TextView)itemView.findViewById(R.id.txtSalesTarget);
            txtTotalECAFH = (TextView)itemView.findViewById(R.id.txtTotalECAFH);
            txtECTargetAFH = (TextView)itemView.findViewById(R.id.txtECTargetAFH);
            txtCallTargetAFH = (TextView)itemView.findViewById(R.id.txtCallTargetAFH);
            txtSalesTargetAFH = (TextView)itemView.findViewById(R.id.txtSalesTargetAFH);
            txtTotalECMix = (TextView)itemView.findViewById(R.id.txtTotalECMix);
            txtECTargetMix = (TextView)itemView.findViewById(R.id.txtECTargetMix);
            txtCallTargetMix = (TextView)itemView.findViewById(R.id.txtCallTargetMix);
            txtSalesTargetMix = (TextView)itemView.findViewById(R.id.txtSalesTargetMix);

            txtTotalCall = (TextView)itemView.findViewById(R.id.txtTotalCall);
            txtTotalSales = (TextView)itemView.findViewById(R.id.txtTotalSales);
            txtTotalCallAFH = (TextView)itemView.findViewById(R.id.txtTotalCallAFH);
            txtTotalSalesAFH = (TextView)itemView.findViewById(R.id.txtTotalSalesAFH);
            txtTotalCallMix = (TextView)itemView.findViewById(R.id.txtTotalCallMix);
            txtTotalSalesMix = (TextView)itemView.findViewById(R.id.txtTotalSalesMix);

            progressEC = (ProgressBar)itemView.findViewById(R.id.progressEC);
            progressCall = (ProgressBar)itemView.findViewById(R.id.progressCall);
            progressSales = (ProgressBar)itemView.findViewById(R.id.progressSales);
            progressECAFH = (ProgressBar)itemView.findViewById(R.id.progressECAFH);
            progressCallAFH = (ProgressBar)itemView.findViewById(R.id.progressCallAFH);
            progressSalesAFH = (ProgressBar)itemView.findViewById(R.id.progressSalesAFH);
            progressECMix = (ProgressBar)itemView.findViewById(R.id.progressECMix);
            progressCallMix = (ProgressBar)itemView.findViewById(R.id.progressCallMix);
            progressSalesMix = (ProgressBar)itemView.findViewById(R.id.progressSalesMix);
            imgArrow = (ImageView)itemView.findViewById(R.id.img_arrow);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}

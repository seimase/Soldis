package com.soldis.yourthaitea.model.net;

import com.soldis.yourthaitea.model.ApiGoogleMaps;
import com.soldis.yourthaitea.model.Obj_SendData;
import com.soldis.yourthaitea.model.Tbl_Custmst;
import com.soldis.yourthaitea.model.Tbl_CustmstGen;
import com.soldis.yourthaitea.model.Tbl_Dashboard_DataOutlet;
import com.soldis.yourthaitea.model.Tbl_ListDepo;
import com.soldis.yourthaitea.model.Tbl_ListSummarySales_ByDepo;
import com.soldis.yourthaitea.model.Tbl_ListZone;
import com.soldis.yourthaitea.model.Tbl_List_Motoris;
import com.soldis.yourthaitea.model.Tbl_List_Sales_Motoris;
import com.soldis.yourthaitea.model.Tbl_Master;
import com.soldis.yourthaitea.model.Tbl_Message;
import com.soldis.yourthaitea.model.Tbl_Karyawan;
import com.soldis.yourthaitea.model.Tbl_Presence;
import com.soldis.yourthaitea.model.Tbl_Promo_Banner;
import com.soldis.yourthaitea.model.Tbl_Ringkasan;
import com.soldis.yourthaitea.model.Tbl_SendDataCustNotGPS;
import com.soldis.yourthaitea.model.Tbl_Version;
import com.soldis.yourthaitea.model.Tbl_Visit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by User on 20/07/2016.
 */
public interface NetworkService {
    @GET("maps/api/geocode/json")
    Call<ApiGoogleMaps> getMapsAPI(@Query("latlng") String latlng,
                                   @Query("key") String key) ;

    @POST("new_outlet_endofday")
    Call<Tbl_Master> UploadData(@Body RequestBody filePhoto);

    @FormUrlEncoded
    @POST("login")
    Call<Tbl_Karyawan> loginUser(@Field("username") String Username,
                                 @Field("password") String Password,
                                 @Field("mitra_id") String mitra_id);

    @FormUrlEncoded
    @POST("logout")
    Call<Tbl_Karyawan> logOutUser(@Field("username") String Username,
                                  @Field("mitra_id") String mitra_id);

    @FormUrlEncoded
    @POST("updatedevice")
    Call<Tbl_Karyawan> UpdateDevice(@Field("username") String Username,
                                    @Field("password") String Password,
                                    @Field("device_id") String DeviceId,
                                    @Field("mitra_id") String mitra_id);

    @FormUrlEncoded
    @POST("list_sales")
    Call<Tbl_Ringkasan> ListSales(@Field("mitra_id") String KodeCabang,
                                  @Field("slsno") String slsno,
                                  @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("checkversion")
    Call<Tbl_Version> CheckVersion(@Field("mitra_id") String KodeCabang,
                                   @Field("slsno") String slsno);

    @POST("startvisit_json")
    Call<Tbl_Visit> StartVisit(@Body RequestBody object);


    @FormUrlEncoded
    @POST("master_product")
    Call<Tbl_Master> MasterProduct(@Field("mitra_id") String KodeCabang);

    @FormUrlEncoded
    @POST("master_product_rev8")
    Call<Tbl_Master> MasterProductRev7(@Field("mitra_id") String KodeCabang,
                                       @Field("sales_type") String sales_type,
                                       @Field("application_type") String application_type);

    @FormUrlEncoded
    @POST("list_outlet")
    Call<Tbl_Custmst> ListOutlet(@Field("mitra_id") String KodeCabang,
                                 @Field("slsno") String slsno);

    @FormUrlEncoded
    @POST("list_outletgen")
    Call<Tbl_CustmstGen> ListOutletGen(@Field("slsno") String slsno);

    @FormUrlEncoded
    @POST("importfrom_sfa")
    Call<Tbl_Message> ImportFromSFA(@Field("slsno") String slsno);


    @FormUrlEncoded
    @POST("list_ass_motoris")
    Call<Tbl_List_Motoris> ListAssMotoris(@Field("mitra_id") String KodeCabang,
                                          @Field("slsno") String slsno,
                                          @Field("tgltrx") String tgltrx);


    @FormUrlEncoded
    @POST("list_ass_motoris_mtd")
    Call<Tbl_List_Motoris> ListAssMotorisMTD(@Field("mitra_id") String KodeCabang,
                                             @Field("slsno") String slsno,
                                             @Field("tgltrx") String tgltrx);


    @FormUrlEncoded
    @POST("list_depo_motoris")
    Call<Tbl_List_Motoris> ListDepoMotoris(@Field("mitra_id") String KodeCabang,
                                           @Field("slsno") String slsno,
                                           @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo_motoris_mtd")
    Call<Tbl_List_Motoris> ListDepoMotorisMTD(@Field("mitra_id") String KodeCabang,
                                              @Field("slsno") String slsno,
                                              @Field("tgltrx") String tgltrx);

    @Multipart
    @POST("add_product")
    Call<Tbl_Master> postAddProduct(@Part("kodecabang") RequestBody kodecabang,
                                    @Part("tgltrx") RequestBody tgltrx,
                                    @Part("idmakanan") RequestBody idmakanan,
                                    @Part("namamakanan") RequestBody namamakanan,
                                    @Part("categoryid") RequestBody categoryid,
                                    @Part("harga") RequestBody price,
                                    @Part("slsno") RequestBody slsno);

    @Multipart
    @POST("add_product_photo")
    Call<Tbl_Master> postAddProductPhoto(@Part("kodecabang") RequestBody kodecabang,
                                    @Part("tgltrx") RequestBody tgltrx,
                                    @Part("idmakanan") RequestBody idmakanan,
                                    @Part("namamakanan") RequestBody namamakanan,
                                    @Part("categoryid") RequestBody categoryid,
                                    @Part("harga") RequestBody price,
                                    @Part("slsno") RequestBody slsno,
                                    @Part("photo") RequestBody photo,
                                    @Part MultipartBody.Part filePhoto);


    @FormUrlEncoded
    @POST("list_sales_depo_motoris")
    Call<Tbl_List_Sales_Motoris> ListSalesDepoMotoris(@Field("mitra_id") String KodeCabang,
                                                      @Field("slsno") String slsno,
                                                      @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo")
    Call<Tbl_ListDepo> ListDepo(@Field("mitra_id") String KodeCabang,
                                @Field("slsno") String slsno,
                                @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo_only")
    Call<Tbl_ListDepo> ListDepoOnly(@Field("mitra_id") String KodeCabang,
                                    @Field("slsno") String slsno,
                                    @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo_only_bot")
    Call<Tbl_ListDepo> ListDepoOnlyBot(@Field("mitra_id") String KodeCabang,
                                       @Field("slsno") String slsno,
                                       @Field("zoneid") String zoneid,
                                       @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo_mtd")
    Call<Tbl_ListDepo> ListDepoMTD(@Field("mitra_id") String KodeCabang,
                                   @Field("slsno") String slsno,
                                   @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo_bot")
    Call<Tbl_ListDepo> ListDepoBot(@Field("mitra_id") String KodeCabang,
                                   @Field("slsno") String slsno,
                                   @Field("zoneid") String zoneid,
                                   @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_depo_bot_mtd")
    Call<Tbl_ListDepo> ListDepoBotMTD(@Field("mitra_id") String KodeCabang,
                                      @Field("slsno") String slsno,
                                      @Field("zoneid") String zoneid,
                                      @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_zone")
    Call<Tbl_ListZone> ListZone(@Field("slsno") String slsno);

    @FormUrlEncoded
    @POST("list_zone_only")
    Call<Tbl_ListZone> ListZoneOnly(@Field("slsno") String slsno);

    @FormUrlEncoded
    @POST("list_presence")
    Call<Tbl_Presence> ListPresence(@Field("slsno") String slsno,
                                    @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_stock")
    Call<Tbl_Presence> ListStock(@Field("slsno") String slsno,
                                 @Field("mitra_id") String mitra_id,
                                 @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("deletevisit")
    Call<Tbl_Message> DeleteVisit(@Field("slsno") String slsno,
                                  @Field("tgl") String tgl,
                                  @Field("mitra_id") String mitra_id);

    @FormUrlEncoded
    @POST("changename")
    Call<Tbl_Message> ChangeName(@Field("slsno") String slsno,
                                 @Field("slsName") String slsname,
                                 @Field("mitra_id") String mitra_id);

    @FormUrlEncoded
    @POST("dashboard_data_outlet")
    Call<Tbl_Dashboard_DataOutlet> DashboardDataOutlet(@Field("mitra_id") String mitra_id);


    @FormUrlEncoded
    @POST("resetdevice")
    Call<Tbl_Message> ResetDevice(@Field("slsno") String slsno,
                                  @Field("mitra_id") String mitra_id);

    @FormUrlEncoded
    @POST("promo_banner")
    Call<Tbl_Promo_Banner> PromoBanner(@Field("mitra_id") String KodeCabang);

    @Multipart
    @POST("new_outlet")
    Call<Tbl_Custmst> postNewOutlet(@Part("mitra_id") RequestBody mitra_id,
                                    @Part("slsno") RequestBody slsno,
                                    @Part("custno") RequestBody custno,
                                    @Part("custname") RequestBody custname,
                                    @Part("contact") RequestBody file_size_raw,
                                    @Part("address") RequestBody address,
                                    @Part("telp") RequestBody telp,
                                    @Part("kelurahan") RequestBody kelurahan,
                                    @Part("typeout") RequestBody typeout,
                                    @Part("company_name") RequestBody company_name,
                                    @Part("date_next_visit") RequestBody date_next_visit,
                                    @Part("number_of_branch") RequestBody number_of_branch,
                                    @Part("photo") RequestBody photo,
                                    @Part("google_provinsi") RequestBody google_provinsi,
                                    @Part("google_kabupaten") RequestBody google_kabupaten,
                                    @Part("google_kecamatan") RequestBody google_kecamatan,
                                    @Part("google_kelurahan") RequestBody google_kelurahan,
                                    @Part("google_alamat") RequestBody google_alamat,
                                    @Part("google_kodepos") RequestBody google_kodepos,
                                    @Part("latitude") RequestBody latitude,
                                    @Part("longitude") RequestBody longitude,
                                    @Part("flagout") RequestBody flagout,
                                    @Part("ktp_id") RequestBody ktp_id,
                                    @Part("ktp_name") RequestBody ktp_name,
                                    @Part("ktp_address") RequestBody ktp_address,
                                    @Part("npwp_id") RequestBody npwp_id,
                                    @Part("npwp_name") RequestBody npwp_name,
                                    @Part("npwp_address") RequestBody npwp_address,
                                    @Part("payment_type") RequestBody payment_type,
                                    @Part("periode_order") RequestBody periode_order,
                                    @Part("status_contract") RequestBody status_contract,
                                    @Part("startdate_contract") RequestBody startdate_contract,
                                    @Part("enddate_contract") RequestBody enddate_contract,
                                    @Part("status_dispenser") RequestBody status_dispenser,
                                    @Part("dispenser_jrt") RequestBody dispenser_jrt,
                                    @Part("dispenser_hrt") RequestBody dispenser_hrt,
                                    @Part("dispenser_multifold") RequestBody dispenser_multifold,
                                    @Part("dispenser_roll") RequestBody dispenser_roll,
                                    @Part MultipartBody.Part filePhoto);

    @Multipart
    @POST("new_2outlet")
    Call<Tbl_Custmst> postNewOutlet2Photo(@Part("mitra_id") RequestBody mitra_id,
                                          @Part("slsno") RequestBody slsno,
                                          @Part("custno") RequestBody custno,
                                          @Part("custname") RequestBody custname,
                                          @Part("contact") RequestBody file_size_raw,
                                          @Part("address") RequestBody address,
                                          @Part("telp") RequestBody telp,
                                          @Part("kelurahan") RequestBody kelurahan,
                                          @Part("typeout") RequestBody typeout,
                                          @Part("company_name") RequestBody company_name,
                                          @Part("date_next_visit") RequestBody date_next_visit,
                                          @Part("number_of_branch") RequestBody number_of_branch,
                                          @Part("photo") RequestBody photo,
                                          @Part("photo_samping") RequestBody photo_samping,
                                          @Part("google_provinsi") RequestBody google_provinsi,
                                          @Part("google_kabupaten") RequestBody google_kabupaten,
                                          @Part("google_kecamatan") RequestBody google_kecamatan,
                                          @Part("google_kelurahan") RequestBody google_kelurahan,
                                          @Part("google_alamat") RequestBody google_alamat,
                                          @Part("google_kodepos") RequestBody google_kodepos,
                                          @Part("latitude") RequestBody latitude,
                                          @Part("longitude") RequestBody longitude,
                                          @Part("flagout") RequestBody flagout,
                                          @Part("ktp_id") RequestBody ktp_id,
                                          @Part("ktp_name") RequestBody ktp_name,
                                          @Part("ktp_address") RequestBody ktp_address,
                                          @Part("npwp_id") RequestBody npwp_id,
                                          @Part("npwp_name") RequestBody npwp_name,
                                          @Part("npwp_address") RequestBody npwp_address,
                                          @Part("payment_type") RequestBody payment_type,
                                          @Part("periode_order") RequestBody periode_order,
                                          @Part("status_contract") RequestBody status_contract,
                                          @Part("startdate_contract") RequestBody startdate_contract,
                                          @Part("enddate_contract") RequestBody enddate_contract,
                                          @Part("status_dispenser") RequestBody status_dispenser,
                                          @Part("dispenser_jrt") RequestBody dispenser_jrt,
                                          @Part("dispenser_hrt") RequestBody dispenser_hrt,
                                          @Part("dispenser_multifold") RequestBody dispenser_multifold,
                                          @Part("dispenser_roll") RequestBody dispenser_roll,
                                          @Part MultipartBody.Part filePhoto,
                                          @Part MultipartBody.Part filePhotoSamping);

    @Multipart
    @POST("updatemotoris")
    Call<Tbl_Karyawan> postUpdateMotoris(@Part("mitra_id") RequestBody mitra_id,
                                         @Part("slsno") RequestBody slsno,
                                         @Part("photo") RequestBody photo,
                                         @Part MultipartBody.Part filePhoto);

    @Multipart
    @POST("updatphotooutlet")
    Call<Tbl_Message> postUpdatePhotoOutlet(@Part("mitra_id") RequestBody mitra_id,
                                        @Part("custno") RequestBody custno,
                                        @Part("photo") RequestBody photo,
                                        @Part MultipartBody.Part filePhoto);

    //Non Active------------------------------------------------------------------------------------
    @POST("input_trx")
    Call<Obj_SendData> postTransacation(@Body String object);

    @POST("input_trx_all")
    Call<Obj_SendData> postTransacationAll(@Body String object);

    //----------------------------------------------------------------------------------------------

    @POST("input_trx_all_custcard1custmst")
    Call<Obj_SendData> postTransacationWithCustCardCusMst(@Body RequestBody object);

    @POST("generate_custgps")
    Call<Tbl_SendDataCustNotGPS> GenerateDataCustNotGPS(@Body RequestBody object);



    //New Reporting
    @FormUrlEncoded
    @POST("list_summarysales_bydepo")
    Call<Tbl_ListSummarySales_ByDepo> ListSummarySalesByDepo(@Field("team_id") String team_id,
                                                    @Field("tgltrx") String tgltrx);

    @FormUrlEncoded
    @POST("list_summarysales_bydepo_mtd")
    Call<Tbl_ListSummarySales_ByDepo> ListSummarySalesByDepoMTD(@Field("team_id") String team_id,
                                                    @Field("tgltrx") String tgltrx);
}

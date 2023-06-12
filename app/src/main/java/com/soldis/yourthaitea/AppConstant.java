package com.soldis.yourthaitea;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.os.Environment;

import com.soldis.yourthaitea.entity.Obj_MASTER;
import com.soldis.yourthaitea.model.Tbl_Ringkasan;

import net.sqlcipher.database.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public final class AppConstant {
	// Shared Preferences
	public static SharedPreferences pref;

	// Editor for Shared preferences
	public static SharedPreferences.Editor editor;
	public static final String DEVICE_ID = "device_id";
	public static final String LANGUAGE = "language";
	public static final String SESSION_PREFERENCE = "sesion_preference";
	public static final String EMPTY_STRING = "";
	public static final String LANGUAGE_DEFAULT = "en";
	public static final String PARAM_FCM = "param_fcm";
	public static final String DATE_SYNC_PRODUCT = "date_sync_product";
	public static final String DATE_SYNC_OUTLET = "date_sync_outlet";
	public static final String START_ON = "start_on";
	public static final String DATA_SALES_MOTORIS = "data_sales_motoris";
	public static String HASHID ;
	public static String REQID ;
	public static String USER ;
	public static String PASSWORD ;
	public static String DATABASE_NAME_ASSET = "SOLDIS_POS.db";
	public static String KEY_DATABASE = "Soldis.1bc29400";
	public static String KEY_PASSWORD = "S.1bc29400";
	public static String KEY_USER = "bc12b22e93149c175cc034f69b031f35";
	public static String PILIH_PESAN = "1" ;
	public static String SEMUA_PESAN = "2" ;
	public static String TIDAK_PESAN = "3" ;
	public static String TOKEN = "token" ;
	public static String VERSION_DB = "1.0.1.3" ;
	public static String REVISION = " Staging" ;
	public static String APK_FILENAME;
	public static String ACTIVITY_FROM;
	public static final String DOMAIN_GOOGLE_MAPS ="https://maps.googleapis.com/";

	public static String DOMAIN_URL = AppController.getInstance().getSessionManager().getStringData(SessionManager.DOMAIN_URL);
	//public static final String DOMAIN_URL = "http://192.168.1.100/rest_server";

	//public static final String DOMAIN_URL = "https://tbumotoris.app.co.id/rest_server";
	public final static String API_VERSION = "/api/v1/";
	public final static String PHOTO_PROMO_URL = DOMAIN_URL + "/uploads/promo/";
	public final static String PHOTO_MOTORIS_URL = DOMAIN_URL + "/uploads/users/";
	public final static String PHOTO_PRODUCT_URL = DOMAIN_URL + "/uploads/product/";
	public final static String PHOTO_OUTLET_URL = DOMAIN_URL + "/uploads/outlet/";

	public static String API_KEY_GOOGLE_MAPS;
	//public final static String API_KEY_GOOGLE_MAPS = "AIzaSyCSGVQn-EutVpgGr2nyslFtqa5CdLV8kFc";

	public static SQLiteDatabase myDb;
	public static String STORAGE_CARD = Environment.getExternalStorageDirectory().toString() ;
	public static String FOLDER_MAIN= STORAGE_CARD + "/Soldis";
	public static String FOLDER_PROJECT = FOLDER_MAIN + "/YourThaiTea";
	public static String FOLDER_DOWNLOAD =  FOLDER_PROJECT + "/Download";
	public static String PATH_PHOTO =  FOLDER_PROJECT + "/Photo/";
	public static String PATH_PHOTO_CAMERA =  FOLDER_PROJECT + "/Camera/";
	public static String PATH_DB = FOLDER_PROJECT +"/db/SOLDIS_POS.db";
	public static String PATH_FOLDER_DB = FOLDER_PROJECT +"/db";
	public static String PATH_FOLDER_DB_BACKUP =  FOLDER_PROJECT + "/Backup";
	public static String PATH_FOLDER_PDF = FOLDER_PROJECT +"/Pdf";
	public static String PATH_FOLDER_APK = FOLDER_PROJECT +"/Apk";
	public static String PATH_FOLDER_XLS = FOLDER_PROJECT +"/Xls";
	public static String PATH_FOLDER_TRX = FOLDER_PROJECT +"/Trx";
	public static String PATH_FOLDER_LOG = FOLDER_PROJECT +"/Log";


	public static String[] menuList;
	public static int[] menuIcon;
	public static int[] menuIconSelected;
	public static String PDFVIEW_FROM;
	public static String PDFVIEW_SOURCE;
	public static String PDF_FILENAME;

	//Parameter database
	public static String gGHarga;
	public static String strCustNo;
	public static String strMejaNo;
	public static String strOrderNo;
	public static int iSeqNo;
	public static String strCustName;
	public static String strCustAddress;
	public static String strCustTypeName;
	public static String strCustPhotoName;
	public static String strSlsNo;
	public static String strSlsName;
	public static String strSlsPass;
	public static String strMitraID;
	public static String strTglTrx;
	public static String strLevel;
	public static String strLevelDescription;
	public static int iType;
	public static String strApplicationType;
	public static String strTypeDescription;
	public static String strPrefixID;
	public static double dblUangMakan;

	public static boolean bRefresh;
	public static boolean bClose;

	public static BluetoothSocket mBluetoothSocket;
	public static BluetoothDevice mBluetoothDevice;
	public static ArrayList<Obj_MASTER> objMasters;
	public static Tbl_Ringkasan tblRingkasan;
}

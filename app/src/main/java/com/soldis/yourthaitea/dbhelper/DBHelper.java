package com.soldis.yourthaitea.dbhelper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;

import com.soldis.yourthaitea.AppConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME_ASSET = AppConstant.DATABASE_NAME_ASSET;
	//static String getStorageCard = Environment.getExternalStorageDirectory().toString();
	private static final String DATABASE_NAME = AppConstant.PATH_DB;
	//private static final String DB_PATH = "/data/data/com.eska.file/databases/";

	//private static final String DB_PATH = "/sdcard/EskaMobile/db/";
	private static final int DATABASE_VERSION = 3;
	private Context context;
	private SQLiteDatabase myDataBase; 
	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	 
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		//do nothing - database already exist
    		//copyDataBase();
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	copyDataBase();
    	}
 
    }
    
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	boolean StsDb;
    	StsDb = false;
    	String myPath = DATABASE_NAME;
    	try{
    		//String myPath = DB_PATH + DATABASE_NAME;
    		
    		
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    		
    		File file = new File(myPath);
    		if (file.exists()){
    			StsDb = true;	
        	}else{
        		StsDb = false;
        	}
        	
    	}catch(SQLiteException e){
    		File file = new File(myPath);
    		if (file.exists()){
    			StsDb = true;	
        	}else{
        		StsDb = false;
        	}
    		//database does't exist yet.
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}

    	return StsDb;
    	//return checkDB != null ? true : false;
    }
    
	private void copyDataBase() throws IOException{
		// TODO Auto-generated method stub
		InputStream myInput = context.getAssets().open(DATABASE_NAME_ASSET);
		File outFile = context.getDatabasePath(DATABASE_NAME);
		
		outFile.delete();
		
		OutputStream myOutput = new FileOutputStream(outFile);
		
		byte [] buffer = new byte[1204];
		int length ;
		while ((length =  myInput.read(buffer)) > 0){
			myOutput.write(buffer,0,length);
		}
		
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	
	public SQLiteDatabase openDataBase() throws SQLException{
	 
	//Open the database
        //String myPath = DB_PATH + DATABASE_NAME;
        String myPath = DATABASE_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	return myDataBase;
    }
	
	@Override
	public synchronized void close() {
 
	    if(myDataBase != null)
		    myDataBase.close();
 
    	super.close();
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
}

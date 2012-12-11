package com.sim.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sim.evamedic.MyApp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper{
	 
    private static String DB_PATH = "/data/data/com.sim.evamedic/databases/"; 
    private static String DB_NAME = "data.sqlite"; 
    private SQLiteDatabase myDataBase; 
 
    public SQLite() {
    	super(MyApp.getInstance(null), DB_NAME, null, 1);
    }	
 

    public void createDataBase() {
    	boolean dbExist = checkDataBase();
    	if(!dbExist){
    		this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        	}
    	}
    }
    
    public SQLiteDatabase getDB() {
    	return myDataBase;
    }
 
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(Exception e){
    		return false;
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }
 
    private void copyDataBase() throws IOException{

    	InputStream myInput = MyApp.getInstance(null).getAssets().open(DB_NAME);
    	String outFileName = DB_PATH + DB_NAME;
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    public void openDataBase() {
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, 
    			SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {

	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
}
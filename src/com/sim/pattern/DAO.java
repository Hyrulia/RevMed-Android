package com.sim.pattern;

import android.database.sqlite.SQLiteDatabase;

public abstract class DAO<T> {
	
	protected SQLiteDatabase db;
	protected SQLite sql;
	
	public DAO() {
		sql = new SQLite();
		sql.createDataBase();
	}
	
	public void open() {
		sql.openDataBase();
		db = sql.getDB();
	}
	
	public void close() {
		db.close();
	}
	
}

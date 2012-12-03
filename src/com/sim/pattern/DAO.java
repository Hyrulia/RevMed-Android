package com.sim.pattern;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

import com.sim.dao.SQLite;

public abstract class DAO<T> {
	
	protected SQLiteDatabase db;
	protected SQLite sql;
	
	public DAO() {
		
	}
	
	public void open() {
		
	}
	
	public void close() {
		
	}
	
	public abstract long create(T object);
	
	public abstract ArrayList<T> getAll();
	
	public abstract T getById(int id);
}

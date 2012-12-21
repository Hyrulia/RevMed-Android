package com.sim.dao;

import java.util.ArrayList;

import android.database.Cursor;
import com.sim.entities.Speciality;
import com.sim.pattern.DAO;

public class SpecialityDAO extends DAO<Speciality>{

	public final static String TABLE_NAME = "specialities";
	public final static String ID = "_id";
	public final static String SPECIALITY = "speciality";
	
	
	@Override
	public long create(Speciality object) {
		
		return 0;
	}

	@Override
	public ArrayList<Speciality> getAll() {
		Cursor crs = db.query(TABLE_NAME, 
			new String[] { ID, SPECIALITY},
			null, null, null, null, null);

		ArrayList<Speciality> specialities = new ArrayList<Speciality>();
		while(crs.moveToNext()) {
			Speciality s = new Speciality();
			s.setId(crs.getInt(crs.getColumnIndex(ID)));
			s.setSpeciality(crs.getString(crs.getColumnIndex(SPECIALITY)));
			specialities.add(s);
		}
		crs.close();
		return specialities;
		
	}

	@Override
	public Speciality getById(int id) {
		
		return null;
	}

}

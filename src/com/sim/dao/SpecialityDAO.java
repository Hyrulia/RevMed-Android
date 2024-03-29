package com.sim.dao;

import java.util.ArrayList;

import android.database.Cursor;
import com.sim.entities.Speciality;
import com.sim.pattern.DAO;

public class SpecialityDAO extends DAO<Speciality>{

	public final static String TABLE_NAME = "specialities";
	public final static String ID = "_id";
	public final static String SPECIALITY = "speciality";
	

	public ArrayList<Speciality> getAll() {
		Cursor crs = db.query(TABLE_NAME, 
			new String[] { ID, SPECIALITY},
			null, null, null, null, SPECIALITY);
		
		ObjectiveDAO dao = new ObjectiveDAO();
		dao.open();
		ArrayList<Speciality> specialities = new ArrayList<Speciality>();
		while(crs.moveToNext()) {
			Speciality s = new Speciality();
			s.setId(crs.getInt(crs.getColumnIndex(ID)));
			s.setSpeciality(crs.getString(crs.getColumnIndex(SPECIALITY)));
			s.setObjectives(dao.getBySpecialityId(s.getId()));
			specialities.add(s);
		}
		crs.close();
		dao.close();
		return specialities;		
	}


}

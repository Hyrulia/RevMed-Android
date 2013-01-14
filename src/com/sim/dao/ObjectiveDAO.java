package com.sim.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import com.sim.entities.Choice;
import com.sim.entities.Objective;
import com.sim.pattern.DAO;

public class ObjectiveDAO extends DAO<Choice> {
	
	public static final String TABLE_NAME  		 = "objectives";
	public static final String ID        		 = "_id";
	public static final String OBJECTIVE		 = "objective";
	public static final String SPECIALITY_ID 	 = "speciality_id";
	

	public List<Objective> getBySpecialityId(int id) {

		Cursor crs = db.query(TABLE_NAME, 
			new String[] {ID, OBJECTIVE, SPECIALITY_ID},
			SPECIALITY_ID + "=" + id, 
			null, null, null, null);

		 List<Objective> objectives = new ArrayList<Objective>();
		
		while(crs.moveToNext()) {
			Objective o = new Objective();
			o.setId(crs.getInt(crs.getColumnIndex(ID)));
			o.setObjective(crs.getString(crs.getColumnIndex(OBJECTIVE)));
			o.setSpeciality_id(crs.getInt(crs.getColumnIndex(SPECIALITY_ID)));
			objectives.add(o);
		}
		crs.close();
		return objectives;
	}
	
	public Objective getById(int id) {
		Cursor crs = db.query(TABLE_NAME, 
				new String[] {ID, OBJECTIVE, SPECIALITY_ID},
				ID + "=" + id, 
				null, null, null, null);
		Objective o = null;
		if(crs.moveToNext()) {
			o = new Objective();
			o.setId(crs.getInt(crs.getColumnIndex(ID)));
			o.setObjective(crs.getString(crs.getColumnIndex(OBJECTIVE)));
			o.setSpeciality_id(crs.getInt(crs.getColumnIndex(SPECIALITY_ID)));
		}
		return o;
	}

	
}

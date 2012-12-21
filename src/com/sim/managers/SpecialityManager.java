package com.sim.managers;


import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sim.dao.SpecialityDAO;
import com.sim.entities.Speciality;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.R;
import com.sim.pattern.DAOFactory;

public class SpecialityManager extends BaseAdapter{

	private List<Speciality> specialities;
	
	public SpecialityManager() {
		
	}
	
	public void fetchSpecialities() {
		SpecialityDAO dao = (SpecialityDAO) DAOFactory.create(
			DAOFactory.SPECIALITY);
		specialities = dao.getAll();
	}


	
	@Override
	public int getCount() {		
		return specialities.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		return specialities.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		TextView view = new TextView(MyApp.getContext());
		view.setBackgroundResource(R.drawable.textshape);
		view.setText(specialities.get(arg0).getSpeciality());
		return view;
	}
}

package com.sim.managers;


import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sim.dao.ObjectiveDAO;
import com.sim.entities.Objective;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.R;
import com.sim.pattern.DAOFactory;

public class ObjectiveManager extends BaseAdapter{

	private List<Objective> objectives;
	private int specialtyId;
	
	public ObjectiveManager(int specialityId) {
		this.specialtyId = specialityId;
		fetchObjectives();
	}
	
	public void fetchObjectives() {
		ObjectiveDAO dao = (ObjectiveDAO) DAOFactory.create(
			DAOFactory.OBJECTIVE);
		dao.open();
		objectives = dao.getBySpecialityId(specialtyId);
		dao.close();
	}


	@Override
	public int getCount() {		
		return objectives.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		return objectives.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		TextView view = new TextView(MyApp.getContext());
		view.setBackgroundResource(R.drawable.button_choice_selector);
		view.setText(objectives.get(arg0).getObjective());
		return view;
	}
}

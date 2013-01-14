package com.sim.managers;


import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sim.dao.SpecialityDAO;
import com.sim.entities.Objective;
import com.sim.entities.Speciality;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.R;
import com.sim.pattern.DAOFactory;

public class SpecialityManager extends BaseExpandableListAdapter{

	private List<Speciality> specialities;
	LayoutInflater inflater = (LayoutInflater) MyApp.getContext()
			.getSystemService("layout_inflater");
	public SpecialityManager() {
		
	}
	
	public void fetchSpecialities() {
		SpecialityDAO dao = (SpecialityDAO) DAOFactory.create(
			DAOFactory.SPECIALITY);
		dao.open();
		specialities = dao.getAll();
		dao.close();
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return specialities.get(arg0).getObjectives().get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		
		return arg1;
	}

	@Override
	public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {
		View view = inflater.inflate(R.layout.item_objective, null);
		TextView text = (TextView) view.findViewById(R.id.objectiveItem);
		Objective o = (Objective) getChild(arg0, arg1);
		text.setText(o.getObjective());
		return view;
	}

	@Override
	public int getChildrenCount(int arg0) {
		
		return specialities.get(arg0).getObjectives().size();
	}

	@Override
	public Object getGroup(int arg0) {
		return specialities.get(arg0);
	}

	@Override
	public int getGroupCount() {
		return specialities.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
		int[] icone = new int[]{R.drawable.spec_icone_1, R.drawable.spec_icone_2,
				R.drawable.spec_icone_3, R.drawable.spec_icone_4,
				R.drawable.spec_icone_5, R.drawable.spec_icone_6,
				R.drawable.spec_icone_7, R.drawable.spec_icone_8};
		View view = inflater.inflate(R.layout.item_speciality, null);
		TextView text = (TextView) view.findViewById(R.id.specialityItem);
		ImageView img = (ImageView) view.findViewById(R.id.specIcon);
		img.setImageResource(icone[arg0 % icone.length]);
		text.setText(specialities.get(arg0).getSpeciality());
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

}

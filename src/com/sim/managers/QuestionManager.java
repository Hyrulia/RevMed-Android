package com.sim.managers;


import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sim.dao.ChoiceDAO;
import com.sim.dao.QuestionDAO;
import com.sim.entities.Choice;
import com.sim.entities.Question;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.R;
import com.sim.pattern.DAOFactory;

public class QuestionManager extends BaseAdapter{
	private int currentQuestionNumber = 0;
	private int maxQuestion = 0;
	private int specialityId = -1;
	private List<Question> questions;
	private List<Choice> choices;
	
	public QuestionManager() {
		
	}
	
	public QuestionManager(int specId) {
		specialityId = specId;
	}
	
	public void fetchQuestions() {
		QuestionDAO dao = (QuestionDAO) DAOFactory.create(DAOFactory.QUESTION);
		questions = dao.getLimitedAll(specialityId, maxQuestion);
	}
	
	public void fetchChoices() {
		ChoiceDAO dao = (ChoiceDAO) DAOFactory.create(DAOFactory.CHOICE);
		choices = dao.getByQuestionId(questions.get(currentQuestionNumber)
			.getId());
	}
	
	public void nextQuestion() {
		currentQuestionNumber++;
		fetchChoices();
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {		
		return choices.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		return choices.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		TextView view = new TextView(MyApp.getContext());
		view.setBackgroundResource(R.drawable.textshape);
		view.setText(choices.get(arg0).getChoice());
		return view;
	}
}

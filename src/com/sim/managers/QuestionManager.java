package com.sim.managers;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sim.dao.ChoiceDAO;
import com.sim.dao.QuestionDAO;
import com.sim.entities.Choice;
import com.sim.entities.Question;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.QuestionActivity;
import com.sim.evamedic.R;
import com.sim.pattern.DAOFactory;

public class QuestionManager extends BaseAdapter {
	private int currentQuestionNumber = 0;
	private int maxQuestion = 3;
	private int specialityId = 1;
	private List<Question> questions;
	private List<Choice> choices;
	private WeakReference<Activity> activity;
	private ProgressBar barView;
	private QuestionTaskManager task = new QuestionTaskManager(this);
	private boolean finished = false;
	private List<Integer> index = new ArrayList<Integer>();

	
	public QuestionManager(int specId, Activity act) {
		specialityId = specId;
		activity = new WeakReference<Activity>(act);
		barView = (ProgressBar) activity.get().findViewById(R.id.countDownBar);
		fetchQuestions();
		fetchChoices();
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void run() {
		task.execute();
	}
		
	public void update(final int p) {
		barView.setProgress(p);		
		int rest = 100 / (choices.size() - 1);
		if(p % rest == 0)
			disableChoice();
	}
	
	public void disableItem(Choice c) {
		c.setDisabled(true);
		refreshListView();
	}
	
	public void refreshListView() {
		activity.get().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				notifyDataSetChanged();
			}
		});
	}
	
	private void disableChoice() {
		ArrayList<Choice> tmp = new ArrayList<Choice>();
		for(Choice c: choices)
			if(c.getState() == 0)
				tmp.add(c);
		int random = (int) (Math.random() * tmp.size());
		disableItem(tmp.get(random));
	}

	public void fetchQuestions() {
		QuestionDAO dao = (QuestionDAO) DAOFactory.create(DAOFactory.QUESTION);
		dao.open();
		questions = dao.getBySpecialityId(specialityId, maxQuestion);
		dao.close();
	}
	
	public Question getCurrentQuestion() {
		return questions.get(currentQuestionNumber);
	}
	
	public void fetchChoices() {
		ChoiceDAO dao = (ChoiceDAO) DAOFactory.create(DAOFactory.CHOICE);
		dao.open();
		Log.i("Specid", specialityId+"");
		Log.i("CurrentQuestion", currentQuestionNumber+"");
		Log.i("Question", questions.size() + "");
		choices = dao.getByQuestionId(questions.get(currentQuestionNumber)
			.getId());
		dao.close();
	}
	
	public void nextQuestion() {
		currentQuestionNumber++;
		task = new QuestionTaskManager(this);
		if(currentQuestionNumber < maxQuestion) {
			finished = false;
			index.clear();
			fetchChoices();
			refreshListView();
			this.run();
		} else {
			//TODO score here
		}
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
		if(finished) {
			if(choices.get(arg0).getState() == 1)
				view.setBackgroundResource(R.drawable.choice_shape_true);
			else {
				view.setBackgroundResource(R.drawable.choice_shape_false);
				view.setEnabled(false);
				view.setClickable(false);
			}
		} else {
			if(!choices.get(arg0).isDisabled()) {
				view.setBackgroundResource(R.drawable.textshape);
			} else {
				view.setBackgroundResource(R.drawable.choice_shape_false);
				view.setEnabled(false);
				view.setClickable(false);
			}
		}
		view.setText(choices.get(arg0).getChoice());
		return view;
	}

	public int choiceRemaining() {
		int x = 0;
		for(Choice c: choices)
			if(!c.isDisabled())
				x++;
		return x;
	}
	
	public void onClickItem(int idx, View v) {
		if(!index.contains(idx)) {
			index.add(idx);
			if(choices.get(idx).getState() == 0) {
				disableItem(choices.get(idx));
				task.setCounter((choiceRemaining() - 1) * 100 / (choices.size() - 1));
			}
		}	
	}
}

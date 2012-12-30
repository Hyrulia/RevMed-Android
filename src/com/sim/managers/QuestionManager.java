package com.sim.managers;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.sim.dao.ChoiceDAO;
import com.sim.dao.QuestionDAO;
import com.sim.entities.Choice;
import com.sim.entities.Question;
import com.sim.evamedic.MyApp;
import com.sim.evamedic.QuestionActivity;
import com.sim.evamedic.R;
import com.sim.evamedic.RevisionActivity;
import com.sim.pattern.DAOFactory;
import com.sim.storage.LocalStorage;


/**
 * TODO LIST
 * Style & theme (onFocus/onSelect, layouts, ...)
 * Score
 * More cleaning code
 * Sound & effects
 * @author Nader
 *
 */

public class QuestionManager extends BaseAdapter {
	
	private int currentQuestionNumber = 0;
	private int maxQuestion = 3;
	private int specialityId = 1;
	private List<Question> questions;
	private List<Choice> choices;
	private WeakReference<QuestionActivity> activity;
	private boolean finished = false;
	private int counter;
	

	/**
	 * Constructor of the manager
	 * @param specialityId
	 * @param activity
	 */
	public QuestionManager(int specialityId, QuestionActivity activity) {
		this.specialityId = specialityId;
		this.activity = new WeakReference<QuestionActivity>(activity);
		fetchQuestions();
		fetchChoices();
		this.activity.get().runTask();
	}
	
	
	/**
	 * Update de progressbar depending of nombre of questions answered
	 * Choices will be disabled seconds left%choices
	 * @param p Current progress
	 */
	public void update(int p) {
		setCounter(p);
		activity.get().updateProgress(p);
		int rest = 100 / (choices.size() - 1);
		if(p % rest == 0)
			disableRandomChoice();
	}
	
	/**
	 * Disable a wrong choice and refresh the list
	 * @param c The choice to disable
	 */
	public void disableItem(Choice c) {
		c.setDisabled(true);
		activity.get().refreshList();
	}

	/**
	 * Disable random wrong choice
	 */
	private void disableRandomChoice() {
		ArrayList<Choice> tmp = new ArrayList<Choice>();
		for(Choice c: choices)
			if(c.getState() == 0)
				tmp.add(c);
		int random = (int) (Math.random() * tmp.size());
		disableItem(tmp.get(random));
	}

	/**
	 * Fetch maxQuestion questions from the database depending of the speciality
	 * id
	 */
	public void fetchQuestions() {
		QuestionDAO dao = (QuestionDAO) DAOFactory.create(DAOFactory.QUESTION);
		dao.open();
		questions = dao.getBySpecialityId(specialityId, maxQuestion);
		dao.close();
	}
	
	/**
	 * Get the current question
	 * @return Question
	 */
	public Question getCurrentQuestion() {
		return questions.get(currentQuestionNumber);
	}
	
	/**
	 * Fetch all choices from database by question id
	 */
	public void fetchChoices() {
		((TextView) activity.get().findViewById(R.id.questionView))
			.setText(getCurrentQuestion().getQuestion());
		ChoiceDAO dao = (ChoiceDAO) DAOFactory.create(DAOFactory.CHOICE);
		dao.open();
		choices = dao.getByQuestionId(questions.get(currentQuestionNumber)
			.getId());
		dao.close();
	}
	/**
	 * Start new activity Revision
	 */
	public void ShowRevision() {
		Intent intent = new Intent(activity.get(), RevisionActivity.class);
		intent.putExtra("question", getCurrentQuestion().getQuestion());
		intent.putExtra("choice", getCorrectChoice().getChoice());
		intent.putExtra("questionId", getCurrentQuestion().getId());
		activity.get().startRevisionActivity(intent);
	}
	
	/**
	 * 
	 * @return The correct choice
	 */
	public Choice getCorrectChoice() {
		for(Choice c: choices)
			if(c.getState() == 1)
				return c;
		return null;
	}
	
	/**
	 * Go to the next question
	 */
	public void nextQuestion() {
		currentQuestionNumber++;
		if(currentQuestionNumber < maxQuestion) {
			finished = false;
			fetchChoices();
			activity.get().newTask();
			activity.get().runTask();
			activity.get().refreshList();
		} else {
			//TODO score here
		}
	}

	/**
	 * Save states of the manager when the activity is paused
	 */
	public void save() {
		if(!isFinished()) {
			Log.i("save", "y");
			LocalStorage.setInt("counter", getCounter());
		}
	}
	
	/**
	 * Load states
	 */
	public void load() {
		if(!isFinished()) {
			Log.i("load", "y");
			activity.get().newTask(LocalStorage.getInt("counter", 100));
			activity.get().runTask();
		}
	}
	
	/**
	 * 
	 * @return the number of wrong choices left
	 */
	public int choiceRemaining() {
		int x = 0;
		for(Choice c: choices)
			if(!c.isDisabled())
				x++;
		return x;
	}
	
	/**
	 * When a choice has been clicked
	 * @param idx The position of the choice
	 */
	public void onClickItem(int idx) {
		Log.i("index", idx+"");
		if(choices.get(idx).getState() == 0) {
			Log.i("state", " == 0");
			disableItem(choices.get(idx));
			setCounter((choiceRemaining() - 1) * 100 / (choices.size() - 1));
			Sound.wrong();
		} else {
			Log.i("state", " != 0");
			Sound.correct();
			setFinished(true);
		}
	}
	
	public void onTaskFinished() {
		setFinished(true);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				ShowRevision();
			}
		}, 3000);		
	}
		
	/*
	 *  Adapter overrided functions starts
	 */
	
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
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) MyApp.getContext()
				.getSystemService("layout_inflater");
		Button view = (Button) inflater.inflate(R.layout.choice_layout, null);
		if(finished) {
			if(choices.get(arg0).getState() == 1)
				view.setBackgroundResource(R.drawable.choice_shape_true);
			else 
				view.setBackgroundResource(R.drawable.choice_shape_false);
				view.setEnabled(false);
		} else
			if(choices.get(arg0).isDisabled()) {
				view.setBackgroundResource(R.drawable.choice_shape_false);
				view.setEnabled(false);
			}
			else
				view.setBackgroundResource(R.drawable.button_choice_selector);
		view.setText(choices.get(arg0).getChoice());
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickItem(arg0);
				
			}
		});
		return view;
	}
	
	/*
	 * Adapter overrided function ends.
	 */

	
	/*
	 * Getters and Setters here
	 */
	
	public boolean isFinished() {
		return finished;
	}

	
	public void setFinished(boolean finished) {
		activity.get().refreshList();
		this.finished = finished;
	}


	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	
}

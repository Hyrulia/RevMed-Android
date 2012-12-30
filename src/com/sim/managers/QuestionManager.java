package com.sim.managers;

import java.lang.ref.WeakReference;
import java.util.List;
import android.content.Intent;
import android.os.Handler;
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
import com.sim.evamedic.Sound;
import com.sim.pattern.DAOFactory;


/**
 * TODO LIST
 * New question design
 * Add button validate
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
	private int objectiveId = 1;
	private List<Question> questions;
	private List<Choice> choices;
	private WeakReference<QuestionActivity> activity;
	private boolean finished = false;
	

	/**
	 * Constructor of the manager
	 * @param specialityId
	 * @param activity
	 */
	public QuestionManager(int objectiveId, QuestionActivity activity) {
		this.objectiveId = objectiveId;
		this.activity = new WeakReference<QuestionActivity>(activity);
		fetchQuestions();
		fetchChoices();
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
	 * Fetch maxQuestion questions from the database depending of the speciality
	 * id
	 */
	public void fetchQuestions() {
		QuestionDAO dao = (QuestionDAO) DAOFactory.create(DAOFactory.QUESTION);
		dao.open();
		questions = dao.getByObjectiveId(objectiveId, maxQuestion);
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
			activity.get().refreshList();
		} else {
			//TODO score here
		}
	}

	/**
	 * Save states of the manager when the activity is paused
	 */
	public void save() {
	
	}
	
	/**
	 * Load states
	 */
	public void load() {

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
		disableItem(choices.get(idx));
		if(choices.get(idx).getState() == 0) {
			Log.i("state", " == 0");
			Sound.wrong();
		} else {
			Log.i("state", " == 1");
			Sound.correct();
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
	public View getView(final int position, View arg1, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) MyApp.getContext()
				.getSystemService("layout_inflater");
		Button view = (Button) inflater.inflate(R.layout.choice_layout, null);
		
			if(choices.get(position).isDisabled()) {
				if(choices.get(position).getState() == 1)
					view.setBackgroundResource(R.drawable.choice_shape_true);
				else
					view.setBackgroundResource(R.drawable.choice_shape_false);
			} else 			
				view.setBackgroundResource(R.drawable.button_choice_selector);
			
		view.setText(choices.get(position).getChoice());
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickItem(position);				
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
		this.finished = finished;
	}

	
	
}

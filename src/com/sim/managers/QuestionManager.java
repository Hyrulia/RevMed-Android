package com.sim.managers;

import java.lang.ref.WeakReference;
import java.util.List;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
 * Apply the new theme
 * Comment the code
 * Score chart
 * Social network
 * @author Nader
 *
 */

public class QuestionManager extends BaseAdapter {
	
	protected int currentQuestionNumber = 0;
	protected int maxQuestion = 0;
	protected int objectiveId = 1;
	protected List<Question> questions;
	protected List<Choice> choices;
	protected WeakReference<QuestionActivity> activity;
	protected boolean validate = false;
	protected float score = 0;
	
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	LayoutInflater inflater = (LayoutInflater) MyApp.getContext()
			.getSystemService("layout_inflater");

	/**
	 * Constructor of the manager
	 * @param specialityId
	 * @param activity
	 */
	public QuestionManager(int objectiveId, int max,QuestionActivity activity) {
		this.objectiveId = objectiveId;
		this.activity = new WeakReference<QuestionActivity>(activity);
		setMaxQuestion(max);
		fetchQuestions();
		fetchChoices();
		this.activity.get().refreshQuestion(getCurrentQuestion().getQuestion());
	}
	
	public int getCurrentQuestionNumber() {
		return currentQuestionNumber;
	}

	public void setCurrentQuestionNumber(int currentQuestionNumber) {
		this.currentQuestionNumber = currentQuestionNumber;
	}

	public int getObjectiveId() {
		return objectiveId;
	}

	public void setObjectiveId(int objectiveId) {
		this.objectiveId = objectiveId;
	}

	/**
	 * Disable a wrong choice and refresh the list
	 * @param c The choice to disable
	 */
	public void disableItem(Choice c) {
		c.setChecked(true);
		activity.get().refreshList();
	}
	
	public boolean finished() {
		return !(currentQuestionNumber < maxQuestion);
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
		ChoiceDAO dao = (ChoiceDAO) DAOFactory.create(DAOFactory.CHOICE);
		dao.open();
		choices = dao.getByQuestionId(questions.get(currentQuestionNumber)
			.getId());
		dao.close();
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
			validate = false;
			activity.get().refreshQuestion(getCurrentQuestion().getQuestion());
			activity.get().updateTitle();
			fetchChoices();
				activity.get().refreshList();
		} else { 
			if(activity.get().getMode() == 0)
				activity.get().SaveScore(score);
			else {
				activity.get().finish();
			}
				
		}
	}
	
	public void calculateScore() {
		int totalCorrect = correctChoices();
		int correct = 0;
		int wrong = 0;
		for(Choice c: choices) {
			if(c.isChecked())
				if(c.getState() == 1)
					correct++;
				else
					wrong++;			
		}
		score += (float)( correct * (1 / (float) totalCorrect) ) * ( 1 / (float)(wrong + 1) );
	}
	
	
	public void validate() {
		validate = true;
		calculateScore();
		activity.get().updateScore(score);
	}
	
	/**
	 * When a choice has been clicked
	 * @param idx The position of the choice
	 */
	public void onClickItem(int idx) {
		if(!choices.get(idx).isChecked()) {
		Sound.correct();
		disableItem(choices.get(idx));
		} 
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
	
	public int correctChoices() {
		int s = 0;
		for(Choice c: choices)
			if(c.getState() == 1)
				s++;
		return s;
	}
	
	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		
		View view = inflater.inflate(R.layout.item_choice, null);
		Button choiceButton = (Button) view.findViewById(R.id.choiceButton);
		
		if(validate) {
			ImageView choiceState = (ImageView) view.findViewById(R.id.choiceState);
			choiceState.setVisibility(View.VISIBLE);
			
			if(choices.get(position).isChecked()) 
				if(choices.get(position).getState() == 1) {
					choiceButton.setBackgroundResource(R.drawable.choice_shape_true);
					choiceState.setImageResource(R.drawable.img_correct);
				} else {
					choiceButton.setBackgroundResource(R.drawable.choice_shape_false);
					choiceState.setImageResource(R.drawable.img_wrong);
				}
			else
				if(choices.get(position).getState() == 1) {
					choiceButton.setBackgroundResource(R.drawable.choice_shape_true);
					choiceState.setImageResource(R.drawable.img_wrong);
				}
				choiceButton.setEnabled(false);	
		}
		else
			if(choices.get(position).isChecked())
				choiceButton.setBackgroundResource(R.drawable.choice_shape_checked);
		
			
		choiceButton.setText(choices.get(position).getChoice());
		choiceButton.setOnClickListener(new OnClickListener() {
			
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
	
	public int getMaxQuestion() {
		return maxQuestion;
	}

	public void setMaxQuestion(int maxQuestion) {
		this.maxQuestion = maxQuestion;
	}

	
	public boolean isValidate() {
		return validate;
	}
	
	
}

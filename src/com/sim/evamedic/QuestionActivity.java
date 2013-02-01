package com.sim.evamedic;

import java.lang.ref.WeakReference;
import com.sim.entities.Score;
import com.sim.managers.QuestionManager;
import com.sim.managers.ScoreManager;
import com.sim.storage.LocalStorage;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings.System;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {

	private QuestionManager manager;
	private ShakeSensor sensor;
	private TextView score;
	private TextView timer;
	private TextView question;
	private Button validate;
	private TextView revision;
	private int mode = 0;
	private int specId = 0;
	private TimerTask task;
	private boolean finished = false;
	private String objective;
	private String speciality;
	private int counter = 100;
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		question = (TextView) findViewById(R.id.questionView);
		mode = getIntent().getIntExtra("mode", 0);	
		sensor = new ShakeSensor(this);
		
		specId = getIntent().getIntExtra("specId", 1);
		objective = getIntent().getStringExtra("objective");
		speciality = getIntent().getStringExtra("speciality");
		
		ListView choiceList = (ListView) findViewById(R.id.choicesList);
		validate = (Button) findViewById(R.id.validateButton);
		revision = (Button) findViewById(R.id.revisionButton);
		score = (TextView) findViewById(R.id.ScoreText);
		timer = (TextView) findViewById(R.id.timerText);
		manager = new QuestionManager(
				getIntent().getIntExtra("objId", 1),
				getIntent().getIntExtra("nbQuestion", 5), 
				this);
		
		if(mode == 0) {
			counter = 180 * getIntent().getIntExtra("nbQuestion", 5) / 5; 
			task = new TimerTask(this, counter);
			task.execute();
		} else {
			timer.setVisibility(View.INVISIBLE);
			score.setVisibility(View.INVISIBLE);
			
		}
		setTitle("Question N°" + (manager.getCurrentQuestionNumber() + 1));
		choiceList.setAdapter(manager);
		validate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.validate();
				refreshList();
				sensor.start();
				validate.setVisibility(View.INVISIBLE);
				revision.setVisibility(View.VISIBLE);
			}
		});
		
		revision.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sensor.pause();
				ShowRevision();
			}
		});
	}

	@Override
	public void onBackPressed() {
		AlertDialog dialog = new AlertDialog.Builder(this)
		.setTitle("Evamedic")
		.setMessage("Retourner au Menu?")
		.setPositiveButton("Oui", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				QuestionActivity.super.onBackPressed();
				finish();
			}
		})
		.setNegativeButton("Non", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		})
		.create();		
		dialog.show();
	}
	
	public void timeIsUp() {
		if(!manager.finished()) {
			Toast.makeText(this, "Fin du temps!", Toast.LENGTH_LONG).show();
			SaveScore(manager.getScore());
		}
	}

	@Override
	protected void onStop() {
		if(mode == 0) {
			finished = true;
			if(task != null) {
				task.cancel(true);
			}
		}
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		if(mode == 0) {
			finished = false;
			task = new TimerTask(this, counter);
			task.execute();
		}
		super.onStart();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 666 && resultCode == 999) {
			validate.setVisibility(View.VISIBLE);
			revision.setVisibility(View.INVISIBLE);
			manager.nextQuestion();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void refreshQuestion(String question) {
		if(this.question != null)
		this.question.setText(question);
	}
	
	@Override
	protected void onPause() {
		sensor.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if(manager.isValidate())
			sensor.start();
		super.onResume();
	}
	
	public void ShowRevision() {
		Intent intent = new Intent(this, RevisionActivity.class);
		intent.putExtra("question", manager.getCurrentQuestion().getQuestion());
		intent.putExtra("choice", manager.getCorrectChoice().getChoice());
		intent.putExtra("questionId", manager.getCurrentQuestion().getId());
		intent.putExtra("specId", specId);
		startRevisionActivity(intent);
	}
	
	public void onShake() {
		ShowRevision();
	}

	public void refreshList() {
		manager.notifyDataSetChanged();
	}
	
	public void startRevisionActivity(Intent intent) {
		startActivityForResult(intent, 666);
	}
	
	public void SaveScore(final float score) {
		
		if(mode == 0) {
			finished = true;
			task.cancel(true);
		}
		
		final EditText text = new EditText(this);
		text.setText("Votre pseudo ici");
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(text.getText().toString().equals("Votre pseudo ici"))
					text.setText("");
			}
		});
		final AlertDialog dialog = new AlertDialog.Builder(this)
		.setPositiveButton("Save", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialogx, int which) {
				doSaveScore(text.getText().toString(), score);
				finish();
			}
		})
		.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		})
		.setNeutralButton("Save & Share", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent sharingIntent = new Intent(Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
					"Je viens de faire un nouveau score de " + 
					String.format("%.2f", score) + " en " + objective + " de la " +
					"specialité " + speciality);
				startActivity(Intent.createChooser(sharingIntent, "Patager avec"));
				doSaveScore(text.getText().toString(), score);
				finish();
			}
		})
		.setTitle("Voulez-vous enregister votre score?")
		.setView(text)
		.create();
		
		dialog.show();
	}
	
	private void doSaveScore(String pseudo, float score) {
		Score s = new Score();
		s.setScore(score);
		s.setPseudo(pseudo);
		s.setObjectiveId(manager.getObjectiveId());
		ScoreManager.Save(s);
	}
			
	public void updateTimer(int timer) {
		counter = timer;
		Log.i("timer", counter+"");
		int seconde = timer % 60;
		int minute = timer / 60;
		this.timer.setText("Temps restant: " + String.format("%02d", minute) + 
				":" + String.format("%02d", seconde));
	}
	
	@SuppressLint("DefaultLocale")
	public void updateScore(float score) {
		String s = String.format("%.2f", score);
		this.score.setText("Score: " + s);
	}

	public void updateTitle() {
		setTitle("Question N°" + (manager.getCurrentQuestionNumber() + 1));
		
	}
		
	public boolean isFinished() {
		return finished;
	}
	public static class TimerTask extends AsyncTask<Void, Integer, Boolean> {

		private WeakReference<QuestionActivity> activity;
		private int counter;
		
		public TimerTask(QuestionActivity a, int c) {
			activity = new WeakReference<QuestionActivity>(a);
			counter = c;
		}
	
		@Override
		protected Boolean doInBackground(Void... params) {
			
			while(activity.get() != null && !activity.get().isFinished() && !isCancelled()) {
				counter--;
				publishProgress(counter);
				if(counter <= 0)
					break;
				SystemClock.sleep(1000);
			}
			return !activity.get().isFinished();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			if(activity.get() != null)
				activity.get().updateTimer(counter);
			super.onProgressUpdate(values);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			if(result && activity.get() != null)
				activity.get().timeIsUp();
			super.onPostExecute(result);
		}
	}


}

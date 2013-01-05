package com.sim.evamedic;

import java.lang.ref.WeakReference;

import com.sim.entities.Score;
import com.sim.managers.ExamManager;
import com.sim.managers.RevisionManager;
import com.sim.managers.ScoreManager;
import com.sim.storage.LocalStorage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	private RevisionManager manager;
	private ShakeSensor sensor;
	private TextView score;
	private TextView timer;
	private TextView question;
	private Button validate;
	private TextView revision;
	private int mode = 0;
	private TimerTask task;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		question = (TextView) findViewById(R.id.questionView);
		mode = getIntent().getIntExtra("mode", 0);	
		sensor = new ShakeSensor(this);
		
		ListView choiceList = (ListView) findViewById(R.id.choicesList);
		validate = (Button) findViewById(R.id.validateButton);
		revision = (Button) findViewById(R.id.revisionButton);
		score = (TextView) findViewById(R.id.ScoreText);
		timer = (TextView) findViewById(R.id.timerText);
		
		if(mode == 0) {
			manager = new ExamManager(
					getIntent().getIntExtra("objId", 1),
					getIntent().getIntExtra("nbQuestion", 5), 
					this);
			task = new TimerTask(this, getIntent().getIntExtra("nbQuestion", 5));
			task.execute();
			
		} else {
			manager = new RevisionManager(
					getIntent().getIntExtra("objId", 1),
					getIntent().getIntExtra("nbQuestion", 5), 
					this);
			score.setVisibility(View.INVISIBLE);
			timer.setVisibility(View.VISIBLE);
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
				manager.ShowRevision();
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
	
	public void validateExam() {
		manager.validate();
		refreshList();
		sensor.start();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onStop() {
		manager.save();
		if(task != null) {
			LocalStorage.setInt("counter", task.getCounter());
			task.cancel(true);
		}
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		manager.load();
		if(mode == 0) {
			task = new TimerTask(this, manager.getMaxQuestion());
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
	
	public void onShake() {
		manager.ShowRevision();
	}

	public void refreshList() {
		manager.notifyDataSetChanged();
	}
	
	public void startRevisionActivity(Intent intent) {
		startActivityForResult(intent, 666);
	}
	
	public void SaveScore(final float score) {
		final EditText text = new EditText(this);
		text.setText("Votre pseudo ici");
		text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(text.getText().toString().equals("Votre pseudo ici"))
					text.setText("");
			}
		});
		AlertDialog dialog = new AlertDialog.Builder(this)
		.setPositiveButton("Ok", new AlertDialog.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Score s = new Score();
				s.setScore(score);
				s.setPseudo(text.getText().toString());
				s.setObjectiveId(manager.getObjectiveId());
				ScoreManager.Save(s);
			}
		})
		.setNegativeButton("Cancel", new AlertDialog.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				startActivity(new Intent(QuestionActivity.this,
						MenuActivity.class));
			}
		})
		.setTitle("Voulez-vous enregister votre score?")
		.setView(text)
		.create();
		
		dialog.show();
	}
			
	public void updateTimer(int timer) {
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
	
	public static class TimerTask extends AsyncTask<Void, Integer, Boolean> {

		private WeakReference<QuestionActivity> activity;
		private int counter = 100;
		
		public TimerTask(QuestionActivity a, int questionNb) {
			activity = new WeakReference<QuestionActivity>(a);
			counter = 100 * questionNb / 5; 
		}
		
		
		public int getCounter() {
			return counter;
		}


		public void setCounter(int counter) {
			this.counter = counter;
		}


		@Override
		protected Boolean doInBackground(Void... params) {
			while(counter > 0 && !isCancelled()) {
				counter--;
				publishProgress(counter);
				SystemClock.sleep(1000);
			}
			return true;
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
				activity.get().validateExam();
			super.onPostExecute(result);
		}

		


	}

}

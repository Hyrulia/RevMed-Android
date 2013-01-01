package com.sim.evamedic;

import com.sim.entities.Score;
import com.sim.managers.QuestionManager;
import com.sim.managers.ScoreManager;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	QuestionManager manager;
	ProgressBar bar;
	TextView score;
	TextView question;
	private View validate;
	private View revision;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		question = (TextView) findViewById(R.id.questionView);
		manager = new QuestionManager(
				getIntent().getIntExtra("objId", 1),
				getIntent().getIntExtra("nbQuestion", 5), 
				this);
		setTitle("Question N°" + (manager.getCurrentQuestionNumber() + 1));
		ListView choiceList = (ListView) findViewById(R.id.choicesList);
		validate = (Button) findViewById(R.id.validateButton);
		revision = (Button) findViewById(R.id.revisionButton);
		score = (TextView) findViewById(R.id.ScoreText);
		choiceList.setAdapter(manager);
		validate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				manager.validate();
				refreshList();
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
		
		super.onBackPressed();
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
		super.onStop();
	}
	
	@Override
	protected void onStart() {
		manager.load();
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
						SpecialityActivity.class));
			}
		})
		.setTitle("Voulez-vous enregister votre score?")
		.setView(text)
		.create();
		
		dialog.show();
	}
			
	@SuppressLint("DefaultLocale")
	public void updateScore(float score) {
		String s = String.format("%.2f", score);
		this.score.setText("Score: " + s);
	}

	public void updateTitle() {
		setTitle("Question N°" + (manager.getCurrentQuestionNumber() + 1));
		
	}

}

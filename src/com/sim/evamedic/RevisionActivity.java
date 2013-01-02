package com.sim.evamedic;

import com.sim.dao.RevisionDAO;
import com.sim.entities.Revision;
import com.sim.pattern.DAOFactory;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RevisionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revision);
		setTitle("Revision et Astuces");
		overridePendingTransition(R.anim.stretch,R.anim.shrink);
		TextView revision = (TextView) findViewById(R.id.revisionText);
		Button backBt = (Button) findViewById(R.id.back);
		
		RevisionDAO dao = (RevisionDAO) DAOFactory.create(DAOFactory.REVISION);
		dao.open();
		Revision r = dao.getByQuestionId(getIntent()
			.getIntExtra("questionId", 1));
		dao.close();
		revision.setText(r.getRevision());
		
		backBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(999);
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_revision, menu);
		return true;
	}

}

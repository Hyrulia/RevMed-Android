package com.sim.evamedic;

import com.sim.dao.RevisionDAO;
import com.sim.entities.Revision;
import com.sim.pattern.DAOFactory;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RevisionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getIntExtra("mode", 0) == 0) {
			setResult(999);
			finish();
		}
		setContentView(R.layout.activity_revision);
		int[] bg = new int[]{R.drawable.revision_1, R.drawable.revision_2, 
				R.drawable.revision_3, R.drawable.revision_4, R.drawable.revision_5, 
				R.drawable.revision_6, R.drawable.revision_7 }; 
		setTitle("Revision et Astuces");
		overridePendingTransition(R.anim.stretch,R.anim.shrink);
		TextView revision = (TextView) findViewById(R.id.revisionText);
		Button backBt = (Button) findViewById(R.id.revisionBack);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.revisionLayout);
		
		int specId = getIntent().getIntExtra("specId", 1);
		layout.setBackgroundResource(bg[specId - 1]);
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
	
}

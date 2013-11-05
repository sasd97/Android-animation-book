package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SubActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.animator.slide_in_left, R.animator.slide_out_right);
	}
}

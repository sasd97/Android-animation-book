package com.example.animationdemos;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class XMLParserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xmlparser);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xmlparser, menu);
		return true;
	}

}

package com.example.animationdemos;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SendDataActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_data);
		
		final Button txtBtn = (Button) findViewById(R.id.sendTxtBtn);
		final Button binBtn = (Button) findViewById(R.id.sendBinBtn);
		final Button multBtn = (Button) findViewById(R.id.sendMultBtn);
		txtBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setAction(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_TEXT, "This is my text to send");
				i.setType("text/plain");
				startActivity(Intent.createChooser(i, "Share text to"));
			}
		});
		binBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri uriImg = Uri.parse("@drawable/a.png");
				Intent i = new Intent();
				i.setAction(Intent.ACTION_SEND);
				i.putExtra(Intent.EXTRA_STREAM, uriImg);
				i.setType("image/jpeg");
				startActivity(Intent.createChooser(i, "Share Picture to"));
			}
		});		
		multBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ArrayList<Uri> imgUris = new ArrayList<Uri>();
				Uri imgA = Uri.parse("@drawable/a.png");
				Uri imgB = Uri.parse("@drawable/b.png");
				imgUris.add(imgA);
				imgUris.add(imgB);
				
				Intent i = new Intent();
				i.setAction(Intent.ACTION_GET_CONTENT);
				i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUris);
				i.setType("image/*");
				startActivity(Intent.createChooser(i, "Share Picture to"));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_data, menu);
		return true;
	}

}

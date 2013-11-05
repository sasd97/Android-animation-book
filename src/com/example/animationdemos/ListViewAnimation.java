package com.example.animationdemos;

import java.util.ArrayList;
import java.util.List;

import com.example.animationdemos.widget.StableArrayAdapter;

import android.os.Bundle;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewAnimation extends Activity {
	static final String[] sCheeseStrings = { "dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf","dsf", "sd", "sddddddddff",
			"adaf", "ddaff","sdf" };
	static final String TAG = "ListViewAnimation";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_animation);
		
		final CheckBox vpaCB = (CheckBox) findViewById(R.id.vpaCB);
		final CheckBox setTransientStateCB = (CheckBox) findViewById(R.id.setTransientStateCB);
		final ListView listView = (ListView) findViewById(R.id.listView);
		
		final ArrayList<String> cheeseList = new ArrayList<String>();
		for (int i = 0; i < sCheeseStrings.length; i++) {
			cheeseList.add(sCheeseStrings[i]);
		}
		
		Log.d(TAG, "ListView is " + listView.toString());

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				cheeseList);
		/*
		 * final StableArrayAdapter adapter = new StableArrayAdapter(this,
		 * android.R.layout.simple_list_item_1, cheeseList);
		 */
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				if (vpaCB.isChecked()) {

					view.animate().setDuration(1000).alpha(0)
							.withEndAction(new Runnable() {

								@Override
								public void run() {
									cheeseList.remove(item);
									adapter.notifyDataSetChanged();
									// display
									view.setAlpha(1);
								}
							});
				} else {

					ObjectAnimator anim = ObjectAnimator.ofFloat(view,
							view.ALPHA, 0);
					anim.setDuration(1000);
					if (setTransientStateCB.isChecked()) {
						view.setHasTransientState(true);
					}
					anim.addListener(new AnimatorListener() {

						@Override
						public void onAnimationEnd(Animator animation) {
							cheeseList.remove(item);
							adapter.notifyDataSetChanged();
							view.setAlpha(1);
							if (setTransientStateCB.isChecked()) {
								view.setHasTransientState(false);
							}
						}

						@Override
						public void onAnimationCancel(Animator animation) {

						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							
						}

						@Override
						public void onAnimationStart(Animator animation) {

						}

					});
					anim.start();
				}
			}
		});

	}
}

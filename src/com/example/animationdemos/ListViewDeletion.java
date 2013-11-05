package com.example.animationdemos;

import java.util.ArrayList;
import com.example.animationdemos.widget.StableArrayAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewDeletion extends Activity {
	static final String[] sCheeseStrings = { "dsf", "sd", "sddddddddff",
			"adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf",
			"ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf", "ddaff", "sdf",
			"dsf", "sd", "sddddddddff", "adaf", "ddaff", "sdf", "dsf", "sd",
			"sddddddddff", "adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff",
			"adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf",
			"ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf", "ddaff", "sdf",
			"dsf", "sd", "sddddddddff", "adaf", "ddaff", "sdf", "dsf", "sd",
			"sddddddddff", "adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff",
			"adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf",
			"ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf", "ddaff", "sdf",
			"dsf", "sd", "sddddddddff", "adaf", "ddaff", "sdf", "dsf", "sd",
			"sddddddddff", "adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff",
			"adaf", "ddaff", "sdf", "dsf", "sd", "sddddddddff", "adaf",
			"ddaff", "sdf" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_deletion);

		final CheckBox usePositionCB = (CheckBox) findViewById(R.id.usePositionCB);
		final Button deleteButton = (Button) findViewById(R.id.delete);
		final ListView listView = (ListView) findViewById(R.id.listview);
		final TextView total = (TextView) findViewById(R.id.total);

		final ArrayList<String> cheeses = new ArrayList<String>();
		for (int i = 0; i < sCheeseStrings.length; i++) {
			cheeses.add(sCheeseStrings[i]);
		}

		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_multiple_choice,
				android.R.id.text1, cheeses);
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		total.setText("Total is " + adapter.getCount());

		deleteButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SparseBooleanArray checkedItems = listView
						.getCheckedItemPositions();
				int numCheckedItems = checkedItems.size();

				for (int i = numCheckedItems - 1; i >= 0; i--) {
					if (!checkedItems.valueAt(i)) {
						continue;
					}
					int position = checkedItems.keyAt(i);
					final String item = adapter.getItem(position);

					if (!usePositionCB.isChecked()) {
						final View view = listView.getChildAt(position);
						view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
							
							@Override
							public void run() {
								view.setAlpha(1);
								adapter.remove(item);
							}
						});
						/*v.postDelayed(new Runnable() {

							@Override
							public void run() {
								adapter.remove(item);
							}
						}, 300);*/
					} else {
						int positionOnScreen = position
								- listView.getFirstVisiblePosition();
						if (positionOnScreen >= 0
								&& positionOnScreen < listView.getChildCount()) {
							final View view = listView
									.getChildAt(positionOnScreen);
							view.animate().setDuration(1000).alpha(0)
									.withEndAction(new Runnable() {

										@Override
										public void run() {
											view.setAlpha(1);
											adapter.remove(item);
										}
									});
						} else {
							v.postDelayed(new Runnable() {

								@Override
								public void run() {
									adapter.remove(item);
								}
							}, 300);
						}
					}
				}
				adapter.notifyDataSetChanged();
				total.setText("Total is " + adapter.getCount());

			}
		});
	}
}

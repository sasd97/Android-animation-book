package com.example.animationdemos.widget;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class SweepStableArrayAdapter extends ArrayAdapter<String> {

	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
	View.OnTouchListener mOnTouchListener;

	public SweepStableArrayAdapter(Context context, int textViewResourceId,
			List<String> objects, View.OnTouchListener listener) {
		super(context, textViewResourceId, objects);
		mOnTouchListener = listener;
		for (int i = 0; i < objects.size(); i++) {
			mIdMap.put(objects.get(i), i);
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		String item = getItem(position);
		return mIdMap.get(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		if (view != convertView) {
			view.setOnTouchListener(mOnTouchListener);
		}
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}

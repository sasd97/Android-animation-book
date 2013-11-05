package com.example.animationdemos.widget;

import java.util.List;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StableArrayAdapter extends ArrayAdapter<String> {
	
	Context context;
	int resource;
	int textViewId;
	List<String> lists;
	LayoutInflater inflater;

	public StableArrayAdapter(Context context, int resource, int textViewId,
			List<String> lists) {
		super(context, resource, lists);
		this.context = context;
		this.resource = resource;
		this.textViewId = textViewId;
		this.lists = lists;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		String text = lists.get(position);
		View view = (convertView != null) ? convertView : new View(context);
		view = inflater.inflate(resource, null);
		TextView textView = (TextView) view
				.findViewById(textViewId);
		textView.setText(text);
		return view;
	}

	@Override
	public int getCount() {
		return lists.size();
	}
}

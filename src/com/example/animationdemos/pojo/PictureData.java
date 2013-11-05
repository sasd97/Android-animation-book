package com.example.animationdemos.pojo;

import android.graphics.Bitmap;

public class PictureData {
	private int resourceId;
	private String description;
	private Bitmap thumbnail;
	
	public PictureData(int resourceId, String description, Bitmap thumbnail) {
		this.resourceId = resourceId;
		this.description = description;
		this.thumbnail = thumbnail;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}
	
}

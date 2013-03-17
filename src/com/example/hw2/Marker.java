package com.example.hw2;

import android.widget.ImageButton;

public class Marker {
	
	private ImageButton markerImage;
	private String markerDescription;
	
	public Marker(ImageButton markerImage, String markerDescription) {
		super();
		this.markerImage = markerImage;
		this.markerDescription = markerDescription;
	}

	public ImageButton getMarkerImage() {
		return markerImage;
	}

	public void setMarkerImage(ImageButton markerImage) {
		this.markerImage = markerImage;
	}

	public String getMarkerDescription() {
		return markerDescription;
	}

	public void setMarkerDescription(String markerDescription) {
		this.markerDescription = markerDescription;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		Marker other = (Marker) obj;
		
		if (markerDescription == null) 
		{
			if (other.markerDescription != null)
			{
				return false;
			}
		} 
		else if (markerImage == null) 
		{
			if (other.markerImage != null)
			{
				return false;
			}
		} 
		else if (!markerDescription.equals(other.markerDescription))
		{
			return false;
		}
		else if (!markerImage.equals(other.markerImage))
		{
			return false;
		}
		
		return true;
	}
}

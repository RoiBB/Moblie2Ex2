package com.example.hw2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText markerDescription;
	private ImageView map;
	private Marker selectedMarker;
	private List<Marker> mapMarkers = new ArrayList<Marker>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		final RelativeLayout mainView = (RelativeLayout)inflater.inflate(R.layout.activity_main, null);
		
		setContentView(mainView);
		
		markerDescription = (EditText) findViewById(R.id.markerDescription);
		map = (ImageView) findViewById(R.id.imageView1);
		
		
		GestureDetector.OnGestureListener mapGdListener = new GestureDetector.SimpleOnGestureListener() {

			@SuppressLint("NewApi")
			@Override
			public void onLongPress(MotionEvent e) {
				
				getMarkerDescription(mainView, e);
			}
		};
		
		final GestureDetector mapGd = new GestureDetector(this, mapGdListener);
		
		map.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				mapGd.onTouchEvent(event);
				return true;
			}
		});
		
		Button changeButton = (Button) findViewById(R.id.changeButton);
		
		changeButton.setOnClickListener(new View.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				
				if (selectedMarker == null) {
					Toast.makeText(MainActivity.this, "Please select Marker to change", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String newDescription = markerDescription.getText().toString();
				
				if (newDescription.isEmpty()) {
					Toast.makeText(MainActivity.this, "Missing description", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (newDescription.equals(selectedMarker.getMarkerDescription())) {
					Toast.makeText(MainActivity.this, "Same description entered", Toast.LENGTH_SHORT).show();
					return;
				}
				
				selectedMarker.setMarkerDescription(newDescription);
				Toast.makeText(MainActivity.this, "Description has changed to: " + newDescription, Toast.LENGTH_SHORT).show();
			}
		});
		
		Button removeButton = (Button) findViewById(R.id.removeButton);
		
		removeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (selectedMarker == null) {
					Toast.makeText(MainActivity.this, "Please select Marker to delete", Toast.LENGTH_SHORT).show();
					return;
				}
				
				mainView.removeView(selectedMarker.getMarkerImage());
				mapMarkers.remove(selectedMarker);
				markerDescription.setText("");
				
				Toast.makeText(MainActivity.this, "Marker has been removed", Toast.LENGTH_SHORT).show();
				
				selectedMarker = null;
			}
		});
	}
	
	@SuppressLint("NewApi")
	private void getMarkerDescription(final RelativeLayout mainView, final MotionEvent e) {
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Marker Description");
		alert.setMessage("Please enter description:");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		
		public void onClick(DialogInterface dialog, int whichButton) {
			  final String value = input.getText().toString();
			  
			  if (value.isEmpty()) {
				  Toast.makeText(MainActivity.this, "Missing description", Toast.LENGTH_SHORT).show();
				  return;
			  }
			  
			  final ImageButton ib = new ImageButton(MainActivity.this);
		  	  RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			
		   	  ib.setImageResource(R.drawable.marker);
			  ib.setLayoutParams(lp);
			  ib.setX(e.getX() - Float.parseFloat("30"));
			  ib.setY(e.getY() - Float.parseFloat("30"));
			  
			  GestureDetector.OnGestureListener markerDescriptionGdListener = new GestureDetector.SimpleOnGestureListener() {

					@SuppressLint("NewApi")
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						
						for (Marker marker : mapMarkers) {
							if(marker.getMarkerImage().equals(ib)) {
								selectedMarker = marker;
							}
						}
						
						markerDescription.setText(selectedMarker.getMarkerDescription());
						
						return false;
					}
				};
				
				final GestureDetector markerDescriptionGd = new GestureDetector(MainActivity.this, markerDescriptionGdListener);
				
				ib.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						
						markerDescriptionGd.onTouchEvent(event);
						return true;
					}
				});
			  
			  Marker newMarker = new Marker(ib, value);
			  mapMarkers.add(newMarker);
				  
			  mainView.addView(ib);
			  markerDescription.setText(value);
			  selectedMarker = newMarker;
			  
			  Toast.makeText(MainActivity.this, "New marker has been added to map", Toast.LENGTH_SHORT).show();
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    	return;
		  }
		});

		alert.show();
	}

}

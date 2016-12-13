package com.example.project2pathtracker;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import GPSpack.GPStracker;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements LOcationIn
{
	private GoogleMap googleMaps;
	GPStracker gps;
	TextView lat,longt;
	Button btnStopTracing;
	MarkerOptions marker;
	
	double latitude;
	double longitude;
	
	int i=0;
	
	ArrayList<LatLng> routePoints = new ArrayList<LatLng>() ;
	
    @Override
    protected void onCreate(final Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPStracker(MainActivity.this,MainActivity.this);
		lat = (TextView) findViewById(R.id.latText);
		longt = (TextView) findViewById(R.id.longText);
		btnStopTracing = (Button) findViewById(R.id.btnStopTracing);
        
        
        try {
			if (googleMaps == null) 
			{
				googleMaps = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragMapPathTracker)).getMap();
			}
		
			
			// create marker
			
			googleMaps.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			googleMaps.setMyLocationEnabled(true); // false to disable
			
			googleMaps.getUiSettings().setZoomControlsEnabled(true); // tr
			googleMaps.getUiSettings().setZoomGesturesEnabled(true);
			googleMaps.getUiSettings().setCompassEnabled(true);
			googleMaps.getUiSettings().setMyLocationButtonEnabled(true);
			googleMaps.getUiSettings().setRotateGesturesEnabled(true);


			CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(14).build();

			googleMaps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
        
        
        
        
        btnStopTracing.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				googleMaps.clear();
				i=0;
				gps = new GPStracker(MainActivity.this,MainActivity.this);
				//onCreate(savedInstanceState);
			}
		});
        
	}
    
    
    
    

	@Override
	public void locationChange(double[] value)
	{
		// TODO Auto-generated method stub
		lat.setText("latitude "+String.valueOf(value[0]));
		longt.setText("longitude: "+String.valueOf(value[1]));
		
		marker = new MarkerOptions();
		i++;
		
		//String[] value = {}
		
		/*marker.position(new LatLng(15.302316,74.059052));		
		routePoints.add(new LatLng(15.302316,74.059052));
		
		marker.position(new LatLng(15.302399,74.056499));		
		routePoints.add(new LatLng(15.302399, 74.056499));
		
		marker.position(new LatLng(15.304593, 74.050448));		
		routePoints.add(new LatLng(15.304593, 74.050448));
		*/
		
		marker.title("Tracing Marker :" +i);
		marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		googleMaps.addMarker(marker);
		
		
	
		
		Polyline route = googleMaps.addPolyline(new PolylineOptions());
		route.setWidth(5);
		route.setColor(Color.BLUE);
		route.setGeodesic(true);
		route.setPoints(routePoints);
	}
        
        
        
    }


   


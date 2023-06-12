package com.soldis.yourthaitea.journey;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.soldis.yourthaitea.AppConstant;
import com.soldis.yourthaitea.AppController;
import com.soldis.yourthaitea.R;
import com.soldis.yourthaitea.entity.Obj_MOTORIS;
import com.soldis.yourthaitea.entity.Obj_TRX_H;
import com.soldis.yourthaitea.entity.Obj_VISIT;
import com.soldis.yourthaitea.model.Tbl_GPS_Logs;
import com.soldis.yourthaitea.util.GPSTracker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class JourneyActivity extends AppCompatActivity implements OnMapReadyCallback {
	// Google Map
	private GoogleMap mMap;

	Toolbar toolbar;
	TextView txtLabel,
			txtKM,
			txtTrophy,
			txtTimeGo,
			txtTimeEnd
	;
	SupportMapFragment mapFragment;

	Tbl_GPS_Logs tblGpsLogs;

	Obj_MOTORIS objMotoris;

	ImageView imgTrophy1,
			imgTrophy2,
			imgTrophy3,
			imgTrophy4;
	int iEC, iEC_MTD;
	double dTargetEC = 0;
	double dTargetEC_MTD = 0;
	double dTarget = 0;
	double dSales = 0;
	double dSales_MTD = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_journey);
		tblGpsLogs = AppController.getInstance().getSessionManager().getListJourney();

		InitControl();

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		FillForm();
	}

	void InitControl() {
		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		txtLabel = (TextView) findViewById(R.id.textLabel);
		txtKM = (TextView) findViewById(R.id.txtKM);
		txtTrophy = (TextView) findViewById(R.id.txtTrophy);
		txtTimeGo = (TextView) findViewById(R.id.txtTimeGo);
		txtTimeEnd = (TextView) findViewById(R.id.txtTimeEnd);

		imgTrophy1 = (ImageView) findViewById(R.id.img_trophy1);
		imgTrophy2 = (ImageView) findViewById(R.id.img_trophy2);
		imgTrophy3 = (ImageView) findViewById(R.id.img_trophy3);
		imgTrophy4 = (ImageView) findViewById(R.id.img_trophy4);

		txtLabel.setText(getResources().getString(R.string.main_my_journey));

		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

		/*tblGpsLogs = new Tbl_GPS_Logs();
		tblGpsLogs.AddData(tblGpsLogs.new TblGPSLogs( "","", "-6.2459457", "106.6483922"));
		tblGpsLogs.AddData(tblGpsLogs.new TblGPSLogs( "","", "-6.2955955", "106.7060842"));
		tblGpsLogs.AddData(tblGpsLogs.new TblGPSLogs( "","", "-6.1564035", "106.7917289"));
		AppController.getInstance().getSessionManager().setListJourney(null);
		AppController.getInstance().getSessionManager().setListJourney(tblGpsLogs);*/

		Obj_VISIT objVisit = new Obj_VISIT();
		objVisit = objVisit.getData();

		if (objVisit.getTMGO() != null) txtTimeGo.setText(objVisit.getTMGO());
		if (objVisit.getTMBCK() != null) txtTimeEnd.setText(objVisit.getTMBCK());
	}

	void FillForm() {
		int iTrophy = 0;
		objMotoris = new Obj_MOTORIS();
		objMotoris = objMotoris.getData(AppConstant.strSlsNo);

		Obj_TRX_H objTrxH = new Obj_TRX_H();
		iEC = 0;
		dSales = 0;

		for (Obj_TRX_H dat : objTrxH.getDataList()) {
			iEC += 1;
			dSales += dat.getINVAMOUNT();
		}

		iEC_MTD = 0;
		dSales_MTD = 0;
		dTargetEC = 0;
		dTargetEC_MTD = 0;
		if (objMotoris.getSLSNO() != null) {
			dTargetEC_MTD = objMotoris.getTARGET_EC_MTD();
			dTargetEC = objMotoris.getTARGET_EC();
			dTarget = objMotoris.getTARGET_SALES();
			iEC_MTD = objMotoris.getTOTAL_EC();
			dSales_MTD = objMotoris.getTOTAL_SALES();

			if (dSales >= dTarget) {
				iTrophy += 1;
				imgTrophy1.setVisibility(View.VISIBLE);
			}
			if ((dSales + dSales_MTD) >= objMotoris.getTARGET_SALES_MTD()) {
				iTrophy += 1;
				imgTrophy2.setVisibility(View.VISIBLE);
			}
		}

		if (iEC >= dTargetEC) {
			iTrophy += 1;
			imgTrophy3.setVisibility(View.VISIBLE);
		}
		if ((iEC + iEC_MTD) >= dTargetEC_MTD) {
			iTrophy += 1;
			imgTrophy4.setVisibility(View.VISIBLE);
		}
		txtTrophy.setText("0 " + getResources().getString(R.string.main_trophy));
		if (iTrophy > 0)
			txtTrophy.setText(iTrophy + " " + getResources().getString(R.string.main_trophy));
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		// Add a marker in Sydney, Australia, and move the camera.
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		// Enable / Disable zooming controls
		mMap.getUiSettings().setZoomControlsEnabled(false);
		// Enable / Disable my location button
		mMap.getUiSettings().setMyLocationButtonEnabled(true);
		// Enable / Disable Compass icon
		mMap.getUiSettings().setCompassEnabled(true);
		// Enable / Disable Rotate gesture
		mMap.getUiSettings().setRotateGesturesEnabled(true);
		// Enable / Disable zooming functionality
		mMap.getUiSettings().setZoomGesturesEnabled(true);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return;
		}

		double la, lg;
		int iIndex = 0;
		boolean bDone = false;
		LatLng latLngFirt = new LatLng(0,0);
		float distanceInMeters = 0;

		LatLngBounds bounds = null;
		txtKM.setText("0 km");
		if (tblGpsLogs != null){
			if (tblGpsLogs.m_data.size() == 0) mMap.setMyLocationEnabled(true);

			if (tblGpsLogs.m_data.size() > 0){
				for (Tbl_GPS_Logs.TblGPSLogs dat : tblGpsLogs.m_data){

					la = Double.parseDouble(dat.LATITUDE);
					lg = Double.parseDouble(dat.LONGITUDE);

					BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.motor_green);
					MarkerOptions marker = new MarkerOptions().position(
							new LatLng(la,lg)).icon(icon);

					if (iIndex == 0){
						mMap.addMarker(marker).setTitle(getResources().getString(R.string.main_start));
					}else{
						if (dat.CUSTNO != null && !dat.CUSTNO.equals("")){
							icon = BitmapDescriptorFactory.fromResource(R.drawable.store);
							marker = new MarkerOptions().position(
									new LatLng(la,lg)).icon(icon);
							mMap.addMarker(marker).setTitle(dat.CUSTNO);
						}

						DrawArrowHead(mMap,latLngFirt, new LatLng(la,lg));
					}

					if (bDone){
				/*Polyline line = mMap.addPolyline(new PolylineOptions()
						.add(latLngFirt, new LatLng(la,lg)).width(10).color(Color.RED));*/

						DrawRouteMaps.getInstance(this)
								.draw(latLngFirt, new LatLng(la,lg), mMap);
						//DrawMarker.getInstance(this).draw(mMap, latLngFirt, R.drawable.motor_green, "Origin Location");
						//DrawMarker.getInstance(this).draw(mMap, new LatLng(la,lg), R.drawable.motor_red, "Destination Location");

						bounds = new LatLngBounds.Builder()
								.include(latLngFirt)
								.include(new LatLng(la,lg)).build();

						Location loc1 = new Location("");
						loc1.setLatitude(latLngFirt.latitude);
						loc1.setLongitude(latLngFirt.longitude);

						Location loc2 = new Location("");
						loc2.setLatitude(la);
						loc2.setLongitude(lg);
						distanceInMeters += loc1.distanceTo(loc2);
					}
					latLngFirt = new LatLng(la,lg);

					bDone = true;
					iIndex += 1;

					if (iIndex == tblGpsLogs.m_data.size()){
						icon = BitmapDescriptorFactory.fromResource(R.drawable.motor_red);
						marker.icon(icon);
						mMap.addMarker(marker).setTitle(getResources().getString(R.string.main_finish));
					}

				}

				if (bDone) {
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(latLngFirt).zoom(13).build();
					mMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
			/*Point displaySize = new Point();
			getWindowManager().getDefaultDisplay().getSize(displaySize);
			mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 200, 10));*/
					txtKM.setText(AppController.getInstance().toCurrency(distanceInMeters/1000) + " km");
				}
			}
		}else{
			mMap.setMyLocationEnabled(true);
			GPSTracker gps;
			gps = new GPSTracker(this);
			String sLat = "0.0";
			String sLng = "0.0";
			String providerGps = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if(providerGps.contains("gps")) { //if gps is disabled
				if(gps.canGetLocation()){
					la = gps.getLatitude();
					lg = gps.getLongitude();
					sLat = Double.toString(la);
					sLng = Double.toString(lg);
					// \n is for new line
					if (sLng != null && !sLng.equals("0.0") && !sLng.equals("0")){
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(new LatLng(la, lg)).zoom(15).build();
						mMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					}
				}
			}
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.clear();
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				finish();
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private final double degreesPerRadian = 180.0 / Math.PI;

	private void DrawArrowHead(GoogleMap mMap, LatLng from, LatLng to){
		// obtain the bearing between the last two points
		double bearing = GetBearing(from, to);

		// round it to a multiple of 3 and cast out 120s
		double adjBearing = Math.round(bearing / 3) * 3;
		while (adjBearing >= 120) {
			adjBearing -= 120;
		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// Get the corresponding triangle marker from Google
		URL url;
		Bitmap image = null;

		try {
			url = new URL("http://www.google.com/intl/en_ALL/mapfiles/dir_" + String.valueOf((int)adjBearing) + ".png");
			try {
				image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (image != null){

			// Anchor is ratio in range [0..1] so value of 0.5 on x and y will center the marker image on the lat/long
			float anchorX = 0.5f;
			float anchorY = 0.5f;

			int offsetX = 0;
			int offsetY = 0;

			// images are 24px x 24px
			// so transformed image will be 48px x 48px

			//315 range -- 22.5 either side of 315
			if (bearing >= 292.5 && bearing < 335.5){
				offsetX = 24;
				offsetY = 24;
			}
			//270 range
			else if (bearing >= 247.5 && bearing < 292.5){
				offsetX = 24;
				offsetY = 12;
			}
			//225 range
			else if (bearing >= 202.5 && bearing < 247.5){
				offsetX = 24;
				offsetY = 0;
			}
			//180 range
			else if (bearing >= 157.5 && bearing < 202.5){
				offsetX = 12;
				offsetY = 0;
			}
			//135 range
			else if (bearing >= 112.5 && bearing < 157.5){
				offsetX = 0;
				offsetY = 0;
			}
			//90 range
			else if (bearing >= 67.5 && bearing < 112.5){
				offsetX = 0;
				offsetY = 12;
			}
			//45 range
			else if (bearing >= 22.5 && bearing < 67.5){
				offsetX = 0;
				offsetY = 24;
			}
			//0 range - 335.5 - 22.5
			else {
				offsetX = 12;
				offsetY = 24;
			}

			Bitmap wideBmp;
			Canvas wideBmpCanvas;
			Rect src, dest;

			// Create larger bitmap 4 times the size of arrow head image
			wideBmp = Bitmap.createBitmap(image.getWidth() * 2, image.getHeight() * 2, image.getConfig());

			wideBmpCanvas = new Canvas(wideBmp);

			src = new Rect(0, 0, image.getWidth(), image.getHeight());
			dest = new Rect(src);
			dest.offset(offsetX, offsetY);

			wideBmpCanvas.drawBitmap(image, src, dest, null);

			mMap.addMarker(new MarkerOptions()
					.position(to)
					.icon(BitmapDescriptorFactory.fromBitmap(wideBmp))
					.anchor(anchorX, anchorY));
		}
	}

	private double GetBearing(LatLng from, LatLng to){
		double lat1 = from.latitude * Math.PI / 180.0;
		double lon1 = from.longitude * Math.PI / 180.0;
		double lat2 = to.latitude * Math.PI / 180.0;
		double lon2 = to.longitude * Math.PI / 180.0;

		// Compute the angle.
		double angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );

		if (angle < 0.0)
			angle += Math.PI * 2.0;

		// And convert result to degrees.
		angle = angle * degreesPerRadian;

		return angle;
	}
}


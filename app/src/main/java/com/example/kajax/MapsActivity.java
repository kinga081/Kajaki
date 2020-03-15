package com.example.kajax;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;

    public static final String TAG = "GoogleActivity";
    private static final int RECORD_REQUEST_CODE =101 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle( "Kajax" );
        toolbar.setTitleTextColor( Color.WHITE);
       // toolbar.setLogo( R.drawable.kayak );

        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        int permissionCheck = ContextCompat.checkSelfPermission(this,//pozwolenie
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck3 = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 !=
                PackageManager.PERMISSION_GRANTED || permissionCheck3 !=
                PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make( view, "Replace with your own action", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
            }
        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.domyslny) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        }else if(id == R.id.satelita) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        }else if(id == R.id.teren) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50.602132, 23.031859)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
    }


    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Moja lokalizacja: " + location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_LONG).show();
        //info.setVisibility(View.INVISIBLE);
    }



    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Moja lokalizacja", Toast.LENGTH_SHORT).show();
        //info.setVisibility(View.INVISIBLE);
        return false;
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                RECORD_REQUEST_CODE);
    }
}

package com.example.kajax;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, BottomNavigation.BottomSheetListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    public static final String TAG = "GoogleActivity";
    private static final int RECORD_REQUEST_CODE = 101;
    private DatabaseReference mUsers;
    private HashMap<String, String> meMap;
    private Marker marker;
    private FloatingActionButton info;
    public String title;
    public String value;
    public String category;
    private FloatingActionButton my_location;
    Boolean sprawdzK = false;
    Boolean sprawdzJ = false;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "Kajax" );
        toolbar.setTitleTextColor( Color.WHITE );

      //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        //toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.kayak));
        //toolbar.setLogo( R.drawable.kayak );
        //toolbar.setTitle( "Kajax" );

        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        //  mTextView = findViewById(R.id.text_view_button_clicked);


        int permissionCheck = ContextCompat.checkSelfPermission( this,//pozwolenie
                android.Manifest.permission.ACCESS_FINE_LOCATION );
        int permissionCheck2 = ContextCompat.checkSelfPermission( this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION );
        int permissionCheck3 = ContextCompat.checkSelfPermission( this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE );

        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 !=
                PackageManager.PERMISSION_GRANTED || permissionCheck3 !=
                PackageManager.PERMISSION_GRANTED) {
            Log.i( TAG, "Permission to record denied" );
            makeRequest();
        }

        info = (FloatingActionButton) findViewById(R.id.info);
        my_location = (FloatingActionButton) findViewById(R.id.my_location);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                android.app.AlertDialog.Builder builder =
                        new android.app.AlertDialog.Builder(MapsActivity.this);
                builder.setTitle("title");
                builder.setMessage("value");
                builder.setPositiveButton("Zamknij", null);
                builder.show();
*/
                Intent startNewActivity = new Intent(MapsActivity.this, Informacja.class);
                startNewActivity.putExtra("title", title);
                startNewActivity.putExtra("value", value);
                startNewActivity.putExtra("category", category);
                startActivity(startNewActivity);
            }
        });

        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.6031274, 23.0648333), 11);
                mMap.animateCamera(cameraUpdate);
            }
        });
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
            mMap.setMapType( GoogleMap.MAP_TYPE_NORMAL );
            return true;
        } else if (id == R.id.satelita) {
            mMap.setMapType( GoogleMap.MAP_TYPE_SATELLITE );
            return true;
        } else if (id == R.id.teren) {
            mMap.setMapType( GoogleMap.MAP_TYPE_TERRAIN );
            return true;
        } else if (id == R.id.kajaki) {
            dodajMarker2( "Kajaki", "Zwierzyniec");
            dodajMarker2( "Kajaki", "Obrocz");
            dodajMarker2( "Kajaki", "Guciów");
            dodajMarker2( "Kajaki", "Bondyrz");
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.6031274, 23.0648333), 11);
            Toast.makeText( this, "Kajaki", Toast.LENGTH_SHORT ).show();
            mMap.animateCamera(cameraUpdate);
            //item.setIcon( R.drawable.food );
           // category = "Kayaks";
            return true;
        } else if (id == R.id.jedzenie) {
            dodajMarker2( "Gastronomia",  "Zwierzyniec" );
            dodajMarker2( "Gastronomia",  "Obrocz" );
            dodajMarker2( "Gastronomia",  "Guciów" );
            dodajMarker2( "Gastronomia",  "Bondyrz" );
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.6031274, 23.0648333), 11);
            Toast.makeText( this, "Gastronomia", Toast.LENGTH_SHORT ).show();
            mMap.animateCamera(cameraUpdate);
            return true;
        } else if (id == R.id.wyszukaj) {
            miejsce();

        }
        return true;
    }


    public void miejsce(){
        BottomNavigation bottomSheet = new BottomNavigation();
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51.2466815, 22.5678196)));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //LatLng pos= new LatLng(51.2466815, 22.5678196);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(50.6031274, 23.0648333), 11);
        mMap.animateCamera(cameraUpdate);

        mMap.setOnInfoWindowClickListener(this);


    }

    public void onInfoWindowClick(Marker marker) {
        Intent startNewActivity = new Intent(MapsActivity.this, Informacja.class);
        startNewActivity.putExtra("title", title);
        startNewActivity.putExtra("value", value);
        startNewActivity.putExtra("category", category);
        startActivity(startNewActivity);
    }


    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText( this, "Moja lokalizacja: " + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_LONG ).show();
        info.setVisibility(View.INVISIBLE);
    }


    public boolean onMyLocationButtonClick() {
        Toast.makeText( this, "Moja lokalizacja", Toast.LENGTH_SHORT ).show();
        info.setVisibility(View.INVISIBLE);
        return false;
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions( this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                RECORD_REQUEST_CODE );
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        title = marker.getTitle();
        value = marker.getSnippet();
        //info.setVisibility(View.VISIBLE); totototototototootto
        category = meMap.get(title);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        info.setVisibility(View.INVISIBLE);
    }



    public void dodajMarker2(final String kategoria, final String text) {// true== kajaki
        info.setVisibility( View.INVISIBLE );

        mUsers = FirebaseDatabase.getInstance().getReference( kategoria ).child( text );
        mUsers.push().setValue( marker );
        mMap.clear();
        meMap = new HashMap<String, String>();

        mUsers.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Lokalizacje user = s.getValue( Lokalizacje.class );
                    LatLng location = new LatLng( user.getLatitude()  , user.getLongitude() );

                        if (kategoria.equals( "Kajaki" )) {
                            Marker melbourne= mMap.addMarker( new MarkerOptions()
                                            .position( location )
                                            .title( user.getName() )
                                            //.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_BLUE ) )
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1))
                                            .snippet( "Kajaki")
                                    //.snippet( user.getLocal() )
                            );
                            meMap.put( user.getName(),text );
                        } else  {
                            mMap.addMarker( new MarkerOptions()
                                    .position( location )
                                    .title( user.getName() )
                                    //.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_YELLOW ) )
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker2))
                                    .snippet( "Gastronomia"  )
                            );
                            meMap.put( user.getName(),text );
                        }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    @Override
    public void onButtonClicked(String text) {
      dodajMarker2( "Kajaki",text );
        dodajMarker2( "Gastronomia",text );
        Toast.makeText( this, text, Toast.LENGTH_SHORT ).show();

        if(text.equals( "Zwierzyniec" )) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom( new LatLng( 50.6090759, 22.9850484 ), 13 );
            mMap.animateCamera( cameraUpdate );
        }else if(text.equals( "Obrocz" )){
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom( new LatLng( 50.5999074, 23.0179374 ), 14 );
            mMap.animateCamera( cameraUpdate );
        }else if(text.equals( "Guciów" )){
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom( new LatLng( 50.5839813, 23.0745209 ), 15 );
            mMap.animateCamera( cameraUpdate );
        }else if(text.equals( "Bondyrz" )){
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom( new LatLng( 50.5633757, 23.1319835 ), 15 );
            mMap.animateCamera( cameraUpdate );
        }
    }
}
package com.example.kajax;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Informacja extends AppCompatActivity {


    private DatabaseReference mDatabaseReference;
    private ArrayList<String> arrayList =new ArrayList<>();
    private ArrayList<String> lista =new ArrayList<>();
    private ListView listView;
    public ArrayAdapter<String> arrayAdapter;
    private TextView text;
    private Spanned HyperLink;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final String title = getIntent().getStringExtra("title");
        final String value = getIntent().getStringExtra("value");
        final String category = getIntent().getStringExtra("category");


        image = (ImageView)findViewById(R.id.image);

        if(value.equals( "Gastronomia" )){
            image.setImageResource(R.drawable.food3 );
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();
            }
        });

        //text =(TextView) findViewById(R.id.text);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(value).child( category ).child( title );
        ////final String player = mDatabaseReference.push().getKey();

        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   // Toast.makeText( this, "Moja "+value, Toast.LENGTH_SHORT ).show();
                    image.setImageResource(R.drawable.food3 );
                }

        } );


        //listView =(ListView) findViewById(R.id.lista);
        //arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        //listView.setAdapter(arrayAdapter);



        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Lokalizacje user = dataSnapshot.getValue(Lokalizacje.class);
                    ((TextView)findViewById(R.id.nazwa)).setText(user.getName());
                    ((TextView)findViewById(R.id.adres)).setText(user.getAdress());
                    ((TextView)findViewById(R.id.godziny)).setText(user.getH_open());
                    ((TextView)findViewById(R.id.telefon)).setText(user.getTel());
                    ((TextView)findViewById(R.id.strona)).setMovementMethod( LinkMovementMethod.getInstance());
                    ((TextView)findViewById(R.id.strona)).setText(Html.fromHtml("<a href='https://"+user.getWeb()+"'>"+user.getWeb()+"</a>"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //arrayAdapter.notifyDataSetChanged();
        //arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
/*
        mDatabaseReference.addChildEventListener(new ChildEventListener() {  ///lista
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String Value=dataSnapshot.getValue().toString();
                arrayList.add(Value);
                arrayAdapter.notifyDataSetChanged();


            }

            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
   // lista.add( arrayList.get( 5 ) );
       // lista.add( "Adres: "+arrayList.get( 1 ) );


        //text.setText( "  "+arrayList.get( 2 ) );
    }

}

package com.example.vele_.nearbyrestaurantsfoodanddrinks;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity {
    ImageView imgSrc;
    TextView name;
    TextView vicinity;
    TextView rating;
    TextView openNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgSrc = (ImageView) findViewById(R.id.imagePlace);
        name = (TextView) findViewById(R.id.pName);
        vicinity = (TextView) findViewById(R.id.pVicinity);
        rating= (TextView) findViewById(R.id.pRating);
        openNow = (TextView) findViewById(R.id.pIsOpen);

        String ImgSrc="http://xpenology.org/wp-content/themes/qaengine/img/default-thumbnail.jpg";
        String Name="default";
        String Vicinity= "default";
        String Rating = "default";
        Boolean OpenNow = false;

        Bundle extras = getIntent().getExtras();
        //ImgSrc = "https://maps.googleapis.com/maps/api/place/details/json?reference="+extras.getString("imgSrc");
        Name = extras.getString("name");
        Vicinity = extras.getString("vicinity");
        Rating = extras.getString("rating");
        OpenNow = extras.getBoolean("openNow");

        Picasso.with(getApplicationContext()).load(ImgSrc).into(imgSrc);
        name.setText("Name: "+Name);
        vicinity.setText("Vicinity: "+Vicinity);
        rating.setText("Rating: "+Rating);
        if(OpenNow)
            openNow.setText("Open hours: Is Open Now.");
        else
            openNow.setText("Open hours: Is Not Open Now");



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}

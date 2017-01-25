package com.example.vele_.nearbyrestaurantsfoodanddrinks;

import android.app.ActionBar;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {
    SeekBar sb;
    TextView radius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sb = (SeekBar) findViewById(R.id.seekBar);
        radius = (TextView) findViewById(R.id.radius);
        final CheckBox restuarants = (CheckBox) findViewById(R.id.restaurants);
        final CheckBox food = (CheckBox) findViewById(R.id.food);
        final CheckBox liquorStore = (CheckBox) findViewById(R.id.liquor_store);
        final CheckBox cafe = (CheckBox) findViewById(R.id.cafe);
        final File out = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NRFD.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(out));
            String line;
            while ((line=br.readLine())!=null){
                String [] line1 = line.split(":");
                if(line1[0].equals("radius")){
                    radius.setText(line1[1]);
                    sb.setProgress((Integer.parseInt(line1[1])-500)/45);
                    Log.e("brojka",(Integer.parseInt(line1[1])-500)/45+"");
                }else if(line1[0].equals("restaurants")){
                    if(line1[1].equals("true")){
                        restuarants.setChecked(true);
                    }else{
                        restuarants.setChecked(false);
                    }
                }else if(line1[0].equals("food")){
                    if(line1[1].equals("true")){
                        food.setChecked(true);
                    }else{
                        food.setChecked(false);
                    }
                }else if(line1[0].equals("liquor_store")){
                    if(line1[1].equals("true")){
                        liquorStore.setChecked(true);
                    }else{
                        liquorStore.setChecked(false);
                    }
                }else if(line1[0].equals("cafe")){
                    if(line1[1].equals("true")){
                        cafe.setChecked(true);
                    }else{
                        cafe.setChecked(false);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                int initial = 500;

                radius.setText(500+(progress*45)+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button b = (Button) findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radiu = Integer.parseInt(radius.getText().toString());
                boolean restauran = restuarants.isChecked();
                boolean foo = food.isChecked();
                boolean liquorStor = liquorStore.isChecked();
                boolean caf = cafe.isChecked();

                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(out));
                    String write = "";
                    write += "radius:";
                    write += (radiu+"");
                    write += "\n";
                    write += "restaurants:";
                    write += (restauran+"");
                    write += "\n";
                    write += "food:";
                    write += (foo+"");
                    write += "\n";
                    write += "liquor_store:";
                    write += (liquorStor+"");
                    write += "\n";
                    write += "cafe:";
                    write += (caf+"");
                    bw.write(write);
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(SettingsActivity.this,NearbyStuff.class);
                startActivity(i);

            }
        });
    }


}

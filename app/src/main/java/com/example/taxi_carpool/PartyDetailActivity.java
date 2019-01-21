package com.example.taxi_carpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class PartyDetailActivity extends AppCompatActivity {

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private MaterialButton fab;
    private String title, departure, destination,date, explanation;
    private Integer numLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        departure = intent.getStringExtra("departure");
        destination = intent.getStringExtra("destination");
        date = intent.getStringExtra("date");
        numLeft = intent.getIntExtra("numLeft", 4);
        explanation = intent.getStringExtra("explanation");

        fab = findViewById(R.id.action_button);


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        TextView txt_title = findViewById(R.id.party_detail_title);
        TextView txt_start = findViewById(R.id.party_detail_start);
        TextView txt_end = findViewById(R.id.party_detail_end);
        TextView txt_when = findViewById(R.id.party_detail_time);
        TextView txt_people = findViewById(R.id.party_detail_numPeople);
        TextView txt_extra = findViewById(R.id.party_detail_explanation);

        txt_title.setText(title);
        txt_start.setText(departure);
        txt_end.setText(destination);
        txt_when.setText(date);
        txt_people.setText(numLeft.toString());
        txt_extra.setText(title);
    }

    public void animButton(View v) {
        anim();
    }

    public void anim() {
        if (isFabOpen) {
            fab.startAnimation(fab_close);
            fab.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(fab_open);
            fab.setClickable(true);
            isFabOpen = true;
        }
    }
}

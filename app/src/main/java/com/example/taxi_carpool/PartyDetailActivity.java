package com.example.taxi_carpool;

import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class PartyDetailActivity extends AppCompatActivity {

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private MaterialButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        fab = findViewById(R.id.action_button);


        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
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

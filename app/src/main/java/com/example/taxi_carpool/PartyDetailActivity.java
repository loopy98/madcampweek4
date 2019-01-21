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
import android.widget.Toast;

import com.example.taxi_carpool.Retrofit.IMyService;
import com.example.taxi_carpool.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class PartyDetailActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private MaterialButton fab;
    private String title, departure, destination,date, explanation, partyId;
    private Integer numLeft, userPhone;

    //onStop() : 액티비티가 더이상 사용자에게 보여지지 않을 때 호출
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_detail);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Intent intent = getIntent();
        userPhone = intent.getIntExtra("UserPhoneNumber", 00000000000);
        partyId = intent.getStringExtra("PartyId");
        title = intent.getStringExtra("title");
        departure = intent.getStringExtra("departure");
        destination = intent.getStringExtra("destination");
        date = intent.getStringExtra("date");
        numLeft = intent.getIntExtra("numLeft", 4);
        explanation = intent.getStringExtra("explanation");

        fab = findViewById(R.id.action_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DB user 정보에 저장
                compositeDisposable.add(iMyService.enterParty(userPhone, partyId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String response) throws Exception {
                                if(response.equals("\'user info updated'\'")) { //새로운 채팅방에 가입!
                                    Toast.makeText(getApplicationContext(), "가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    //
                                }else { //로그인 성공!!
                                    Intent intent = new Intent(getApplicationContext(), PartyListActivity.class);
                                    startActivity(intent);
                                }


                            }
                        }));
            }
        });


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

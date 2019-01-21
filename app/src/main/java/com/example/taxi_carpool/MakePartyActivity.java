package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taxi_carpool.Retrofit.IMyService;
import com.example.taxi_carpool.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MakePartyActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    //onStop() : 액티비티가 더이상 사용자에게 보여지지 않을 때 호출
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeparty);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

    }

    protected void mOnMakeParty(View v){
        final EditText pageTitle = findViewById(R.id.Title);
        final EditText startText = findViewById(R.id.Start);
        final EditText endText = findViewById(R.id.End);
        final EditText peopleText = findViewById(R.id.Number);
        final EditText extraText = findViewById(R.id.Extra);


        if(pageTitle.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(startText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "출발지를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(endText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "목적지를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(peopleText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "몇명과 함께 타시길 원하시나요?", Toast.LENGTH_SHORT).show();
        }else{ // 모든 데이터들을 입력했음으로 그 데이터들을 서버로 전송
            makeRoom(pageTitle.getText().toString(),
                    startText.getText().toString(),
                    endText.getText().toString(),
                    peopleText.getText().toString(),
                    extraText.getText().toString());
            finish();
        }

    }

    private void makeRoom(String title, String departure, String destination, String numLeft, String explanation) {
        Log.e("makeRoom Parameter", title + " / " + departure + " / " + destination + " / " + numLeft + " / " + explanation);
        compositeDisposable.add(iMyService.makeNewParty(title, departure, destination, numLeft, explanation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {

                        if(response.equals("\"1\"")) { //만들기 성공!!
                            finish();
                        }
                        else if(response.equals("\"0\"")) { //만들기 실패!
                            Toast.makeText(MakePartyActivity.this, "다시 시도해주세요", Toast.LENGTH_SHORT).show();
                        }

                    }
                }));
    }
}

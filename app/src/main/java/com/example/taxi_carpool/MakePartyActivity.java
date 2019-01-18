package com.example.taxi_carpool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MakePartyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeparty);
    }

    protected void mOnMakeParty(View v){
        EditText pageTitle = findViewById(R.id.Title);
        EditText startText = findViewById(R.id.Start);
        EditText endText = findViewById(R.id.End);
        EditText peopleText = findViewById(R.id.Number);
        EditText extraText = findViewById(R.id.Extra);


        if(pageTitle.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(startText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "출발지를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(endText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "목적지를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(peopleText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "몇명과 함께 타시길 원하시나요?", Toast.LENGTH_SHORT).show();
        }else{ //방만들기 가능!!
            //서버에 해당 데이터들을 보내주기!!
            finish();
        }

    }
}

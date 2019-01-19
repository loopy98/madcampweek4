package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        //intent.putExtra("data", "Test Popup");
        startActivityForResult(intent, 1);
    }

    public void mOnLogIn(View v){
        EditText phonenumText = findViewById(R.id.editText2);
        EditText passwordText = findViewById(R.id.editText4);

        //회원가입 시 빈칸이 있나 확인
        if(phonenumText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(passwordText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }else{
            //아이디와 비밀번호가 맞는지 확인하는 기능?
            Intent intent = new Intent(this, PartyListActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 바깥 레이어를 클릭해도 안닫히게 하기
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


}

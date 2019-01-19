package com.example.taxi_carpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PopupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity);
    }

    //뒤로가기 버튼 누르기
    public void mOnBack(View v) {
        //액티비티(팝업) 닫기
        finish();
    }

    //확인 버튼 클릭
    public void mOnSignUp(View v){
        EditText phonenumText = findViewById(R.id.register_phonenum);
        EditText passwordText = findViewById(R.id.register_password);
        EditText passwordTestText = findViewById(R.id.register_password_check);
        EditText accountCompanyText = findViewById(R.id.register_account_company);
        EditText accountText = findViewById(R.id.register_account);


        //회원가입 시 빈칸이 있나 확인
        if(phonenumText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(passwordText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(accountCompanyText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "본인 계좌의 회사를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if(accountText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "계좌번호를 입력하세요", Toast.LENGTH_SHORT).show();
        }else if( !passwordText.getText().toString().equals(passwordTestText.getText().toString()) ){
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
            finish();
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

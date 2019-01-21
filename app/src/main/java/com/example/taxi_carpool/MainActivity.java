package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.taxi_carpool.Retrofit.IMyService;
import com.example.taxi_carpool.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    EditText edt_login_phonenumber, edt_login_password;
    Button login_btn, signUp_btn;

    //onStop() : 액티비티가 더이상 사용자에게 보여지지 않을 때 호출
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //Init View
        edt_login_phonenumber = findViewById(R.id.editText2);
        edt_login_password = findViewById(R.id.editText4);
        login_btn = findViewById(R.id.btn_login);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 시 빈칸이 있나 확인
                if(edt_login_phonenumber.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }else if(edt_login_password.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(edt_login_phonenumber.getText().toString(), edt_login_password.getText().toString());
                }
            }
        });

        signUp_btn = findViewById(R.id.btn_sign_up);
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View register_layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup_activity1, null);

                new MaterialStyledDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.taxi1)
                        .setTitle("REGISTRATION")
                        .setDescription("Please fill all fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                EditText phonenumText = register_layout.findViewById(R.id.register_phonenum1);
                                EditText passwordText = register_layout.findViewById(R.id.register_password1);
                                EditText passwordTestText = register_layout.findViewById(R.id.register_password_check1);
                                EditText accountCompanyText = register_layout.findViewById(R.id.register_account_company1);
                                EditText accountText = register_layout.findViewById(R.id.register_account1);

                                //회원가입 시 빈칸이 있나 확인
                                if(TextUtils.isEmpty(phonenumText.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                                }else if(TextUtils.isEmpty(passwordText.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                                }else if(TextUtils.isEmpty(accountCompanyText.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "본인 계좌의 회사를 입력하세요", Toast.LENGTH_SHORT).show();
                                }else if(TextUtils.isEmpty(accountText.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "계좌번호를 입력하세요", Toast.LENGTH_SHORT).show();
                                }else if( !passwordText.getText().toString().equals(passwordTestText.getText().toString()) ){
                                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                }else{
                                    registerUser(phonenumText.getText().toString(),
                                            passwordText.getText().toString(),
                                            accountCompanyText.getText().toString(),
                                            accountText.getText().toString());
                                    
                                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                            }
                        }).show();
            }
        });

    }

    private void registerUser(String phonenum, String password, String accountCompany, String account) {
        compositeDisposable.add(iMyService.registerUser(phonenum, password, accountCompany, account)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        if(response.equals("\"1\"")) { // 회원가입 성공!
                            Intent intent = new Intent(getApplicationContext(), PartyListActivity.class);
                            startActivity(intent);
                        }
                        else if(response.equals("\"2\"")) { //이미 등록한 유저!!
                            Toast.makeText(MainActivity.this, "이미 등록된 사용자 입니다", Toast.LENGTH_SHORT).show();
                        }




                        Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void loginUser(String phonenum, String password) {
        compositeDisposable.add(iMyService.loginUser(phonenum, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String response) throws Exception {


                if(response.equals("\"0\"")) {
                    Toast.makeText(MainActivity.this, "등록된 사용자가 아닙니다", Toast.LENGTH_SHORT).show();
                }else { //로그인 성공!!
                    Intent intent = new Intent(getApplicationContext(), PartyListActivity.class);
                    startActivity(intent);
                }


            }
        }));
    }


    //버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        //intent.putExtra("data", "Test Popup");
        startActivityForResult(intent, 1);
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

package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PartyListActivity extends AppCompatActivity{

    JSONArray inputJSONPartyList;
    List<TaxiParty> partyList;

    String userId, password, salt, company, account, currentTaxiParty;
    Integer phoneNumber;

    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();
        userId = intent.getStringExtra("_id");
        password = intent.getStringExtra("password");
        salt = intent.getStringExtra("salt");
        company = intent.getStringExtra("company");
        account = intent.getStringExtra("account");
        phoneNumber = intent.getIntExtra("phoneNumber", 4);
        currentTaxiParty = intent.getStringExtra("currentTaxiParty");




        //ViewPager와 Button 초기화
        vp = findViewById(R.id.vp);
        Button btn_presentParty = findViewById(R.id.presentParty);
        Button btn_myParty = findViewById(R.id.myParty);

        partyList = loadParty();


        //ViewPager와 Adapter 연결
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        //앱이 실행됐을 때 첫번째 페이지로 초기화 시키는 부분
        vp.setCurrentItem(0);

        //버튼 클릭 시 해당 페이지로 이동
        btn_presentParty.setOnClickListener(movePageListener);
        btn_presentParty.setTag(0);
        btn_myParty.setOnClickListener(movePageListener);
        btn_myParty.setTag(1);
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            vp.setCurrentItem(tag);
        }
    };

    //FragmentPagerAdapter가 아닌 FragmentStatePagerAdapter를 사용
    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            //각각의 position값이 들어오면 생성해둔 Fragment Class를 열어줌
            switch(position)
            {
                case 0:
                    return new PartyListFragment();
                case 1:
                    return new MyPartyFragment();
                default:
                    return null;
            }
        }
        //getCount : ViewPager에 들어가는 Page의 갯수
        @Override
        public int getCount()
        {
            return 2;
        }
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 누르면 로그아웃을 하겠냐는 창 띄우기

        new MaterialStyledDialog.Builder(PartyListActivity.this)
                .setTitle("LOGOUT")
                .setDescription("로그아웃 하시겠습니까?")
                .setNegativeText("CANCEL")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveText("LOGOUT")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                }).show();
    }

    public void mOnPlusParty(View v){
        Intent intent = new Intent(this, MakePartyActivity.class);
        startActivity(intent);
    }

    private List<TaxiParty> loadParty() {
        final List<TaxiParty> taxiPartyList = new ArrayList<>();
        Thread loadPartyThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://socrip4.kaist.ac.kr:2080/party-list");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    //Taxi팟 JsonObject가 담긴 Array하나씩 읽기
                    JSONArray inputArray = new JSONArray(bufferedReader.readLine());

                    inputJSONPartyList = inputArray;

                    for(int i = 0; i < inputArray.length(); i++) {
                        JSONObject inputItem = (JSONObject) inputArray.get(i);
                        String inputId = (String) inputItem.get("_id");
                        String inputTitle = (String) inputItem.get("title");
                        String inputDeparture = (String) inputItem.get("departure");
                        String inputDestination = (String) inputItem.get("destination");
                        String inputDate = (String) inputItem.get("date");
                        int inputNumLeft = (Integer) inputItem.get("numLeft");
                        String inputExplanation = (String) inputItem.get("explanation");

                        TaxiParty t = new TaxiParty(inputId, inputTitle, inputDeparture, inputDestination, inputDate, inputNumLeft, inputExplanation);
                        taxiPartyList.add(t);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        loadPartyThread.start();
        return taxiPartyList;
    }

}

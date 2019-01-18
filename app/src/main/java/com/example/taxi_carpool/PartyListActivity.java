package com.example.taxi_carpool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PartyListActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        //Manager를 설정하고 RecyclerView에 어댑터 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        PartyAdapter myAdapter = new PartyAdapter(PartyListActivity.this);
        mRecyclerView.setAdapter(myAdapter);



    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    public void mOnPlusParty(View v){
        Intent intent = new Intent(this, MakePartyActivity.class);
        startActivity(intent);


    }

}

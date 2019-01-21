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

public class PartyListActivity extends AppCompatActivity{
    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //ViewPager와 Button 초기화
        vp = findViewById(R.id.vp);
        Button btn_presentParty = findViewById(R.id.presentParty);
        Button btn_myParty = findViewById(R.id.myParty);

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

}

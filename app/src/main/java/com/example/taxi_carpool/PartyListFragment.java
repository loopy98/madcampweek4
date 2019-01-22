package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.taxi_carpool.Retrofit.IMyService;
import com.example.taxi_carpool.Retrofit.RetrofitClient;

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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PartyListFragment extends Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    List<TaxiParty> taxiPartyList;
    RecyclerView mRecyclerView;
    LinearLayout partyListLayout;
    Integer userPhoneNumber;

    PartyAdapter myAdapter;


    public PartyListFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Action Check", "onCreateView is started");

        partyListLayout = (LinearLayout) inflater.inflate(R.layout.fragment_partylist, container, false);

        mRecyclerView = partyListLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);

        //Manager를 설정하고 RecyclerView에 어댑터 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Fragment에서 부모 Activity의 변수 or 메소드에 접근하는 방법
        taxiPartyList = ((PartyListActivity)getActivity()).partyList;
        userPhoneNumber = ((PartyListActivity)getActivity()).phoneNumber;

        myAdapter = new PartyAdapter(getContext(), taxiPartyList, userPhoneNumber);
        mRecyclerView.setAdapter(myAdapter);


        return partyListLayout;
    }

    @Override
    public void onStart() {
        Log.e("onStart is started", "Start");
        super.onStart();

        taxiPartyList = ((PartyListActivity)getActivity()).getPartyList();
        Log.e("getPartyList is started", "Start");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("Thread is started", "Start");
                myAdapter = new PartyAdapter(getContext(), taxiPartyList, userPhoneNumber);
                mRecyclerView.setAdapter(myAdapter);
            }
        }, 500);

    }

//    @Override
//    public void onResume() {
//        Log.e("onResume", "Start");
//        super.onResume();
//        taxiPartyList = ((PartyListActivity)getActivity()).getPartyList();
//        myAdapter = new PartyAdapter(getContext(), taxiPartyList, userPhoneNumber);
//        mRecyclerView.setAdapter(myAdapter);
//    }
}

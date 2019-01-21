package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    ConstraintLayout partyListLayout;
    Integer userPhoneNumber;


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

        partyListLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_partylist, container, false);

        mRecyclerView = partyListLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);

        //Manager를 설정하고 RecyclerView에 어댑터 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        taxiPartyList = ((PartyListActivity)getActivity()).partyList;
        userPhoneNumber = ((PartyListActivity)getActivity()).phoneNumber;

        PartyAdapter myAdapter = new PartyAdapter(getContext(), taxiPartyList, userPhoneNumber);
        mRecyclerView.setAdapter(myAdapter);


        return partyListLayout;
    }

//    @Override
//    public void onResume() {
//
//        super.onResume();
//        mRecyclerView = partyListLayout.findViewById(R.id.recycler_view);
//        mRecyclerView.setHasFixedSize(false);
//        Log.e("Action Check", "onResume is started");
//        partyList = loadParty();
//        Log.e("Action Check", "loadParty is finished");
//        PartyAdapter myAdapter = new PartyAdapter(getContext(), partyList);
//        //myAdapter.notifyDataSetChanged();
//        mRecyclerView.setAdapter(myAdapter);
//    }
}

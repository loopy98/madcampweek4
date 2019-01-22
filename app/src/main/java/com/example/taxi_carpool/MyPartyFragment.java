package com.example.taxi_carpool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taxi_carpool.Retrofit.IMyService;
import com.example.taxi_carpool.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MyPartyFragment extends Fragment {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    ArrayList<TaxiParty> TaxiArray;
    String currentTaxi;
    TaxiParty myTaxi;
    ConstraintLayout myPartyLayout;
    MaterialButton btn;
    int userPhoneNumber;


    public MyPartyFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myPartyLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_myparty, container, false);

        btn = myPartyLayout.findViewById(R.id.btn_join_chatroom);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(currentTaxi);
                Toast toast = Toast.makeText(getActivity(), "문자가 발송되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                txt_title.setText(myTaxi.title);
//                txt_start.setText(myTaxi.departure);
//                txt_end.setText(myTaxi.destination);
//                txt_when.setText(myTaxi.when);
//                txt_people.setText(myTaxi.numLeft.toString());
//                txt_extra.setText(myTaxi.explanation);
//            }
//        }, 3000);
        return myPartyLayout;
    }

    private void sendMessage(String currentTaxi) {
        compositeDisposable.add(iMyService.sendMessage(currentTaxi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                    }
                }));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final TextView txt_title = myPartyLayout.findViewById(R.id.party_detail_title);
        final TextView txt_start = myPartyLayout.findViewById(R.id.party_detail_start);
        final TextView txt_end = myPartyLayout.findViewById(R.id.party_detail_end);
        final TextView txt_when = myPartyLayout.findViewById(R.id.party_detail_time);
        final TextView txt_people = myPartyLayout.findViewById(R.id.party_detail_numPeople);
        final TextView txt_extra = myPartyLayout.findViewById(R.id.party_detail_explanation);

        TaxiArray = ((PartyListActivity)getActivity()).getPartyList();
        userPhoneNumber = ((PartyListActivity)getActivity()).phoneNumber;
        currentTaxi = ((PartyListActivity)getActivity()).currentTaxiParty;


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentTaxi != null){
                    for(int i = 0; i < TaxiArray.size(); i++) {
                        Log.e("Check", Integer.toString(i));
                        TaxiParty t = TaxiArray.get(i);
                        if(currentTaxi.equals(t.ID)){
                            Log.e("Find!!", t.ID);
                            //myTaxi = t;

                            txt_title.setText(t.title);
                            txt_start.setText(t.departure);
                            txt_end.setText(t.destination);
                            txt_when.setText(t.when);
                            txt_people.setText(t.numLeft.toString());
                            txt_extra.setText(t.explanation);
                        }
                    }
                }

            }
        }, 500);
    }

    @Override
    public void onStart() {
        Log.e("MyParty onStart started", "Start");
        super.onStart();

        final TextView txt_title = myPartyLayout.findViewById(R.id.party_detail_title);
        final TextView txt_start = myPartyLayout.findViewById(R.id.party_detail_start);
        final TextView txt_end = myPartyLayout.findViewById(R.id.party_detail_end);
        final TextView txt_when = myPartyLayout.findViewById(R.id.party_detail_time);
        final TextView txt_people = myPartyLayout.findViewById(R.id.party_detail_numPeople);
        final TextView txt_extra = myPartyLayout.findViewById(R.id.party_detail_explanation);

        TaxiArray = ((PartyListActivity)getActivity()).getPartyList();

        compositeDisposable.add(iMyService.loadMyParty(userPhoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        currentTaxi = response;
                    }
                }));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("Current Taxi Check", currentTaxi);
                if(currentTaxi != null){
                    for(int i = 0; i < TaxiArray.size(); i++) {
                        Log.e("Check", Integer.toString(i));
                        TaxiParty t = TaxiArray.get(i);
                        if(currentTaxi.equals(t.ID)){
                            Log.e("Find!!", t.ID);
                            //myTaxi = t;

                            txt_title.setText(t.title);
                            txt_start.setText(t.departure);
                            txt_end.setText(t.destination);
                            txt_when.setText(t.when);
                            txt_people.setText(t.numLeft.toString());
                            txt_extra.setText(t.explanation);
                        }
                    }
                }

            }
        }, 500);

//        taxiPartyList = ((PartyListActivity)getActivity()).getPartyList();
//        Log.e("getPartyList is started", "Start");
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("Thread is started", "Start");
//                myAdapter = new PartyAdapter(getContext(), taxiPartyList, userPhoneNumber);
//                mRecyclerView.setAdapter(myAdapter);
//            }
//        }, 500);

    }

    public void joinChatRoom() {
        Toast.makeText(getContext(), "버튼을 클릭했어요!", Toast.LENGTH_SHORT).show();
    }
}

package com.example.taxi_carpool;

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

import java.util.ArrayList;

public class MyPartyFragment extends Fragment {

    ArrayList<TaxiParty> TaxiArray;
    String currentTaxi;
    TaxiParty myTaxi;
    ConstraintLayout myPartyLayout;

    public MyPartyFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myPartyLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_myparty, container, false);
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
        currentTaxi = ((PartyListActivity)getActivity()).currentTaxiParty;


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < TaxiArray.size(); i++) {
                    Log.e("Check", Integer.toString(i));
                    TaxiParty t = TaxiArray.get(i);
                    Log.e("Taxi ID", t.ID);
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
        }, 500);
    }

    public void joinChatRoom() {
        Toast.makeText(getContext(), "버튼을 클릭했어요!", Toast.LENGTH_SHORT).show();
    }
}

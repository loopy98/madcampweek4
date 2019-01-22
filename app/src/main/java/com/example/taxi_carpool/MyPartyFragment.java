package com.example.taxi_carpool;

import android.os.Bundle;
import android.os.Handler;
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

    public MyPartyFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ConstraintLayout myPartyLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_myparty, container, false);

        TaxiArray = ((PartyListActivity)getActivity()).partyList;
        currentTaxi = ((PartyListActivity)getActivity()).currentTaxiParty;

        for(int i = 0; i < TaxiArray.size(); i++) {
            Log.e("Check", Integer.toString(i));
            TaxiParty t = TaxiArray.get(i);
            if(currentTaxi.equals(t.ID)){
                Log.e("Find!!", t.ID);
                myTaxi = t;
            }

        }

        final TextView txt_title = myPartyLayout.findViewById(R.id.party_detail_title);
        final TextView txt_start = myPartyLayout.findViewById(R.id.party_detail_start);
        final TextView txt_end = myPartyLayout.findViewById(R.id.party_detail_end);
        final TextView txt_when = myPartyLayout.findViewById(R.id.party_detail_time);
        final TextView txt_people = myPartyLayout.findViewById(R.id.party_detail_numPeople);
        final TextView txt_extra = myPartyLayout.findViewById(R.id.party_detail_explanation);

        txt_title.setText(myTaxi.title);
        txt_start.setText(myTaxi.departure);
        txt_end.setText(myTaxi.destination);
        txt_when.setText(myTaxi.when);
        txt_people.setText(myTaxi.numLeft.toString());
        txt_extra.setText(myTaxi.explanation);


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

    public void joinChatRoom() {
        Toast.makeText(getContext(), "버튼을 클릭했어요!", Toast.LENGTH_SHORT).show();
    }
}

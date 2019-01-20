package com.example.taxi_carpool;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MyPartyFragment extends Fragment {

    public MyPartyFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ConstraintLayout myPartyLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_myparty, container, false);
        return myPartyLayout;
    }
}

package com.example.taxi_carpool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class PartyListFragment extends Fragment {

    public PartyListFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ConstraintLayout partyListLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_partylist, container, false);

        RecyclerView mRecyclerView = partyListLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);

        //Manager를 설정하고 RecyclerView에 어댑터 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        PartyAdapter myAdapter = new PartyAdapter(getContext());
        mRecyclerView.setAdapter(myAdapter);


        return partyListLayout;
    }
}

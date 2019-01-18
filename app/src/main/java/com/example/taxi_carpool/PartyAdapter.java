package com.example.taxi_carpool;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.MyViewHolder> {

    private Context _context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout roomList;

        MyViewHolder(View view){
            super(view);
            roomList = itemView.findViewById(R.id.constraintLayout);
        }
    }

    public PartyAdapter(Context context){
        _context = context;
    }

    @Override
    public PartyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_party_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PartyAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.roomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_context, PartyDetailActivity.class);
                _context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 15;
    }

}

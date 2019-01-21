package com.example.taxi_carpool;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.MyViewHolder> {

    private Context _context;
    List<TaxiParty> _list;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout roomList;
        public TextView roomTitle;
        public TextView start_end;
        public TextView date;
        public ImageView partition;

        MyViewHolder(View view){
            super(view);
            roomList = itemView.findViewById(R.id.constraintLayout);
        }
    }

    //Adapter 생성자
    public PartyAdapter(Context context, List<TaxiParty> list){
        this._context = context;
        this._list = list;
    }

    @Override
    public PartyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_party_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PartyAdapter.MyViewHolder viewHolder, int position) {
        final TaxiParty t = _list.get(position);
        viewHolder.roomList.setText(t.title);





        
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
        return _list.size();
    }

}

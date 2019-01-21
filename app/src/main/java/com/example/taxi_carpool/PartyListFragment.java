package com.example.taxi_carpool;

import android.content.Intent;
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

    List<TaxiParty> partyList;


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

        ConstraintLayout partyListLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_partylist, container, false);

        RecyclerView mRecyclerView = partyListLayout.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);

        partyList = loadParty();

        //Manager를 설정하고 RecyclerView에 어댑터 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        PartyAdapter myAdapter = new PartyAdapter(getContext(), partyList);
        mRecyclerView.setAdapter(myAdapter);


        return partyListLayout;
    }

    private List<TaxiParty> loadParty() {
        final List<TaxiParty> taxiPartyList = new ArrayList<>();
        Thread loadPartyThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://socrip4.kaist.ac.kr:2080/party-list");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    //Taxi팟 JsonObject가 담긴 Array하나씩 읽기
                    JSONArray inputArray = new JSONArray(bufferedReader.readLine());

                    for(int i = 0; i < inputArray.length(); i++) {
                        JSONObject inputItem = (JSONObject) inputArray.get(i);
                        String inputTitle = (String) inputItem.get("title");
                        String inputDeparture = (String) inputItem.get("departure");
                        String inputDestination = (String) inputItem.get("destination");
                        String inputNumLeft = (String) inputItem.get("numLeft");
                        String inputExplanation = (String) inputItem.get("explanation");

                        TaxiParty t = new TaxiParty(inputTitle, inputDeparture, inputDestination, inputNumLeft, inputExplanation);
                        taxiPartyList.add(t);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        loadPartyThread.start();
        return taxiPartyList;
    }
}

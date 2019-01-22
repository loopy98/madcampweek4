package com.example.taxi_carpool;

public class TaxiParty {
    String ID;
    String title;
    String departure;
    String destination;
    String when;
    Integer numLeft;
    String explanation;


    public TaxiParty(String id, String title, String departure, String destination, String date, Integer numLeft, String explanation) {
        this.ID = id;
        this.title = title;
        this.departure = departure;
        this.destination = destination;
        this.when = date;
        this.numLeft = numLeft;
        this.explanation = explanation;
    }
}

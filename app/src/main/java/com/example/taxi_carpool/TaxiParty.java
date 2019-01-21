package com.example.taxi_carpool;

public class TaxiParty {
    String title;
    String departure;
    String destination;
    Integer numLeft;
    String explanation;

    public TaxiParty(){ }

    public TaxiParty(String title, String departure, String destination, Integer numLeft, String explanation) {
        this.title = title;
        this.departure = departure;
        this.destination = destination;
        this.numLeft = numLeft;
        this.explanation = explanation;
    }
}

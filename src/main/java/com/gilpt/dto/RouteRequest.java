package com.gilpt.dto;

public class RouteRequest {
    private String departure;
    private String destination;
    private String preferredArrivalTime;

    // Getters and Setters
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPreferredArrivalTime() {
        return preferredArrivalTime;
    }

    public void setPreferredArrivalTime(String preferredArrivalTime) {
        this.preferredArrivalTime = preferredArrivalTime;
    }
}

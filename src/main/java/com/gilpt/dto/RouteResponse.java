package com.gilpt.dto;

import java.util.List;

public class RouteResponse {
    private Long routeId;
    private String recommendedLeaveTime;
    private String lastBusTime;
    private List<Object> subPaths; // Replace Object with a specific SubPath DTO later

    // Getters and Setters
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getRecommendedLeaveTime() {
        return recommendedLeaveTime;
    }

    public void setRecommendedLeaveTime(String recommendedLeaveTime) {
        this.recommendedLeaveTime = recommendedLeaveTime;
    }

    public String getLastBusTime() {
        return lastBusTime;
    }

    public void setLastBusTime(String lastBusTime) {
        this.lastBusTime = lastBusTime;
    }

    public List<Object> getSubPaths() {
        return subPaths;
    }

    public void setSubPaths(List<Object> subPaths) {
        this.subPaths = subPaths;
    }
}

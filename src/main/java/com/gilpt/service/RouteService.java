package com.gilpt.service;

import com.gilpt.dto.RouteRequest;
import com.gilpt.dto.RouteResponse;
import com.gilpt.external.OdsayClient;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    private final OdsayClient odsayClient;

    public RouteService(OdsayClient odsayClient) {
        this.odsayClient = odsayClient;
    }

    public RouteResponse findRoute(RouteRequest request) {
        // TODO: Implement ODsay API call and route calculation
        return new RouteResponse();
    }
}

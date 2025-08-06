package com.gilpt.controller;

import com.gilpt.dto.RouteRequest;
import com.gilpt.dto.RouteResponse;
import com.gilpt.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<RouteResponse> getRoute(@RequestBody RouteRequest request) {
        return ResponseEntity.ok(routeService.findRoute(request));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponse> getRouteById(@PathVariable Long routeId) {
        // TODO: Implement logic to fetch a specific route
        return ResponseEntity.ok(new RouteResponse());
    }

    @GetMapping("/users/{userId}/routes")
    public ResponseEntity<Object> getUserRoutes(@PathVariable String userId, @RequestParam(required = false, defaultValue = "5") int limit) {
        // TODO: Implement logic to fetch user's route history
        return ResponseEntity.ok(new Object());
    }
}

package com.gilpt.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {

    @Id @GeneratedValue
    private Long id;

    private String userId;

    private int pathType; // 1: 지하철, 2: 버스, 3: 혼합
    private int totalTime;
    private int totalWalk;
    private int busTransitCount;
    private int subwayTransitCount;

    private LocalDateTime requestedAt;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<SubPath> subPaths = new ArrayList<>();
}

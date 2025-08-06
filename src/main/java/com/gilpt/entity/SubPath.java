package com.gilpt.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class SubPath {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    private int trafficType; // 1: 지하철, 2: 버스, 3: 도보
    private String startName;
    private String endName;
    private int sectionTime;

    @OneToOne(mappedBy = "subPath", cascade = CascadeType.ALL)
    private Lane lane;
}

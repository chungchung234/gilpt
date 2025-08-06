package com.gilpt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Lane {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "sub_path_id")
    private SubPath subPath;

    private String name;         // 노선명 (예: 2호선, 301번)
    private Integer subwayCode;  // ODsay 지하철 코드
    private Integer busCode;     // ODsay 버스 코드 (선택적)
}

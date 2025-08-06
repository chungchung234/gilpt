# 🚌 길찾기 챗봇 서버

> 길찾기 + 막차시간 + 출발 권장 시간 + GPT 기반 안내까지 제공하는 스마트 챗봇 서버

---

## ✅ 프로젝트 개요

이 서버는 사용자로부터 출발지와 도착지를 입력받아 [ODsay 길찾기 API](https://lab.odsay.com/guide/releaseReference)를 통해 대중교통 경로를 계산하고, 
막차 시간과 출발 권장 시간을 추론한 뒤, **GPT API**를 통해 자연어로 안내 메시지를 생성하거나 응답에 필여힌 데이터를 제공하는 **Java Spring 기반 챗봇 백엔드+MCP 서버**입니다.

---

## 🛠️ 기술 스택

| 항목         | 내용                                   |
|------------|--------------------------------------|
| Language   | Java 17                              |
| Framework  | Spring Boot 3.x                      |
| Build Tool | Gradle (Kotlin DSL)                  |
| API Client | WebClient                            |
| ORM        | Spring Data JPA + Hibernate          |
| 외부 API     | ODsay 길찾기 API, OpenAI GPT API        |
| 인증 방식      | API Key 기반 (.env or 환경변수)            |
| DB         | PostgreSQL (운영), H2 (개발/테스트)         |
| 배포         | Docker + AWS EC2                     |
| 모니터링       | Spring Actuator, Prometheus, Grafana |
| 로깅         | Logback                              |
| 문서화        | Swagger                              |


---

## 📂 디렉토리 구조

```
src
├── java/com/example/lastbusbot
│   ├── controller     # REST API
│   ├── service        # 비즈니스 로직
│   ├── dto            # 요청/응답 객체
│   ├── entity         # JPA 엔티티 (Route, SubPath, Lane 등)
│   ├── external       # ODsay, GPT WebClient 연동
│   └── config         # 설정 (WebClient, API Key 등)
└── resources
    ├── application.yml
    └── logback-spring.xml
```

---

## 🔐 환경 변수 (.env or 시스템 환경 설정)

```env
OD_SAY_API_KEY=mQ09UrCkxtLEUNU20XAxpCCSApN5OGKkDQ0jSct6fIo
GPT_API_KEY=your_gpt_key
SPRING_DATASOURCE_URL=jdbc:postgresql://your-host:5432/lastbus
SPRING_DATASOURCE_USERNAME=your_user
SPRING_DATASOURCE_PASSWORD=your_password
```

---

## 🗃️ JPA Entity 설계 (ODsay 길찾기 API 기반)

### ✅ Route

```java
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
```

### ✅ SubPath

```java
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
```

### ✅ Lane

```java
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
```

---

## 🧪 로컬 실행

```bash
# 1. 환경변수 또는 application.yml 구성
cp .env.example .env  # 또는 수동 작성

# 2. 실행
./gradlew bootRun
```

---

## 🐳 Docker 배포 (EC2 등)

```bash
# 1. 빌드
./gradlew build

# 2. Docker 이미지 생성
docker build -t lastbusbot .

# 3. 컨테이너 실행
docker run -d -p 8080:8080 \
  -e OD_SAY_API_KEY=xxx \
  -e GPT_API_KEY=xxx \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://... \
  -e SPRING_DATASOURCE_USERNAME=xxx \
  -e SPRING_DATASOURCE_PASSWORD=xxx \
  lastbusbot
```

---

## 📦 REST API 명세 (예시)

| 메서드 | URI | 기능 설명 | 요청 형식 | 응답 형식 | 상태 코드 |
|--------|-----|-----------|-----------|------------|------------|
| `POST` | `/routes` | 출발지~도착지 기반 경로 탐색 + 막차 시간 + 출발 권장 시간 계산 | `application/json`<br>`{ "departure": "서울역", "destination": "강남역", "preferredArrivalTime": "23:40" }` | `application/json`<br>`{ "routeId": 1, "recommendedLeaveTime": "...", "lastBusTime": "...", "subPaths": [...] }` | `200 OK`<br>`400 Bad Request`<br>`500 Internal Server Error` |
| `GET` | `/routes/{routeId}` | 저장된 특정 경로 상세 정보 조회 | 없음 | `application/json`<br>`{ "routeId": 1, "requestedAt": "...", "subPaths": [...] }` | `200 OK`<br>`404 Not Found` |
| `GET` | `/users/{userId}/routes?limit=5` | 사용자의 최근 경로 요청 이력 조회 | 없음<br>(QueryParam: limit 선택) | `application/json`<br>`[{ "routeId": 1, "departure": "...", "destination": "...", "requestedAt": "..." }]` | `200 OK`<br>`404 Not Found` |
| `POST` | `/chat/messages` | 자연어 입력을 기반으로 GPT로 안내 메시지 생성 | `application/json`<br>`{ "message": "막차 언제야?", "userId": "abc123" }` | `application/json`<br>`{ "reply": "23시 5분 전에 출발하세요." }` | `200 OK`<br>`400 Bad Request`<br>`500 Internal Server Error` |
| `GET` | `/actuator/health` | 시스템 상태 확인 | 없음 | `application/json`<br>`{ "status": "UP" }` | `200 OK` |
| `GET` | `/actuator/prometheus` | Prometheus 메트릭 수집용 엔드포인트 | 없음 | `text/plain` (Prometheus 포맷) | `200 OK` |
---

## 📊 모니터링 설정

### application.yml

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

### prometheus.yml

```yaml
scrape_configs:
  - job_name: 'lastbusbot'
    static_configs:
      - targets: ['localhost:8080']
```

---

## 🧠 주요 기능

- 🚏 ODsay API 기반 **경로 탐색 및 대중교통 정보 수집**
- ⏱️ **막차 시간 판단** 및 **출발 권장 시간 계산**
- 💬 GPT API로 **자연어 기반 응답 생성**
- 🧠 JPA로 사용자 요청/경로 결과 **DB 저장 및 분석**
- 📈 Prometheus + Grafana 기반 모니터링 대시보드 구성

---

## 🧩 확장 아이디어

- 출발 알림 예약 (Spring Batch + Quartz)
- 사용자별 선호 경로 추천 (AI 추천 알고리즘)
- GPT 커스터마이징 (예: 막차 피드백 학습)

---

## 👨‍💻 작성자

이름: 이충현  
이메일: a8207637@naver.com  
GitHub: [https://github.com/chungchung234](https://github.com/chungchung234)

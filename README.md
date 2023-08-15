# marketboro-assignment

# 개발 스펙
- JAVA 17
- Spring Boot 3.1.2
- MySQL
> - 스키마 이름 : test (로컬 환경)
- Lombok
- JUnit5, Mockito
- Gradle

<br>

# ERD

![ERD](https://github.com/iheese/marketboro-assignment/assets/88040158/90eadba0-b203-4ce5-87d7-7b3448438e4b)

- 포인트 엔티티가 적립되어질 때 포인트 상세 엔티티는 적립됩니다.
- 포인트 엔티티가 사용되어질 때 먼저 적립된 포인트부터 사용되며(FIFO)
포인트 상세 엔티티는 각 포인트별 사용 처리된 값을 나누어 저장되게 됩니다. 그리고 사용 후 남은 포인트가 있다면 재저장하여 다음 요청 때 사용할
수 있게 됩니다. 

<br>

# API 명세서

| 유저 데이터 (포인트 총액 포함) 조회 | POST        | /user/getUserPoint |
|-----------------------|-------------|--------------------|
| <b>타입</b>             | <b>변수명</b>  | <b>설명</b>          |
| Long                  | userNo (필수) | 유저 ID              |

```shell
- 예시
{
  "userNo" : 1 
}
```

<br>

| 포인트 적립/사용 내역 조회 | POST       | /point/getPointHistory |
|-----------------|------------|------------------------|
| <b>타입</b>             | <b>변수명</b>  | <b>설명</b>          |
| Long            | userNo (필수) | 유저 ID                  |
| int             | page (필수)  | 페이지 번호                 |
| int             | size  (필수) | 페이지에 들어갈 객체 갯수         |

```shell
- 예시
{
  "userNo" : 1, 
  "page" : 0,
  "size" : 10
}
```

<br>

| 포인트 적립 | POST      | /point/rewardPoint |
|--------|-----------|--------------------|
| <b>타입</b>             | <b>변수명</b>  | <b>설명</b>          |
| Long   | userNo (필수) | 유저 ID              |
| Long   | rewardValue (필수)     | 포인트 적립 금액          |

```shell
- 예시
{
  "userNo" : 1, 
  "rewardValue" : 1000
}
```

<br>


| 포인트 사용 | POST      | /point/usePoint |
|--------|-----------|-----------|
| <b>타입</b>             | <b>변수명</b>  | <b>설명</b> |
| Long   | userNo (필수) | 유저 ID     |
| Long   | usageValue (필수)     | 포인트 사용 금액 |

```shell
- 예시
{
  "userNo" : 1, 
  "usageValue" : 1000
}
```

<br>

- 추가적으로 적립금 유효기간을 구현하였습니다.

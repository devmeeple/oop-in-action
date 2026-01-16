DROP TABLE IF EXISTS customer;
CREATE TABLE customer(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '이름'
);

DROP TABLE IF EXISTS discount_condition;
CREATE TABLE discount_condition(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    policy_id      INT         NOT NULL COMMENT '할인 정책 번호',
    condition_type VARCHAR(10) NOT NULL COMMENT '할인 조건 타입',
    sequence       INT         NULL COMMENT '회차',
    day_of_week    VARCHAR(10) NULL COMMENT '요일',
    start_time     TIME        NULL COMMENT '시작 시간',
    end_time       TIME        NULL COMMENT '종료 시간'
);

DROP TABLE IF EXISTS discount_policy;
CREATE TABLE discount_policy(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    policy_type VARCHAR(10) NOT NULL COMMENT '할인 정책 타입',
    movie_id    INT         NOT NULL COMMENT '영화 ID',
    amount      INT         NULL COMMENT '할인 금액',
    percent     DOUBLE      NULL COMMENT '할인 비율'
);

DROP TABLE IF EXISTS movie;
CREATE TABLE movie(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL COMMENT '영화 제목',
    fee   INT          NOT NULL DEFAULT 0 COMMENT '영화 요금'
);


DROP TABLE IF EXISTS screening;
CREATE TABLE screening(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    movie_id       INT NOT NULL COMMENT '영화 ID',
    sequence       INT NOT NULL COMMENT '회차',
    when_screened TIMESTAMP COMMENT '시작 시간'
);


DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    customer_id    INT NOT NULL COMMENT '고객 번호',
    screening_id   INT NOT NULL COMMENT '상영 번호',
    audience_count INT NOT NULL COMMENT '관람자 수',
    fee            INT NOT NULL COMMENT '요금'
);

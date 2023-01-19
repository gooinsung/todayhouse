# 오늘의 집 클론코딩 팀 프로젝트

<br><br>

## 프로젝트 정보

#### 1. 프로젝트 기간 : 2022.12~ 2023.01 (2주)

#### 2. 참여인원
- IOS : 노균옥
- 백앤드 : 구인성

#### 3. 역할
- AWS EC2 인스턴스, RDS, S3, 가비아 도메인, SSL, ERD 작성, API 명세서 작성, API 구축 담당

#### 4. Language : java, SQL

#### 5. Tools : Inteli j , MySQL Workbench, SpringBoot 2.7.5

#### 6. DataBase : MySQL<br>

#### 7. Environment : AWS(프리티어) EC2 Ubuntu 18.0.4, RDS, ROUTE53, S3, NGINX

#### 8. Skill Set : JWT, Spring Security, JdbcTemplate, BeanValidation

#### 9. API 명세서 : https://docs.google.com/spreadsheets/d/1SXbu_G3MzdjnHe1dAhFsKCaYNRuAiPw_zs1ee79t3QU/edit?usp=sharing

#### 10. 프로젝트 링크 : https://prod.baliserver.shop/home

#### 11. ERD
![todayHouse_20230110_222537](https://user-images.githubusercontent.com/108322891/211563631-765eee42-373a-4601-a594-a6620ca76cba.png)


<br>

## 프로젝트 설명

컴공선배 라이징캠프를 수료하기 위한 마지막 과제, 2주간 팀 클론코딩 프로젝트로 '오늘의집' 어플리케이션을 배정받았습니다.
'오늘의집' 은 이커머스 시스템으로, 오늘날 많은 자취생 및 인테리어와 집을 꾸미는데 관심이 있는 많은 사람들이 찾는 어플리케이션 입니다.
짧은 시간이기에, 또 처음 본 팀원과 함께하는 첫 팀단위 프로젝트 이기에, 최대한 간소화하여 크게 게시글(집 소개) 와 상품 크게 두 파트로 구성하였습니다.
AWS 프리티어로 EC2 에서 ubuntu18.0.4 서버를 사용하였고, RDS에서 MySQL 서버를, S3 서비스의 저장소를 이용하여 이미지 및 파일들을 관리하였습니다. 또 JWT 인증으로 서버의 보안을 관리하였습니다. ERD 는 AqueryTool 을 사용하였고, 구글 스프레드시트를 활용하여 API 명세서를 작성하였습니다.
기본적인 기능으로는, 게시글 작성 수정 조회 삭제, 그리고 회원가입 및 로그인기능, 상품 조회 및 리뷰작성 또 장바구니 추가와 결제 (실제로 결제 API 를 구현하는데 제한이있어 그냥 상품 수량 조절로 결제 기능을 대체하였습니다.) 마지막으로 마이페이지를 구성으로 프로젝트를 진행하였습니다. 로그인을 할 시, JWT 를 발급받아 추후에 모든 요청에 JWT 를 RequestHeader에 첨부하여 보내주면, JWTFilter 로 권한을 확인하도록 하여 보안적인 부분을 구성하였습니다.




## 아쉬운 점
- 장바구니 및 상품 구매 API 에서 아쉬운부분을 찾았다. 실제 결제가 적용되는 서비스가 아니고 약식으로 주문을 하면 상품재고만 줄이는 식으로 구현하다보니, 주문 상태(배송중,배송완료) 나 장바구니 관련 테이블을 따로 구현하지는 않았다. 이 부분을 실제로 구현할때, 어떻게 테이블과 트랜젝션을 처리해야할지 좀 더 고민해봐야 할 것 같다.
- 내 마음대로 작성한 코드이다보니, 실제 협업에서는 예외처리, 트랜젝션처리, 테이블 관리 등을 어떤식으로 구현하는지 적극적인 피드백을 받지 못하였다. 실제로는 서비스의 특징에 따라 사용하는 기술스택이 달라질 수 있을것같다. 결국 실전 경험이 부족해 아쉬움이 남는다.
- Reflesh Token 을 구현하지못하였다.
- 시간이 촉박한 이유로, 많은 API에 JWT 인증 필터 설정을 하지 못하고 '/home' 하나의 API에만 적용을 하였다. 

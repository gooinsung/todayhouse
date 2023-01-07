2022-12-31<br>
Done
- EC2 인스턴스 생성
- ERD 설계(60%)
- 가비아 domain 연결
- 서브도메인 연결(dev,prod)
- RDS 설정
- SSL 인증서 발급(서브도메인도)<br>
To do
- ERD 설계 
- API 명세서 작성
<br>

2023-01-01<br>
Done
- ERD 설계(90%)
- API 명세서 작성
- template 세팅<br>
To do
- ERD 및 API 수정
- API 작성 시작<br>

2023-01-02<br>
Done
- 게시글 상세페이지 조회 API(100%)
- 리뷰 작성 API(80%)
- S3 서버 설정

2023-01-02<br>
Done
- jwt 인증 필터 구현
- 로그인API(100%)
- 회원가입API(100%)
- 상품 전체조회 API(100%)
- 장바구니 조회 API(100%)
- 장바구니에 상품 추가 API(100%)

2023-01-03<br>
- 1차 피드백 요소 수정(장바구니 구현, user테이블 동의 컬럼 삭제,명세서와 실제 데이터 타입 및 이름 설정, 유효성검사 설정)
- 상품 상세조회 API(100%)
- 특정상품 리뷰조회 API(100%)
- 리뷰 작성 API(100%)
- 리뷰 수정 API(100%)
- 리뷰 삭제 API(100%)
- 장바구니 상품 수량 수정API(100%)
- 상품구매 API(100%)
- 장바구니 주문 삭제 API(100%)
- 특정 리뷰 조회 API(100%)
- dev 서버 말고 prod 서버로 

2023-01-05<br>
- 게시글 전체조회 API(100%)
- 특정 게시글 조회 API(100%)
- 게시글 작성 API(100%)
- 게시글 수정 API(80%)

2023-01-06<br>
- 게시글 수정 API(100%)
- 프론트 팀원과 상의하여 게시글 및 상품의 response 값을 수정함(더미데이터 삭제 후 다시 입력하느라 시간을 많이 소요함)
- 게시글 삭제 API(100%)
- 게시글 검색 API(100%)
- 상품 및 게시글 스크랩 API(50%)

2023-01-07<br>
- 상품 조회 API에 추가 response 값 설정
- 독감으로 인해 작업하지 못했습니다..

Question
- 동적쿼리 작성법(사용자의 선택에따라, 검색 키워드가 동적으로 바뀌게 될 경우, 경우에수에 맞도록 컨트롤러에 API 를 작성해야 하는지?)
Answer 
- jdbcTemplate 에서는 명쾌한 해답은 없는듯 하다. 한개의 API에서 동적으로 DAO 를 호출하도록 설계!


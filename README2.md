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
- 위클리 스크럼 진행

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

2023-01-08<br>
- 홈 화면 API(100%)
- 상품 or 게시글 스크랩 API(100%)
- 스크북 조회 API(100%)
- 위클리 스크럼 진행

2023-01-09<br>
- 마이페이지 API(100%)
- 전체 소스코드 다듬기
- 새로운 API 구성
- ERD 수정(필요없는 테이블 삭제)
- 게시글 댓글 조회 API(100%)
- 게시글 댓글 작성 API(100%)
- 주문했던 상품 조회API(100%)

2023-01-10<br>
- 댓글 수정 API(100%)
- 댓글 삭제 API(100%)
- 게시글 좋아요 API(100%)
- 게시글 좋아요 조회API(100%)
- 유저 프로필 수정 API(100%)
- 유저가 남긴 리뷰,댓글 조회 API(100%)
- 상품 검색,카테고리별조회,카테고리별 검색 API(100%)

2023-01-11<br>
- 댓글 작성 시, 부모댓글 작성 가능하도록 수정
- 테스트 데이터들 정리
- 발생하는 Exception 처리

2023-01-12<br>
- 마지막 코드 정리
- 시연영상 촬영
- API 명세서 및 ERD 정리

## 문제
2023-01-10
1. API 작성을 모두 마치고, 서버에서 테스트를 진행하던중 계속하여 Request Method "Get" Not Supported 라는 에러가 발생하였다.
분명 POST 로 로그인을 시도하는 메서드였는데, 아무리 찾아봐도 답이 나오지 않았다. 그래서 다른 API들을 실행하던중, POST 로 보내는
API중, 실행은되지만 post 기능이 아닌, 같은 URL에 GET API 가 호출되었다. 이를 기반으로 검색해보니, 내 도메인은 https://prod.baliserver.shop
이다. nginx 서버설정에서, prod.baliserver.shop 나, http:// 로 오는 URL을 원래 주소로 리다이렉션 할수 있도록 설정하였는데,
리다이렉트 과정에서 메서드가 모두 GET 으로 전송된다는 사실을 알았다. 모든 요청에 https:// 로 경로 수정 후 문제 해결.
2. 로그인시, 다양한 API에 로그인한 유저의 userNum값이 필요한데, 처음 userNum 값을 어떻게 가져와야 할지 문제가 있었다.
원래 SpringSecurity 설정에서 @AuthenticationPrincipal 을 통해 UserDetails 객체로 userNum 을 받을 수 있는데, 이를 간과하고 
필요한 userNum 값을, REST API URL 상에 userNum 을 변수나 쿼리스트링으로 받도록 설계하였다. 프로젝트 막바지에
이를 알아차려 API들의 URL 경로를 수정하기에는 무리가 있을것같아, 로그인 시 Response Header에 jwt와 userNum 값을 같이 넘겨주는
방식으로 처리하였다.
3. 리뷰작성API 작성시, 리뷰에는 사진을 첨부할수도 안할수도 있도록 코드를 작성하였다. 파일을 받을때, @RequestPart(required=false) MultipartFile file
로 받았고,service 에서 if(!file.isEmpty){savedUrl=s3uploader.uploadFiles()} 로, 파일이 없으면 공백을, 파일이 있으면 저장 후 String 값을 넣어주도록 설계하였는데,
계속 file.isEmpty에서 NullPointException이 발생하였다. 아무리 해결해보려해도 같은 상황만 반복되었기에, 리뷰에는 파일첨부를 하는것으로 마무리하였다.

### 해결
3. 파일 자체를 안보내는게 아니라, 파일을 null로 보내도록 설정하면 오류없이 잘 작동하는것을 확인하였다..

Question
- 동적쿼리 작성법(사용자의 선택에따라, 검색 키워드가 동적으로 바뀌게 될 경우, 경우에수에 맞도록 컨트롤러에 API 를 작성해야 하는지?)
Answer 
- jdbcTemplate 에서는 명쾌한 해답은 없는듯 하다. 한개의 API에서 동적으로 DAO 를 호출하도록 설계!


2주차 피드백
- jwt 설정 할것
- REST API URL 명명규칙에 따라 동사형이 아닌 명사형 URL 로 변경할것
- REST API URL 명명규칙에 따라 URL 을 보고 API의 동작에 대한 힌트를 얻을 수 있게 할 것
- 문자,이메일 전송 및 결제 API 테스트 연동을 하면 좋을듯

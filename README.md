 # Linegames Homework
 * [Build](#build)
 * [Run](#run)
 * [Features](#features)
 * [Caution](#caution)
 
 ## Build
 ```
# Git 프로젝트를 내려받을 Path로 이동
cd <Path>/

# 원격 저장소에서 Git 프로젝트를 클론
git clone https://github.com/yechanpark-private/LinegamesHomwork.git

# 클론받은 Git 프로젝트 내부로 이동
cd <Path>/LinegamesHomework

# 그래들을 사용하여 빌드
gradle build
```

## Run
```
# 빌드 후 jar 파일이 생성된 경로로 이동  
cd <Path>/LinegamesHomework/build/libs

# jar 파일 실행
java -jar LinegamesHomwork-0.0.1-SNAPSHOT.jar

# http://localhost:8080/client 접속
 ```
 - [localhost](http://localhost:8080/client) 접속
  
  
 ## Features
 ### 화면구성
  - 메인 뷰
    - 게시판, 게시글 작성 폼 등 출력
  - 네비게이션 바
    - 공통
      - 전체 게시판 조회
    - 미인증 유저
      - 로그인
      - 회원가입
    - 인증 유저
      - username 표시
      - 권한 표시
      - 내정보 수정
      - 로그아웃
    - ADMIN 유저
      - 게시판 종류 추가
  
 ### 인증
  - 로그인
    - 로그인 Validation
       - 로그인 실패 시 로그인창으로 리다이렉션, 실패 이유 Error Message 출력
         - username이 존재하지 않는 경우
         - password가 틀린 경우
         - username은 기존 값 유지
    - 동일 username 중복 로그인 방지
  - 로그아웃
  - 회원가입
    - 회원가입 Validation
       - username기준 중복 가입 불가
    - 패스워드 암호화 저장
  - 권한
    - MEMBER (일반 사용자, 회원가입 시 Default)
    - ADMIN (관리자)
  - 패스워드 찾기
    - username기반 패스워드 변경
  - 내 계정 정보 수정
    - username 변경
  
 ### 게시판
  - 게시판 종류 추가
  - 게시판 관리 (ADMIN 기능)  
    - 게시판 Title 수정
    - 게시판 URI 수정
    - 게시판 공개/비공개 설정
      - 비공개 게시판 : ADMIN만 조회 가능
  - 게시판 게시글 페이징
    - 최상위 부모 게시글을 기준으로 페이징
      - 페이징 기준은 글 작성 시간이 가장 최근인 것부터 시작 (등록시간 내림차순 정렬)
      - 자식 게시글(답글)은 페이징 기준으로 삼지 않음
    - 페이징 바
      - 페이지 번호 기반 조회
        - Query String Parameter를 통한 페이징
      - Next / Previous 조회
        - 한 게시판에서 최대로 보여줄 수 있는 최대 페이지는 5페이지 (편의 상 적게 설정)
          - 6페이지부터는 Next버튼을 통해 다음 페이지 셋으로 이동해야 함
        - 한 페이지에서 최대로 보여주는 게시글은 2개 (편의 상 적게 설정)
          - ex. 5개 페이지 x 페이지 당 2개 게시글 = 10개의 게시글이 존재
        - Next : 현재 페이지 + 최대 페이지 갯수 만큼 다음 페이지로 이동
          - ex. 1페이지 + 최대 페이지 갯수 (5) = 6페이지로 이동
        - Previous : 현재 페이지 - 최대 페이지 갯수 만큼 이전 페이지로 이동
          - ex. 6페이지 - 최대 페이지 갯수 (5) = 1페이지로 이동
          
 ### 게시글
  - 게시판 리스트로 돌아가기
  - 게시글 CRUD
    - 게시글 조회
      - 제목
      - 내용
      - 작성자
      - 작성시간
      - 댓글 조회
    - 게시글 등록
      - 권한 : MEMBER, ADMIN
    - 게시글 수정
      - 권한 : MEMBER(본인의 글만 수정 가능), ADMIN
    - 게시글 삭제
      - 권한 : MEMBER(본인의 글만 삭제 가능), ADMIN
    - 게시글 답글 작성
    
 ### 댓글
  - 댓글 CRUD
    - 댓글 조회
      - 내용
      - 작성자
    - 댓글 등록
      - 권한 : MEMBER, ADMIN
    - 댓글 수정
      - 권한 : MEMBER(본인의 댓글만 수정 가능), ADMIN
    - 댓글 삭제
      - 권한 : MEMBER(본인의 댓글만 삭제 가능), ADMIN
    
 ## Caution
 ### Clone & Build
  - 프로젝트는 Git을 사용해 Clone해야 하므로 Git이 설치되어 있어야 함
  - 빌드는 gradle을 사용해서 Build해야 하므로 gradle이 설치되어 있어야 함
  
 ### ADMIN 계정
  - username : admin
  - pw : 1234
    
 ### Embedded-Redis
  - 내부적으로 Embbeded Redis 서버를 활용
  - port는 REDIS 기본 포트(6379)를 사용
    - 포트가 충돌나지 않도록 해당 포트를 사용하지 않도록 pc설정 필요 
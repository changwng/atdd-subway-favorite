# 지하철 회원 관리 및 즐겨찾기

## 1단계 회원관리 기능

### 요구사항

-[x] 회원 정보를 관리하는 기능 구현
-[x] 자신의 정보만 수정 가능하도록 해야하며 로그인이 선행되어야 함
-[x] 토큰의 유효성 검사와 본인 여부를 판단하는 로직 추가
-[x] side case에 대한 예외처리 
-[x] 인수 테스트와 단위 테스트 작성
-[x] API 문서를 작성하고 문서화를 위한 테스트 작성
-[x] 페이지 연동

### 기능목록

-[x] 회원가입
    -[x] 비밀번호 확인
-[x] 로그인
-[x] 회원정보 조회
-[x] 수정
-[x] 삭제

## 2단계 즐겨찾기

### 요구사항

-[x] 즐겨찾기 기능을 추가(추가,삭제,조회)
-[x] 자신의 정보만 수정 가능하도록 해야하며 로그인이 선행되어야 함
-[x] 토큰의 유효성 검사와 본인 여부를 판단하는 로직 추가(interceptor, argument resolver)
-[x] side case에 대한 예외처리 필수
-[x] 인수 테스트와 단위 테스트 작성
-[x] API 문서를 작성하고 문서화를 위한 테스트 작성
-[x] 페이지 연동

### TO-DO

-[x] 즐겨찾기 인수테스트
    -[x] 회원가입
    -[x] 로그인
    -[x] 즐겨찾기 추가
    -[x] 즐겨찾기 조회
    -[x] 즐겨찾기 삭제
-[x] 즐겨찾기 단위테스트
    -[x] 예외처리
        -[x] 동일 경로
-[x] 즐겨찾기 문서화
-[x] 페이지 연동

### 추가 TO-DO

-[x] 즐겨찾기 중복 예외 처리
-[x] 나의 정보
    -[x] 이름만 수정가능
    -[x] 수정을 위해선 비밀번호를 입력해야함.
    -[x] 이메일은 변경 불가.
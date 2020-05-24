# 1단계 - 회원관리 기능
## 회원정보 수정 기능
### 요구 사항
- 회원 정보를 관리하는 기능 구현
- 자신의 정보만 수정 가능하도록 해야하며 로그인이 선행되어야 함
- 토큰의 유효성 검사와 본인 여부를 판단하는 로직 추가
- side case에 대한 예외처리
    - 회원가입
      - 로그인 상태에선 회원가입이 불가능해야 한다
      - 모든 입력칸이 채워져야 한다
      - 비밀번호와 비밀번호 확인이 일치해야 한다
      - 입력한 이메일과 중복되는 이메일이 없어야 한다
    - 로그인
      - 로그아웃 상태에서만 로그인이 가능해야 한다
      - 모든 입력칸이 채워져야 한다
    - 회원정보 수정
      - 로그인 상태에서만 수정이 가능해야 한다
      - 모든 입력칸이 채워져야 한다
      - 비밀번호와 비밀번호 확인이 일치해야 한다
    - 회원정보 삭제
      - 로그인 상태에서만 삭제가 가능해야 한다
- 인수 테스트와 단위 테스트 작성
- API 문서를 작성하고 문서화를 위한 테스트 작성
- 페이지 연동

### 기능목록
1. 회원가입
2. 로그인
3. 로그인 후 회원정보 조회/수정/삭제

# 2단계 - 즐겨찾기 기능
## 즐겨찾기 기능
### 요구사항
- 즐겨찾기 기능을 추가(추가,삭제,조회)
- 자신의 정보만 수정 가능하도록 해야하며 로그인이 선행되어야 함
- 토큰의 유효성 검사와 본인 여부를 판단하는 로직 추가(interceptor, argument resolver)
- side case에 대한 예외처리 필수
- 인수 테스트와 단위 테스트 작성
- API 문서를 작성하고 문서화를 위한 테스트 작성
- 페이지 연동
### 기능 목록
1. 즐겨찾기 추가
2. 즐겨찾기 목록조회/제거
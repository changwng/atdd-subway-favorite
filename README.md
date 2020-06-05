# 1단계 - 회원관리 기능

## 요구 사항

- 회원 정보를 관리하는 기능 구현
- 자신의 정보만 수정 가능하도록 해야하며 로그인이 선행되어야 함
- 인수 테스트와 단위 테스트 작성
- 토큰의 유효성 검사와 본인 여부를 판단하는 로직 추가
- side case에 대한 예외처리
- API 문서를 작성하고 문서화를 위한 테스트 작성
- 페이지 연동

## To-do list
- Login 멤버의 회원 관리
    - [x] 인수 테스트 구현
        - [ ] Side Case 테스트 구현
    - [x] Controller 테스트 구현
        - [x] API 문서화
        - [ ] Side Case 테스트 구현
    - [x] Service 테스트 구현
        - [ ] Side Case 테스트 구현
    - [x] Domain 테스트 구현
        - [ ] Side Case 테스트 구현
    - [x] Interceptor 테스트 구현
        - [x] Side Case 테스트 구현
    - [x] Front와 연동
        - [x] 회원가입
        - [x] 로그인
        - [x] 회원정보 수정
        - [x] 탈퇴
    - 추가 기능
        - [ ] 중복되는 테스트 변수 (properties 에 추가 고려)        
        - [ ] Request하는 기기 정보로 검증 추가
        - [ ] 로그아웃
        - [ ] 즐겨찾기 데이터 수정 (Station 이름 -> Station id)
            - [ ] back
            - [ ] front
        
# 2단계 - 즐겨찾기 기능
        
## 요구사항

- 즐겨찾기 기능을 추가(추가,삭제,조회)
- 자신의 정보만 수정 가능하도록 해야하며 로그인이 선행되어야 함
- 토큰의 유효성 검사와 본인 여부를 판단하는 로직 추가(interceptor, argument resolver)
- side case에 대한 예외처리 필수
- 인수 테스트와 단위 테스트 작성
- API 문서를 작성하고 문서화를 위한 테스트 작성
- 페이지 연동

## To-do list
 - 즐겨찾기 기능 api 구현 (추가, 삭제, 조회)
    - [x] 인수테스트 작성
    - [x] 컨트롤러 구현
        - [x] 컨트롤러 테스트
        - [x] API 문서화
    - [x] 서비스 구현
        - [x] 서비스 테스트
    - [x] DB, Entity 구현
    - [x] 프론트 연동
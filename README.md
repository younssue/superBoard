# readme

# 🚀 [superBoard](https://github.com/younssue/superBoard)

### ⚙️ 개발환경

- IntelliJ IDEA Community Edition
- amazon corretto open jdk 11
- mysql 8
- gradle
- JPA
- Tomcat 9
- Spring Security/JWT

### ✅ 주요기능

### [1. User](https://github.com/younssue/superBoard/tree/main/src/main/java/com/Super/Board/user)

Spring Security/JWT

- 회원가입
- 로그인
- 로그아웃

### [2. Post](https://github.com/younssue/superBoard/tree/main/src/main/java/com/Super/Board/post)

로그인 유저 기반 게시판

- 게시글 작성
    - JWT 토큰 에서 email 값을 받아와 author 로 주입
- 게시글 전체 목록 불러오기
- 게시글 검색 기능 (작성자 email 기반 검색)
    - api 와 동일한 이메일 유저의 게시글 목록 출력
- 게시글 수정
    - 로그인한 유저와 동일한 작성자 일때만 작성한 댓글 수정 가능
- 게시글 삭제
    - 로그인한 유저와 동일한 작성자 일때만 작성한 댓글 삭제 가능
    

### [3. Comment](https://github.com/younssue/superBoard/tree/main/src/main/java/com/Super/Board/comment)

로그인 유저 기반 댓글

- 댓글 작성
    - JWT 토큰 에서 email 값을 받아와 author 로 주입
- 댓글 목록 불러오기
- 댓글 수정
    - 로그인한 유저와 동일한 작성자 일때만 작성한 댓글 수정 가능
- 댓글 삭제
    - 로그인한 유저와 동일한 작성자 일때만 작성한 댓글 삭제 가능

### [4. 포스트맨API 명세서](https://documenter.getpostman.com/view/29522798/2s9YCBtUUK)


### [5. 프로젝트 API 시연 영상 ](https://youtu.be/qkseVBgtcYY?si=izoickDZej5uV2l2)

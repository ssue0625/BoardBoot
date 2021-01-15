# 게시판 진행하며 발생했었던 에러, 정보 정리

## 404 뜨며 dispatcherservlet 오류
- servlet-context.xml 에 추가 작성
	- xmlns:mvc=http://www.springframework.org/schema/mvc 추가
	- <mvc:annotation-driven/> 추가 <br/> 둘 다 작성하였더니 정상 작동

## Mapper.xml 에서 부등호 >, < 작성 시 에러 발생
- 부등호 > 은 &lt 로 작성	
- 부등호 < 은 &lt 로 작성

## @GetMapping import 되지 않는 경우
- @RequestMapping(method = RequestMethod.GET) 방식으로 사용

## <% %> JSP 태그
- dto를 import 하지 않아 에러 발생 
  - <%@ page import =”com.mycompany.board.dto.board%> jsp 상단에 작성
- <% 자바 코드 %>
- <%= 값 %>

## form 전송하기 전 함수 실행하기 
```html
<form onsubmit =”checkForm( )”>
```
```html
<form onsubmit = “return checkForm( )”>
```

## jsp에서 게시글 번호 작성 방법
```html
<c:forEach var = “board” items=”${boardList}” varStatus=”i”>
```
```
${totalRowNum-(pageNo – 1) * rowsPerPage + i.count}  => 1, 2, 3, 4, 5 ...
```
- i.index : 0부터의 순서
- i.count : 1부터의 순서

## DTO -> Lombok 설치 후 프로젝트 빨간 엑박 
- 프로젝트 우 클릭 -> maven -> update Project
- 그래도 계속 뜬다면 -> 자바 버전들이 맞게 되어있는지 확인 하고 아니라면 변경하기

## Lombok @DATA 사용 지양하기
- @Getter 와 @Setter 사용하기
	- 스택오버플로우 발생할 수 있음
	- 안전성 위험

## 조회수 컬럼의 문제점
- 새로 고침 키를 연타하게 된다면 ??
	- 쿠키 사용
	- @CookieValue
- 다른 컴퓨터에서 로그인 한다면 쿠키는? <br/>
A컴과 B컴이 있을 때 B컴에서 로그인 해서 본다면 쿠키는 저장되는가.
	- ID조회
	- 더 심한 경우 IP조회
		- select 하고 다르다면 insert나 update
- 캐시 지우기를 했는데 쿠키가 올라가는 경우도 있다고 한다. 

## 수정을 했는데 수정이 적용이 안되는 경우
- 캐시가 남아있기 때문
	- 브라우저 : cache 날리기

## 비밀번호를 DB에 저장하는 경우
- 비밀번호를 암호화 해서 저장하기
- controller에서 비밀번호 비교를 해야하는 경우
	- 암호화한 비밀번호를 복호화해서 비교하는 것이 아니라 <br/> 복호화된 비밀번호를 암호화해서 암호화 된 비밀번호끼리 비교하기

## SpringUtils.isEmpty => deprecated 더 이상 사용 X
- hasLength 나 hasText 로 변경해서 사용하기

## 게시글 작성 시 날짜가 들어가지 않음
1. @PrePersist 사용
	- insert 할 때는 날짜가 들어갔지만 update 하면 null 값으로 수정됨
2. @Column(insertable = true, updatable = false) <br/>
	Private Date date;
	- insert할 때 필드 사용하고 update할 때에는 사용하지 않겠다. <br/> 수정을 해도 null 값으로 변하지 않음
- @PrePersist 
	- DB에서 insert문 실행되기 전에 호출된 콜백 메서드로 모델이 생성되는 시점에 먼저 실행된다

## Spring security 이용했을 때 rawPassword cannot be null 에러 발생
- 확인하니 클래스 전체 null 값 넘어옴
1. MemberDto클래스 안에 toEntity(){} 생성
2. Member클래스에 @AllArgsConstructor와 @NoArgsConstructor 작성

## Pom.xml 변경 후 적용되지 않는 경우
- Maven – project update O 
	- pom.xml 다시 빌드
- Project – clean X
	- 이클립스가 사용하고 있던 정보나 class를 전부 삭제 후 다시 생성
	- 이것은 pom.xml을 빌드 하는 것이 아니다.

## Spring boot 프로젝트 배포
- war
	- 자바서버 페이지, 자바 서블릿, 자바 클래스, XML, 파일, 태그 라이브러리, 정적 웹 페이지 (HTML 관련 파일) 및 <br/> 웹 애플리케이션을 함께 이루는 기타 자원을 한데 모아 압축
	- = jsp 페이지가 있다면 war로 배포해야 함
	- 웹 사이트 배포를 할 때 주로 사용	
	1. pom.xml <packaging>war</packaging>작성 
	2. BoardBootApplication.java 에서 SpringBootServletInitializer 상속받고 메소드 오버라이딩 하기
	3. Maven install 실행하면 war 파일 생성된다. 
	4. 톰캣 설치
	5. 톰캣의 webapps 폴더 안에 Root.war 파일 배포 후 톰캣 실행 하면 완료
- jar
	- 자바 클래스 파일과, 클래스들이 이용하는 관련 리소스 및 메타데이터를 하나의 파일로 모아 압축
	- 자바 플랫폼에 응용 소프트웨어나 라이브러리를 배포할 때 사용 
	- spring boot 프로젝트는 내장 서버가 있기 때문에 jar 파일로 주로 배포한다. 
	- 서버 설치 필요 없다. (톰캣 없어도 실행 잘 됨)
	1. pom.xml <packaging>jar</packaging>작성
	2. Maven install 실행하면 jar 파일 생성된다.
	3. cmd 창에서 java -jar 파일명.jar 하면 실행 완료

## <script> 의 작성 위치
- <script>를 < head > 부분에 작성했을 때 script가 천줄이라면? 스크립트를 읽는 동안 화면은? <br/> <script>는 < body > 밑에 쓰기. 위도 가능
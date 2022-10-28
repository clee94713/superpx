# Tmax Edu Spring Project BoilerPlate

| 작성/변경일 | 변경사항 | 작성자 |
| --- | --- | --- |
| 2022.02.17 | 최초 작성 | 나은총 |
| 2022.02.23 | single/multiple datasource 분리 | 나은총 |

## 개요
- Tmax Edu AE본부 스프링 백엔드 프로젝트의 스켈레톤
- Single Datasource 사용

## 프로젝트 복제 방법
### 1. Clone 및 프로젝트 명 변경
- 필요에 따라 원하는 branch에서 clone 수행
  - 루트 폴더 명은 clone 시 원하는 이름으로 지정해서 사용할 것. 스크립트에서 변경해주지 않음.
- intelliJ, eclipse 등을 이용해서 refactor 하거나, 아래 내용대로 스크립트를 이용해서 프로젝트 이름을 변경
- 스크립트 사용 시
  - 프로젝트 폴더 내 renameProject.py 파일에서 newProjectName 값 변경
  - `python3 renameProject.py` 명령어 실행
  - 정상적으로 프로젝트 실행 여부 확인 후 renameProject.py 는 삭제해도 무방함

### 2. 예제 코드 확인
- (todo) 예제 코드에 대한 설명 작성

## Versions

* Java8
* SpringBoot 2.6.3
    * JPA
    * WEB
    * Security
    * WebFlux
    * TEST
* Gradle 7.4
* QueryDSL 1.0.10
* Lombok 1.18.22
* SpringDoc OpenAPI 1.6.6
  * Swagger 3
* ModelMapper 2.3.8
* JWT 3.18.1
  * JJWT 0.9.1
* SLF4J 1.7.35
* LogBack 1.2.10


## Directory Architecture

```bash

패키지 구조
src.main.java.package
  ├── data                                                // 데이터 관련 코드
  │   │
  │   └── primary                                         // 첫번째 데이터 소스에 종속되는 코드
  │       ├── model                           
  │       │   ├── entity                                  // 테이블 엔티티
  │       │   └── enums                                   // 테이블 관련된 enum
  │       ├── repository                                  // 테이블 엔티티 종속 기본 repository 코드 작성
  │       └── PrimaryQuerydslRepositorySupport.java       // 첫번째 데이터 소스를 위한 QuerydslRepositorySupport
  │    
  ├── domain                                              // api 도메인
  │   │
  │   ├── domain1                                         // 도메인 단위로 폴더 구분(ex. member / admin 등)
  │   │   ├── controller                                  // 컨트롤러 코드 작성
  │   │   ├── dao                                         // 기본 repository에 해당하지 않는 repositorySupport 등 작성
  │   │   ├── dto                                         // DTO
  │   │   └── service                                     // 서비스 코드 작성
  │   │
  │   └── domain2                                         // 상동.
  │       ├── controller
  │       ├── dao
  │       ├── dto
  │       └── service
  │
  └── global                                              // 프로젝트 전체에 해당하는 코드를 포함
      │
      ├── config                                          // 설정
      │   ├── ApplicationCommonConfig.java                // 프로젝트 공통 설정(ex. modelMapper)
      │   ├── DataSourcePrimaryConfig.java                // 첫번째 데이터 소스에 대한 설정      
      │   ├── QueryDslConfig.java                         // queryDSL 설정     
      │   ├── SecurityConfig.java                         // 보안 관련 설정(비밀번호 암호화, 웹 기본 보안 등)
      │   ├── ServletContextConfig.java                   // WebMVC Context 관련 설정 
      │   ├── SwaggerConfig.java                          // Swagger 관련 설정
      │   └── WebClientConfig.java                        // WebClient 설정
      │
      ├── error                                           // 오류 처리
      │   ├── exception                                   
      │   │   ├── BusinessException.java                  // custom exception class
      │   │   └── ErrorCode.java                          // 프로젝트에서 정의하는 오류 코드 리스트
      │   └── GlobalExceptionHandler.java                 // 오류 발생 시 핸들링을 위한 클래스
      │
      ├── interceptor                                     
      │   └── ApiInterceptor.java                         // 기본 interceptor, 
      │                                                   // pre에 jwt 핸들링, after에 api request/response에 대한 처리를 포함함
      ├── response                                     
      │   ├── DataResponse.java                           // 정상 처리 시 데이터를 포함한 Default Response 형태에 대한 정의
      │   └── ErrorResponse.java                          // 오류 발생 시 응답 형태에 대한 정의 
      │
      └── utils
          ├── CustomServletWrappingFilter.java            // API Request, Response를 처리하기 위한 모듈
          ├── InitialApplicationRunner.java               // Application Runner
          ├── JwtTokenProvider.java                       // JWT 관련 모듈
          └── QueryDslUtil.java                           // QueryDSL 관련 모듈

...

그 외 프로젝트 구조
          
├ libs
│ └── tibero6-jdbc.jar                                    // 티베로 드라이버
├ logs                                                    // 로그 저장 경로, info.log / error.log 로 구분하려 rolling save
│
├ src.main.resources
│ ├── ...
│ ├── application.yml                                     // 프로젝트 설정
│ └── logback-spring.xml                                  // Logback 설정
│
├ ...
├ build.gradle
├ renameProject.py                                        // 프로젝트 이름 변경을 위한 파이썬 스크립트
└ ...

```

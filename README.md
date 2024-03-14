<div align=center>
  
# HOSP Rez
![삼일병원](https://github.com/jiyeon950510/security/assets/122354247/e29709b9-fa3b-403b-9270-c59646c24492)

</div> 

> 병원 진료과, 의사 관리와 고객들이 직접 원하는 의사를 선택해 예약할 수 있는 시스템 입니다.
> > 개발기간 [2024.03.13 ~ ]
> > 
> > 팀원 : `정수연` `오현서` `박지연`
> > 
> > 배포 주소 :
</br>
> 요구사항
> > Spring boot 프레임 워크 사용
> > 
> > 시큐리티, OAuth 구현
> > 
> > 이미지 첨부 게시판 구현



### 목차
1. [기술스택](#기술스택)
2. [GIT 전략](#git-전략)
3. [3.모델링](#모델링)
4. [4.ERD](#erd) 


## 기술스택 
### BackEnd
<img src="https://img.shields.io/badge/JDK 21-0094F5?style=for-the-badge&logo=openjdk&logoColor=black?labelColor=white"> <img src="https://img.shields.io/badge/Springboot 3.2.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=black"> <img src="https://img.shields.io/badge/Spring Security-071D49?style=for-the-badge&logo=springsecurity&logoColor=white"> 

### FrontEnd 
<img src="https://img.shields.io/badge/JSP-302683?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=black"> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/JQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=black">

### DB 
<img src="https://img.shields.io/badge/MyBatis-4B5562?style=for-the-badge&logo=&logoColor=black"> <img src="https://img.shields.io/badge/MSSQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 

### IDE 
<img src="https://img.shields.io/badge/Intell J-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> 

## GIT 전략 
![Untitled (3)](https://github.com/jiyeon950510/newBlog/assets/122354247/183e7ef1-d5dd-4efc-bb0b-1ebfc3f3ea39)
> 1개월 이상의 긴 호흡으로 개발하여 주기적으로 배포, QA 및 테스트,
>  hotfix 등 수행할 수 있는 여력이 있는 팀이라면 git-flow가 적합하다.

## 모델링 
- 유저정보 `User`
  
- 대시보드(메인) `Main`
  
- 병원 정보 `HospInfo`
    - 진료과 `Dep`
    - 의사 `Doctor`
      
- 진료 예약(스케쥴) `Rez`
    - 예약 `Booking`
    - 리뷰/추천 `Review`

## ERD 
[ERD Cloud](https://www.erdcloud.com/d/BXo7c4yv4q2XZLXAQ) 
![Untitled (4)](https://github.com/jiyeon950510/newBlog/assets/122354247/2b389a46-e1ed-4988-970b-802f70f4a281)

[![Untitled (4)](https://github.com/jiyeon950510/newBlog/assets/122354247/2b389a46-e1ed-4988-970b-802f70f4a281)](https://www.erdcloud.com/d/BXo7c4yv4q2XZLXAQ)


## 잔디
<img src="http://mazandi.herokuapp.com/api?handle=jiyeon0510&theme=warm"/> 


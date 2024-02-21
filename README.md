# README 현재 수정 중!! 

# BobMate-Server
server repository for 'BobMate'🍚
![GOMCAM 20240222_0252490731](https://github.com/hwnooy/BobMate-Server/assets/93791124/a7639564-8560-47fa-96b4-3c22e44ec8ae)

## 개발 일정, 스택 
[개발일정] 2024//1/9 ~ 2024/2/18
[구현 플랫폼] 웹
[기술 스택] Spring Boot3
[팀원] PM 1명, Design 1명, FE 3명, BE 3명 

## 서비스 소개
밥 친구는 
일반상황에서는 

### 팀에서 맡은 역할 
##### 1) 일반상황, 특정상황에 따른 컨텐츠 추천기능
![GOMCAM 20240222_0256350033](https://github.com/hwnooy/BobMate-Server/assets/93791124/8a397e4d-5e6f-45f3-bcd3-4404a059c6f9)

###### 추천 로직 코드 설명 
- 일반상황 
: Content DB(contentId, GenreList, img_url, link_url. name, contentType 속성)에 있는 데이터 중, 사용자가 선택한 컨텐츠 타입에 따른 
1차적 핉터링. 여기에서 감정에 따른 추천된 장르
- 특정 상황 
:

#### 2) 사용자 피드백에 따른 특정상황 4개 만들기
  : 음식, 장르, 감정으로 특정상황 생성
  : 서비스에서 보이는 4가지 컨텐츠는 사용자들이 등록한 데이터 중 음식+감정이 겹치는 코멘트로 만들어주고,
    가장 많이 등장하는 장르에 해당하는 컨텐츠를 해당 특정상황을 선택했을 때 추천해준다.

  [사용자 피드백이 없을 때]  
  ![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/520e1d85-eb57-4f42-a1a2-ec5262935a14)

  [사용자 피드백이 적용됐을 때]  
  ![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/4ffa2ebf-f894-40b4-933b-c5e5137881d9)

  [데이터베이스 상황]
  ![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/69f0b8fd-6072-45b5-8e5c-9870819a23d6)


#### 3) 평가 기능 
: 추천시, 다음 추천에 나올 가능성을 높임 / 비추천시 다음 추천에 해당 컨텐츠를 제외한 컨텐츠 추천
: 사용자가 추천 엄지를 누르면, 해당 컨텐츠에 대해서 Evaluation DB의 is_good(좋은지 안좋은지 true/false) 속성에 true로 저장됨 / 비추천 엄지면 false
: 평가 Create, Update, Delete 기능 생성 + 추천 로직에 해당 평가 DB를 통해 같은 상황으로 컨텐츠 추천 요청시 평가 반영 후 추천 

[평가 화면]
![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/50831adb-d875-4cb3-869f-90098fb657eb)

[평가 생성]
![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/e5ca5860-e353-41cf-abaf-153df6598797)

[평가 수정]
![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/43aa4f87-d9b8-49b8-bf6e-ebb213bfd557)

[평가 삭제]
![image](https://github.com/hwnooy/BobMate-Server/assets/93791124/be9bfa6e-330b-4cb2-a984-6d7346985b73)

### API 명세서 
https://scrawny-icebreaker-16b.notion.site/API-401ce15127ec4662ac59b83559608335?pvs=4 

### API PostMan 시연영상 
최종 mapping 주소와 조금 다르지만 Request에 따른 Response가 잘 따라옴을 확인할 수 있습니다. 
https://drive.google.com/file/d/1WJ97ikhkBpxFETkvdbgs0CYSZ8LsUpvG/view?usp=sharing

### 아쉬웠던 점, 나중에 보완할 점 
데모데이때 중앙 서버 파트장님이 오셨는데, HTTPS를 안 썼던 이유를 물어보셨다. 프론트와의 협업으로 스프링 서버 파트로 프로젝트는 이번이 처음이라, 배포는 내가 못했는데
나중에 서버 파트로 다시 프로젝트를 하게 된다면, HTTPS로 보완을 더 강화해서 발전시킬 것. 

크롤링을 못해서 content DB에 testCode로 데이터를 추가했는데, 크롤링 관련 경험을 나중에 더 쌓아서 더 좋은 서비스를 만들고 싶다. 

### AWS Server Error Log 기록 
white label error
502 Bad Gate error 

### 느낀 점 
마감기한이 있고, 팀프로젝트이면, 팀의 속도에 따라가야함. 프론트와의 소통도 중요하다. 내가 어떻게 설계했고 헤더에 어떤 내용이 추가되어야하는지 전달을 해줘야한다. 
=> 헤더에 어떤 정보가 들어가는지도 공부하기! 
1인분을 하고 싶단 마음으로 구현이 안 되어도 혼자 끙끙 고민하지 말고 팀원에게 빨리빨리 물어봐라. 그것이 오히려 팀에게 도움이 되는 행동이다. 
서버가 여러번 터졌었는데 AWS에 대해 공부가 더 필요하다고 느꼈고 error log를 읽을 수 있는 경험을 하게 되어서 좋았다. 
추천로직을 코드를 짜기 힘들었는데, 스프링에 대한 공부와 주어진 상황을 해결하는 알고리즘 문제 해결능력을 키워야겠다고 생각했다. 

### 프로젝트 이후 보완이 된 점 기록 ... ing 

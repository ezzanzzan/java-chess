# java-chess
체스 게임 구현을 위한 저장소

# 요구 사항 정의

## 체스말
- [ ] 각각의 말이 흑백 여부 저장
- [ ] 폰
  - [x] 앞으로 한 칸 이동
    - [ ] 앞에 말이 있는 경우 이동 불가
  - [x] 처음 이동인 경우 두 칸 이동 가능
  - [ ] 전방의 대각선으로 이동하여 kill
- [ ] 룩
  - [ ] 상하좌우로 이동, kill
    - [ ] 뛰어 넘기 불가
- [ ] 나이트
  - [ ] 나이트 이동 방식으로 이동, kill (뛰어 넘기 가능)
- [ ] 비숍
  - [ ] 대각선으로 이동, kill
    - [ ] 뛰어 넘기 불가
- [ ] 퀸
  - [ ] 상하좌우, 대각선으로 이동, kill
    - [ ] 뛰어 넘기 불가
- [ ] 킹
  - [ ] 3x3 내에서만 이동, kill
    - [ ] 공격받을 자리로 이동 불가
    
## 체스 보드
- [x] (x,y) 좌표 를 정의
    - [x] 가능한 x 좌표를 enum 으로 정의 (a~h)
    - [x] 가능한 y 좌표를 enum 으로 정의 (1~8)
- [x] 체스보드 구현 ((x,y) 좌표에 체스말 매핑)
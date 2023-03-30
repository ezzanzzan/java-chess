# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

## [1단계 미션]

### 기능 요구사항

- Piece
    - [x] 역할을 갖는다.
    - [x] 진영을 갖는다.
        - 진영은 검은색(대문자)과 흰색(소문자) 편으로 구분한다.

- Square
    - [x] 파일을 갖는다.
        - 가로 위치는 왼쪽부터 a ~ h이다.
    - [x] 랭크를 갖는다.
        - 세로 위치는 아래부터 1 ~ 8이다.

- Board
    - [x] 보드는 키가 Square이고 값이 Piece인 Map을 갖는다.
    - [x] 체스 게임을 할 수 있는 체스판을 초기화한다.

## [2단계 미션]

### 기능 요구사항

- Board
    - [x] 이동 경로상에 말이 존재할 경우 움직일 수 없다.
    - [x] 이동 경로상에 말이 존재하지 않고 이동 위치에 같은 팀 말이 존재하지 않고 이동하려는 말의 가동 범위 내에 있을 경우 이동할 수 있다.

- Pawn
    - [x] 앞으로 한 칸 움직일 수 있다.
    - [x] 대각선으로 움직일 수 있다.
    - [x] 움직인 적이 없을 경우 앞으로 두 칸 움직일 수 있다.

- Rook
    - [x] 사방으로 거리 제한 없이 움직일 수 있다.

- Knight
    - [x] 사방 중 한 방향으로 한 칸 그리고 그 방향의 양 대각선 방향 중 한 방향으로 움직일 수 있다.

- Bishop
    - [x] 대각선으로 거리 제한 없이 움직일 수 있다.

- Queen
    - [x] 사방과 대각선으로 거리 제한 없이 움직일 수 있다.

- King
    - [x] 사방과 대각선으로 한 칸씩 움직일 수 있다.

- Game
    - [x] 체스 말을 이동시킬 수 있다.
    - [x] 비어 있는 칸은 움직일 수 없다.
    - [x] 진영이 번갈아가며 이동시킨다.

### 컨벤션 요구사항

- [x] 한 메서드에 한 단계의 들여쓰기만 허용
- [x] else 사용 금지
- [x] 모든 원시값과 문자열 포장 (일회성 변수 제외)
- [x] 일급 컬렉션 적용
- [x] 2개 이하의 인스턴스 변수 사용
- [x] getter/setter 금지
- [x] 3개 이하의 메서드 인자 사용
- [x] 디미터 법칙 지키기
- [x] 메서드는 한 가지 역할만 담당
- [x] 클래스 작게 유지

## [1,2단계 미션 후 리팩토링 목록]

- [x] 자바 네이밍 컨벤션에 맞게 수정
- [x] Piece를 상속받은 객체들의 possibleMove 수정
- [x] 오류 메세지를 출력하는 기능 구현
- [x] 패키지 위치 수정
- [x] '구현체가 아닌 인터페이스에 대고 프로그래밍' 규칙 지키기
- [x] orElseThrow -> 대신 적절한 예외 처리
- [x] KnightMove -> Move로 합치기
- [x] 콘솔에 노출될 문구 -> view에서 확인할 수 있도록 수정하기
- [x] direction 계산 로직 -> gcd를 사용하는 것으로 수정

## [3단계 미션]

### 기능 요구사항

- Game
  - [x]  King이 잡혔을 때 게임이 종료된다.
  - [x]  게임 종료 후 남아 있는 말에 대한 점수를 구할 수 있어야 한다.
  - [x]  게임 종료 후 각 진영의 점수를 계산할 수 있다.
  - [x]  게임 종료 후 어느 진영이 이겼는지 결과를 확인할 수 있다.
  - [x]  “status” 명령에 대해 결과를 출력할 수 있다.

- GameCommand
  - [x]  "status" 명령인지 확인할 수 있다.

- 점수 계산
  - [x]  한 번에 한 쪽의 점수만을 계산해야 한다.

- Queen
  - [x]  9점을 가진다.

- Rook
  - [x]  5점을 가진다.

- Bishop
  - [x]  3점을 가진다.

- Knight
  - [x]  2.5점을 가진다.

- Pawn
  - [x]  1점을 가진다.

  - if 같은 세로줄에 같은 색의 폰이 있는 경우
    - [x]  0.5점을 가진다.

- King
  - [x]  잡히는 경우 경기가 끝나기 때문에 점수가 없다.

## [4단계 미션]

### 기능 요구사항

- [x] 애플리케이션을 재시작하더라도 이전에 하던 체스 게임을 다시 시작할 수 있어야 한다.
- [x] DB를 적용할 때 도메인 객체의 변경을 최소화해야한다.

### SQL 구조

```sql
CREATE TABLE game
(
    id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    turn VARCHAR(255) NOT NULL
);

CREATE TABLE piece
(
    id         INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    game_id    INT          NOT NULL,
    FOREIGN KEY (game_id) REFERENCES game (id) ON UPDATE CASCADE,
    piece_type VARCHAR(255) NOT NULL,
    piece_file VARCHAR(255) NOT NULL,
    piece_rank VARCHAR(255) NOT NULL,
    piece_team VARCHAR(255) NOT NULL
);
```

## [3,4단계 미션 후 리팩토링 목록]

- [x]  null 체크 Optional 객체를 사용하도록 변경
- [x]  `getWhitePieces(), getBlackPieces()` 하나의 메서드로 리팩토링
- [x]  `calculateWhiteScore(),``calculateBlackScore()`  하나의 메서드로 리팩토링
- [x]  BoardFactory로 책임 분리
- [x]  문자열을 먼저 두고 equals 비교 하도록 수정
- [ ]  객체들의 책임 분리

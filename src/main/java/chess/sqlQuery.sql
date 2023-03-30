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

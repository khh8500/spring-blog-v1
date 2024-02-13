insert into user_tb(username, password, email, created_at) values('ssar', '$2a$10$BUcmGtNuyI4T6ZSsbG.oYe/Ez3Z6j7CVRPo2zCYAJvafZL1Grvl6y', 'ssar@nate.com', now());
insert into user_tb(username, password, email, created_at) values('cos', '$2a$10$BUcmGtNuyI4T6ZSsbG.oYe/Ez3Z6j7CVRPo2zCYAJvafZL1Grvl6y', 'cos@nate.com', now());

insert into board_tb(title, content, user_id, created_at) values('제목1', '내용1', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목2', '내용2', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목3', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목4', '내용4', 2, now());
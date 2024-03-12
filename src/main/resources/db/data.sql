INSERT INTO user_tb(username, password, role, email, created_at) values('ssar', '$2a$10$pszt9sAg3yXLmy1XxZEjkeXOwJY0hMqhifao3hgwnqZ9XVb/7p.cm', 'ADMIN', '쌀', now());
INSERT INTO user_tb(username, password, role, email, created_at) values('cos', '$2a$10$uXK69mvklicxSDcW1XpxPuLYJDUgMMaaCSHaJRJfGKFX0voJ6ouZq', 'USER',  '코스', now());

commit;
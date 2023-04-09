
DELETE FROM users;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;

DELETE FROM categories;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 1;

DELETE FROM compilations;
ALTER TABLE compilations ALTER COLUMN id RESTART WITH 1;

DELETE FROM events;
ALTER TABLE events ALTER COLUMN id RESTART WITH 1;

DELETE FROM events_compilations;
ALTER TABLE events_compilations ALTER COLUMN id RESTART WITH 1;

DELETE FROM requests;
ALTER TABLE requests ALTER COLUMN id RESTART WITH 1;

/*

INSERT INTO users (email, name) VALUES ('Test1@gmail.com', 'Test user 1');
INSERT INTO users (email, name) VALUES ('Test2@gmail.com', 'Test user 2');
INSERT INTO users (email, name) VALUES ('Test3@gmail.com', 'Test user 3');

INSERT INTO categories (name) VALUES ('Категория 1');
INSERT INTO categories (name) VALUES ('Категория 2');
INSERT INTO categories (name) VALUES ('Категория 3');

INSERT INTO compilations (title, pinned) VALUES ('Компиляция 1', false);
INSERT INTO compilations (title, pinned) VALUES ('Компиляция 2', true);
INSERT INTO compilations (title, pinned) VALUES ('Компиляция 3', false);

INSERT INTO events (category_id, initiator_id, annotation, title, description,
                    "state", confirmed_requests, paid, participant_limit,
                    request_moderation, created_on, event_date,
                    published_on, lat, lon) VALUES (1, 1, 'аннотация', 'заголовок', 'описание',
                                                    'PENDING', 1, false, 5, false, '2023-01-01 00:00:00',
                                                    '2023-03-01 00:00:00',
                                                    '2023-02-01 00:00:00',
                                                    10, 50);

INSERT INTO events (category_id, initiator_id, annotation, title, description,
                    "state", confirmed_requests, paid, participant_limit,
                    request_moderation, created_on, event_date,
                    published_on, lat, lon) VALUES (2, 2, 'аннотация', 'заголовок', 'описание',
                                                    'PENDING', 1, false, 5, false, '2023-01-01 00:00:00',
                                                    '2023-03-01 00:00:00',
                                                    '2023-02-01 00:00:00',
                                                    10, 50);

INSERT INTO requests (requester_id, event_id, created, status) VALUES (1, 1, '2023-01-01 00:00:00', 'PUBLISHED');
INSERT INTO requests (requester_id, event_id, created, status) VALUES (2, 2, '2023-01-01 00:00:00', 'PUBLISHED');

INSERT INTO events_compilations (event_id, compilation_id) VALUES (1, 1);
INSERT INTO events_compilations (event_id, compilation_id) VALUES (2, 2);
 */

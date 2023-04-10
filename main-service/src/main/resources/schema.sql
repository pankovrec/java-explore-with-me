DROP TABLE IF EXISTS users, categories, compilations, events, events_compilations, requests CASCADE;

CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email varchar(100) NOT NULL,
    name varchar(50) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name varchar(100) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS compilations(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title varchar(200) NOT NULL,
    pinned BOOLEAN,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events(
                                     id
                                     BIGINT
                                     GENERATED
                                     BY
                                     DEFAULT AS
                                     IDENTITY
                                     NOT
                                     NULL,
                                     category_id
                                     BIGINT
                                     NOT
                                     NULL,
                                     initiator_id
                                     BIGINT
                                     NOT
                                     NULL,
                                     annotation
                                     varchar
(
                                     2000
) NOT NULL,
    title varchar
(
    120
) NOT NULL,
    description varchar
(
    7000
) NOT NULL,
    "state" varchar
(
    50
),
    confirmed_requests INTEGER,
    paid BOOLEAN,
    participant_limit INTEGER,
    request_moderation BOOLEAN,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_date TIMESTAMP
                         WITHOUT TIME ZONE NOT NULL,
    published_on TIMESTAMP
                         WITHOUT TIME ZONE,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT FK_EVENT_ON_USER FOREIGN KEY(initiator_id) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT FK_EVENT_ON_CATEGORY FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE RESTRICT,
    CONSTRAINT UQ_INITIATOR_TITLE UNIQUE(initiator_id, title)
);

CREATE TABLE IF NOT EXISTS events_compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_id BIGINT NOT NULL,
    compilation_id BIGINT NOT NULL,
    CONSTRAINT FK_EVENT FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT FK_COMPILATION FOREIGN KEY(compilation_id) REFERENCES compilations(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    requester_id BIGINT,
    event_id BIGINT,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT pk_requester PRIMARY KEY (id),
    CONSTRAINT FK_REQUEST_ON_REQUESTER FOREIGN KEY(requester_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK_REQUEST_ON_EVENT FOREIGN KEY(event_id) REFERENCES events(id) ON DELETE CASCADE
);
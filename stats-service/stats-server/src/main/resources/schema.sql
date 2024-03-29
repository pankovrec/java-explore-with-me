DROP TABLE IF EXISTS statistics;
CREATE TABLE IF NOT EXISTS statistics
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app VARCHAR(50) NOT NULL,
    uri VARCHAR(100) NOT NULL,
    ip VARCHAR(50) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE
);
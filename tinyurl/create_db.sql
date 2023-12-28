CREATE KEYSPACE IF NOT EXISTS tinyurl
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 3
        };

CREATE TABLE IF NOT EXISTS tinyurl.url_mapping
(
    ID          bigint PRIMARY KEY,
    shortURL    varchar,
    originalURL varchar,
    userId      varchar
);

CREATE INDEX IF NOT EXISTS ON tinyurl.url_mapping (shortURL);
CREATE INDEX IF NOT EXISTS ON tinyurl.url_mapping (originalURL);

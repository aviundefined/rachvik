CREATE KEYSPACE IF NOT EXISTS rummy
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 3
        };

CREATE TYPE IF NOT EXISTS rummy.card (
    deck_identifier int,
    suit TEXT,
    card_value TEXT
);

CREATE TYPE IF NOT EXISTS rummy.player (
    username text
);

CREATE TABLE IF NOT EXISTS rummy.move (
    id BIGINT PRIMARY KEY,
    game_id TEXT,
    timestamp BIGINT,
    player FROZEN<player>,
    is_picked_from_discarded_pile BOOLEAN,
    picked FROZEN<card>,
    discarded FROZEN<card>
);

CREATE INDEX IF NOT EXISTS ON rummy.move (game_id);
CREATE KEYSPACE IF NOT EXISTS rummy
    WITH REPLICATION = {
        'class' : 'SimpleStrategy',
        'replication_factor' : 3
        };

CREATE TYPE IF NOT EXISTS rummy.card (
    deck_identifier int,
    suit TEXT,
    cardValue TEXT
);

CREATE TYPE IF NOT EXISTS rummy.player (
    username text
);

CREATE TYPE IF NOT EXISTS rummy.user_hand (
    player frozen<player>,
    card list<frozen<card>>
);

CREATE TYPE IF NOT EXISTS rummy.deck (
    card list<frozen<card>>
);

CREATE TYPE IF NOT EXISTS rummy.rummy_deck (
    deck list<frozen<deck>>
);

CREATE TYPE IF NOT EXISTS rummy.game_config (
    min_number_of_players int,
    max_number_of_players int,
    max_timeout_millis_to_start int
);


CREATE TYPE IF NOT EXISTS rummy.game_state (
    state int,
    available list<frozen<card>>,
    joker frozen<card>,
    player list<frozen<player>>,
    user_hand list<frozen<user_hand>>,
    discarded_pile list<frozen<card>>,
    closed_piles list<frozen<card>>,
    number_of_turns_played int,
    last_move_id bigint,
    last_discarded_card frozen<card>,
    active_player_index int
);

CREATE TABLE IF NOT EXISTS rummy.game (
    game_id text PRIMARY KEY,
    config frozen<game_config>,
    state frozen<game_state>
);

CREATE TABLE IF NOT EXISTS rummy.move (
    id BIGINT PRIMARY KEY,
    game_id TEXT,
    timestamp BIGINT,
    username TEXT,
    is_picked_from_discarded_pile BOOLEAN,
    picked FROZEN<card>,
    discarded FROZEN<card>
);

CREATE INDEX IF NOT EXISTS ON rummy.move (game_id);
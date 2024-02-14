CREATE TABLE user_review
(
    id          BIGSERIAL PRIMARY KEY,
    external_id BIGINT,
    user_id     BIGINT REFERENCES users (id)
);

CREATE INDEX ON user_review (user_id);
CREATE INDEX ON user_review (external_id);

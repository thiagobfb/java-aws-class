CREATE TABLE IF NOT EXISTS request_log (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    endpoint    VARCHAR(255) NOT NULL,
    method      VARCHAR(10)  NOT NULL,
    start_time  DATETIME     NOT NULL,
    end_time    DATETIME     NOT NULL,
    duration_ms BIGINT       NOT NULL,
    status_code INT          NOT NULL,
    created_at  DATETIME     NOT NULL
);
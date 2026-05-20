package com.aws.class3.requestlog.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_log")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String endpoint;

    @Column(nullable = false, length = 10)
    private String method;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Long durationMs;

    @Column(nullable = false)
    private Integer statusCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected RequestLog() {}

    public RequestLog(String endpoint, String method, LocalDateTime startTime, LocalDateTime endTime, Long durationMs, Integer statusCode) {
        this.endpoint = endpoint;
        this.method = method;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationMs = durationMs;
        this.statusCode = statusCode;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getEndpoint() { return endpoint; }
    public String getMethod() { return method; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public Long getDurationMs() { return durationMs; }
    public Integer getStatusCode() { return statusCode; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
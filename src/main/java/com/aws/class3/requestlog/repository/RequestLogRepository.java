package com.aws.class3.requestlog.repository;

import com.aws.class3.requestlog.domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
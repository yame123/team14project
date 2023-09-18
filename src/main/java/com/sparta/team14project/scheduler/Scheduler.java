package com.sparta.team14project.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 매일 자정에 실행
    @Scheduled(cron = "0 30 1 * * ?")// 매일 오전 1시 30분
    public void deleteTableAtMidnight() {

        // 해당 테이블 삭제 SQL 실행
        String deleteTableSQL = "DROP TABLE signup_code";

        // 테이블 삭제
        jdbcTemplate.execute(deleteTableSQL);
    } // 다시 어플리케이션.java 실행시키면 테이블 만들어져 있음
}

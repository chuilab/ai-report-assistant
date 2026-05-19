package com.aireport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * AI报表助手启动类
 *
 * @author aireport
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.aireport.mapper")
public class AiReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiReportApplication.class, args);
        System.out.println("==========================================");
        System.out.println("   AI Report Assistant Started Successfully");
        System.out.println("   API Documentation: http://localhost:8080/api/doc.html");
        System.out.println("==========================================");
    }

}
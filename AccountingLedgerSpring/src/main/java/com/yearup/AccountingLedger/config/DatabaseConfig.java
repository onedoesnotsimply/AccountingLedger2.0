package com.yearup.AccountingLedger.config;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    private BasicDataSource basicDataSource;

    @Bean
    public DataSource dataSource(){
        return basicDataSource;
    }

    @Autowired
    public DatabaseConfig (@Value("${spring.datasource.url}") String url)//,
    //@Value("${spring.datasource.username}") String username)
    //@Value("${datasource.password}") String password)
    {
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("password");
        // print the values to the screen just to verify it worked
        //System.out.println(url + " : " + username + " : password");
    }
}
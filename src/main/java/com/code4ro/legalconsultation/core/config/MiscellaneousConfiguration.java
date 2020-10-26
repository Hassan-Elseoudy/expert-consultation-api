package com.code4ro.legalconsultation.core.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MiscellaneousConfiguration {

    @Bean
    public Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }
}

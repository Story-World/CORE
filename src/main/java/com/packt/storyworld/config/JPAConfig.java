package com.packt.storyworld.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.packt.storyworld.repository")
public class JPAConfig {

}
